package com.tenth.nft.crawler.service;

import com.tenth.nft.convention.TenthOssKeys;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.crawler.NftCrawlerProperties;
import com.tenth.nft.crawler.dao.NftBotDao;
import com.tenth.nft.crawler.dao.NftCollectionDao;
import com.tenth.nft.crawler.dao.NftCollectionStatsDao;
import com.tenth.nft.crawler.dao.NftItemDao;
import com.tenth.nft.crawler.dao.expression.*;
import com.tenth.nft.crawler.entity.NftBot;
import com.tenth.nft.crawler.entity.NftCollection;
import com.tenth.nft.crawler.entity.NftItem;
import com.tenth.nft.crawler.sdk.alchemy.AlchemySdk;
import com.tenth.nft.crawler.sdk.alchemy.dto.AlchemyNftDTO;
import com.tenth.nft.crawler.sdk.opensea.OpenseaSdk;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaCollectionDTO;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaCollectionStats;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.oss.qiniu.QiniuImageUrls;
import com.tpulse.gs.oss.qiniu.service.QiniuOSSService;
import com.tpulse.gs.oss.vo.OSSUploadMetadata;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * bot process
 * @author shijie
 * @createdAt 2022/6/14 11:35
 */
@Service
public class NftBotProcessService {

    private static final Long COLLECT_DURATION = 0l;
    @Autowired
    private NftBotDao nftBotDao;
    @Autowired
    private OpenseaSdk openseaSdk;
    @Autowired
    private NftCollectionDao nftCollectionDao;
    @Autowired
    private NftItemDao nftItemDao;
    @Autowired
    private AlchemySdk alchemySdk;
    @Autowired
    private QiniuOSSService qiniuOSSService;
    @Autowired
    private NftCrawlerProperties nftCrawlerProperties;
    @Autowired
    private NftCollectionStatsDao nftCollectionStatsDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(NftBotProcessService.class);

    /**
     * schedule execute
     */
    public void execute(){

        if(!nftCrawlerProperties.isOpen()) return;

        int page = 1;
        final int pageSize = 1;
        boolean empty = false;
        final int scanLimit = 30;
        int scans = 0;
        do{
            Page<NftBot> bots = nftBotDao.findPage(NftBotQuery.newBuilder().offline(false).setPage(page).setPageSize(pageSize).setSortField("collectedAt").setReverse(false).build());
            if(bots.getData().isEmpty()){
                empty = true;
                continue;
            }

            for(NftBot bot: bots.getData()){
                if(null == bot.getCollectedAt() || Times.isExpired(bot.getCollectedAt() + COLLECT_DURATION)){

                    try{
                        doCollect(bot);
                        //delay
                        Thread.sleep(1000);
                    }catch (Exception e){
                        String errorMsg = String.format("bot collect exception, bot: %s", bot.getContractAddress());
                        LOGGER.error(errorMsg, e);
                    }finally {
                        scans++;
                        nftBotDao.update(
                                NftBotQuery.newBuilder().id(bot.getId()).build(),
                                NftBotUpdate.newBuilder().setCollectedAt(System.currentTimeMillis()).build()
                        );
                    }
                }
            }

            page++;

        }while (!empty && scans < scanLimit);

    }

    /**
     *
     * @param bot
     */
    private void doCollect(NftBot bot) throws Exception {

        NftCollection nftCollection = nftCollectionDao.findOne(NftCollectionQuery.newBuilder().contractAddress(bot.getContractAddress()).build());
        OpenseaCollectionDTO opeanseaConstractDTO = openseaSdk.getByMarketplaceId(bot.getMarketplaceId());
        if(null == nftCollection){

            String imageUrl = qiniuWrap(opeanseaConstractDTO.getImageUrl(), null);
            String bannerImageUrl = qiniuWrap(opeanseaConstractDTO.getBannerImageUrl(), null);
            String featuredImageUrl = qiniuWrap(opeanseaConstractDTO.getFeaturedImageUrl(), null);

            nftCollection = nftCollectionDao.findAndModify(
                    NftCollectionQuery.newBuilder().contractAddress(bot.getContractAddress()).build(),
                    NftCollectionUpdate.newBuilder()
                            .setLogoImage(imageUrl)
                            .setBannerImage(bannerImageUrl)
                            .setFeaturedImage(featuredImageUrl)
                            .setName(opeanseaConstractDTO.getName())
                            .setDesc(opeanseaConstractDTO.getDesc())
                            .setCreatedAtOnInsert(dtFormat(opeanseaConstractDTO.getCreatedDate()))
                            .build(),
                    UpdateOptions.options().upsert(true).returnNew(true)
            );
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("bot have scanned through {}", bot.getContractAddress());
            }
        }

        //stats
        if(null != opeanseaConstractDTO.getStats()){

            OpenseaCollectionStats statsDTO = opeanseaConstractDTO.getStats();

            nftCollectionDao.update(
                    NftCollectionQuery.newBuilder().contractAddress(bot.getContractAddress()).build(),
                    NftCollectionUpdate.newBuilder()
                            .setVolume(statsDTO.getTotalVolume())
                            .setFloorPrice(statsDTO.getFloorPrice())
                            .setTotalSupply(statsDTO.getTotalSupply())
                            .build()
            );

            String date = DateTime.now().toString("yyyy-MM-dd");
            nftCollectionStatsDao.findAndModify(
                    NftCollectionStatsQuery.newBuilder().contractAddress(bot.getContractAddress()).date(date).build(),
                    NftCollectionStatsUpdate.newBuilder()
                            .setOneDayVolume(statsDTO.getOneDayVolume())
                            .setSevenDayVolume(statsDTO.getSevenDayVolume())
                            .setThirtyDayVolume(statsDTO.getThirtyDayVolume())
                            .setTotalSupply(statsDTO.getTotalSupply())
                            .setTotalVolume(statsDTO.getTotalVolume())
                            .setCreatedAtOnInsert()
                            .build(),
                    UpdateOptions.options().upsert(true)
            );
        }


        //
        int fromTokenId = 0;
        List<NftItem> existedItems = nftItemDao.find(NftItemQuery.newBuilder()
                .contractAddress(bot.getContractAddress())
                .setLimit(1)
                .setSortField("tokenNo")
                .setReverse(true)
                .build()
        );
        if(!existedItems.isEmpty()){
            Integer latestTokenNo = existedItems.get(0).getTokenNo();
            fromTokenId = latestTokenNo + 1;
        }
        List<AlchemyNftDTO> items = alchemySdk.getItemsByConstract(bot.getContractAddress(), fromTokenId); // rate-limit
        if(null != items){
            items.stream().limit(10).forEach(item -> {
                Integer tokenNo = Integer.decode(item.getId().getTokenId());
                String tokenId = String.valueOf(tokenNo);
                String name = item.getTitle();
                String url = item.getMetadata().getImage();
                url = ipfsUnwrap(url);
                String previewUrl = url;
                String rawUrl = url;
                String thumbnailUrl = url;
                //
                try{
                    String key = TenthOssKeys.join(TenthOssKeys.nft, DigestUtils.md5Hex(url));
                    url = qiniuOSSService.upload(key, new URL(url).openStream(), new OSSUploadMetadata()).getUrl();
                    rawUrl = url;
                    previewUrl = QiniuImageUrls.newBuilder().url(url.substring(0, url.indexOf("?"))).thumbnail().width(500).build().build();
                    thumbnailUrl = previewUrl;
                }catch (Exception e){
                    LOGGER.error(String.format("upload to qiniu excepition, url: %s", url), e);
                }

                nftItemDao.findAndModify(
                        NftItemQuery.newBuilder().contractAddress(bot.getContractAddress()).tokenNo(tokenNo).build(),
                        NftItemUpdate.newBuilder()
                                .setName(name)
                                .setPreviewUrl(previewUrl)
                                .setRawUrl(rawUrl)
                                .setThumbnailUrl(thumbnailUrl)
                                .setCreatedAtOnInsert()
                                .setTokenId(tokenId)
                                .build(),
                        UpdateOptions.options().upsert(true)
                );

            });
        }
    }

    private String qiniuWrap(String url, Integer width) {

        try{
            String key = TenthOssKeys.join(TenthOssKeys.nft, DigestUtils.md5Hex(url));
            url = qiniuOSSService.upload(key, new URL(url).openStream(), new OSSUploadMetadata()).getUrl();
            if(null != width){
                QiniuImageUrls.Builder builder = QiniuImageUrls.newBuilder().url(url.substring(0, url.indexOf("?")));
                builder = builder.thumbnail().width(width).build();
                url = builder.build();
            }
            return url;
        }catch (Exception e){
            LOGGER.error(String.format("upload to qiniu excepition, url: %s", url), e);
            return url;
        }
    }


    private String ipfsUnwrap(String url){

        try{

            URI urlObj = new URI(url);
            String protocol = urlObj.getScheme();
            if(protocol.equals("ipfs")){
                url = String.format("https://ipfs.io" + url.substring(6));
            }
        }catch (Exception e){
            LOGGER.error("", e);
        }
        return url;
    }

    /**
     * datetime format
     * @param createdAt
     * @return
     */
    private Long dtFormat(String createdAt) {
        return Instant.parse(createdAt).getMillis();
    }

}
