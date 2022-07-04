package com.tenth.nft.marketplace.service;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.TpulseIdModules;
import com.ruixi.tpulse.convention.protobuf.NftSearch;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.nft.CollectionRebuildRouteRequest;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.marketplace.dao.NftCollectionNoCacheDao;
import com.tenth.nft.marketplace.dao.expression.NftCollectionQuery;
import com.tenth.nft.marketplace.dao.expression.NftCollectionUpdate;
import com.tenth.nft.marketplace.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.entity.NftCollection;
import com.tenth.nft.marketplace.vo.*;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.oss.IGsOssService;
import com.tpulse.gs.oss.qiniu.QiniuProperties;
import com.tpulse.gs.oss.vo.OSSToken;
import com.tpulse.gs.oss.vo.OSSTokenCreateOption;
import com.tpulse.gs.oss.vo.OSSTokenType;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 10:06
 */
@Service
public class NftCollectionService {

    @Autowired
    private NftCollectionNoCacheDao nftCollectionDao;
    @Autowired
    private IGsOssService gsOssService;
    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private RouteClient routeClient;

    public OSSToken getUploadToken() {
        return gsOssService.token(OSSTokenCreateOption.newBuilder()
                .bucket(qiniuProperties.getDefaultBucket())
                .keyPrefix("tmp/")
                .type(OSSTokenType.FILE)
                .build()
        );
    }

    public Page<NftCollectionDTO> list(NftCollectionListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Page<NftCollectionDTO> dataPage = nftCollectionDao.findPage(
                NftCollectionQuery.newBuilder()
                        .uid(uid)
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftCollectionDTO.class
        );

        String nickname = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder()
                        .addUids(uid)
                        .build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0).getNickname();
        dataPage.getData().forEach(dto -> {
            dto.setCreatorName(nickname);
        });

        return dataPage;
    }

    public NftCollectionDTO create(NftCollectionCreateRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Long id = gsCollectionIdService.incrementAndGet(TpulseIdModules.COLLECTION);

        String logoImageDir = request.getLogoImage().substring(request.getLogoImage().indexOf("tmp/") + 4, request.getLogoImage().lastIndexOf("/"));
        String logoImageUrl = gsOssService.changeDir(request.getLogoImage(), logoImageDir);
        String featuredImageDir = request.getFeaturedImage().substring(request.getFeaturedImage().indexOf("tmp/") + 4, request.getFeaturedImage().lastIndexOf("/"));
        String featuredImageUrl = gsOssService.changeDir(request.getFeaturedImage(), featuredImageDir);

        NftCollection nftCollection = new NftCollection();
        nftCollection.setId(id);
        nftCollection.setUid(uid);
        nftCollection.setCreatedAt(System.currentTimeMillis());
        nftCollection.setUpdatedAt(System.currentTimeMillis());
        nftCollection.setName(request.getName());
        nftCollection.setDesc(request.getDesc());
        nftCollection.setLogoImage(logoImageUrl);
        nftCollection.setFeaturedImage(featuredImageUrl);
        nftCollection.setCategory(request.getCategory());
        nftCollection.setCreatorFee(request.getCreatorFee());
        nftCollection.setBlockchain(request.getBlockchain());
        nftCollection = nftCollectionDao.insert(nftCollection);

        rebuildCache(nftCollection.getId());

        NftCollectionDetailRequest detailRequest = new NftCollectionDetailRequest();
        detailRequest.setId(nftCollection.getId());
        return detail(detailRequest);

    }

    public void edit(NftCollectionEditRequest request) {

        nftCollectionDao.update(
                NftCollectionQuery.newBuilder().id(request.getId()).build(),
                NftCollectionUpdate.newBuilder()
                        .setName(request.getName())
                        .setDesc(request.getDesc())
                        .setLogoImage(request.getLogoImage())
                        .setFeaturedImage(request.getFeaturedImage())
                        .setCategory(request.getCategory())
                        .setCreatorFee(request.getCreatorFee())
                        .setBlockchain(request.getBlockchain())
                        .build()
        );
        rebuildCache(request.getId());
    }

    public void delete(NftCollectionDeleteRequest request) {
        nftCollectionDao.remove(NftCollectionQuery.newBuilder().id(request.getId()).build());
    }

    public NftCollectionDTO detail(NftCollectionDetailRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftCollectionDTO dto = nftCollectionDao.findOne(NftCollectionQuery.newBuilder()
                .id(request.getId())
                .build(), NftCollectionDTO.class);

        dto.setOwned(uid.equals(dto.getUid()));

        return dto;
    }

    public void updateItems(Long collectionId, long items) {
        nftCollectionDao.update(
                NftCollectionQuery.newBuilder().id(collectionId).build(),
                NftCollectionUpdate.newBuilder().items(items).build()
        );
        rebuildCache(collectionId);
    }

    private void rebuildCache(Long id) {

        routeClient.send(
                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
                        .setCollectionId(id)
                        .build(),
                CollectionRebuildRouteRequest.class
        );
    }
}
