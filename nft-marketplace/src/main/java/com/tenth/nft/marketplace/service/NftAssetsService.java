package com.tenth.nft.marketplace.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.AssetsRebuildRouteRequest;
import com.tenth.nft.orm.marketplace.dao.NftAssetsNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsUpdate;
import com.tenth.nft.marketplace.dto.NftAssetsDTO;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.marketplace.vo.*;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.oss.IGsOssService;
import com.tpulse.gs.oss.qiniu.QiniuProperties;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 10:06
 */
@Service
public class NftAssetsService {

    @Autowired
    private NftAssetsNoCacheDao nftAssetsDao;
    @Autowired
    private IGsOssService gsOssService;
    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private NftCollectionService nftCollectionService;
    @Autowired
    private RouteClient routeClient;


    public Page<NftAssetsDTO> list(NftAssetsListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftAssetsDTO> dataPage = nftAssetsDao.findPage(
                NftAssetsQuery.newBuilder()
                        .setCollectionId(request.getCollectionId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftAssetsDTO.class
        );

        return dataPage;
    }

    public NftAssetsDTO create(NftAssetsCreateRequest request) {

        GameUserContext context = GameUserContext.get();

        NftAssets nftAssets = new NftAssets();
        nftAssets.setCreatedAt(System.currentTimeMillis());
        nftAssets.setUpdatedAt(System.currentTimeMillis());
        nftAssets.setType(request.getType());
        nftAssets.setCollectionId(request.getCollectionId());
        String dir = request.getUrl().substring(request.getUrl().indexOf("tmp/") + 4, request.getUrl().lastIndexOf("/"));
        String url = gsOssService.changeDir(request.getUrl(), dir);
        if(!Strings.isNullOrEmpty(request.getPreviewUrl())){
            String previewDir = request.getUrl().substring(request.getUrl().indexOf("tmp/") + 4, request.getUrl().lastIndexOf("/"));
            String previewUrl = gsOssService.changeDir(request.getPreviewUrl(), previewDir);
            nftAssets.setPreviewUrl(previewUrl);
        }
        nftAssets.setUrl(url);
        nftAssets.setName(request.getName());
        nftAssets.setDesc(request.getDesc());
        nftAssets.setSupply(request.getSupply());
        nftAssets.setBlockchain(request.getBlockchain());
        nftAssets = nftAssetsDao.insert(nftAssets);

        //更新总数量
        long count = nftAssetsDao.count(NftAssetsQuery.newBuilder().setCollectionId(request.getCollectionId()).build());
        nftCollectionService.updateItems(request.getCollectionId(), count);

//        rebuildCache(nftAssets.getId());

        NftAssetsDetailRequest detailRequest = new NftAssetsDetailRequest();
        detailRequest.setId(nftAssets.getId());
        return detail(detailRequest);


    }

    public void edit(NftAssetsEditRequest request) {

        nftAssetsDao.update(
                NftAssetsQuery.newBuilder().id(request.getId()).build(),
                NftAssetsUpdate.newBuilder()
                        .setType(request.getType())
                        .setUrl(request.getUrl())
                        .setPreviewUrl(request.getPreviewUrl())
                        .setName(request.getName())
                        .setDesc(request.getDesc())
                        .setSupply(request.getSupply())
                        .setBlockchain(request.getBlockchain())
                        .build()
        );

//        rebuildCache(request.getId());
    }

    public void delete(NftAssetsDeleteRequest request) {
        nftAssetsDao.remove(NftAssetsQuery.newBuilder().id(request.getId()).build());
    }

    public NftAssetsDTO detail(NftAssetsDetailRequest request) {

        NftAssetsDTO dto = nftAssetsDao.findOne(NftAssetsQuery.newBuilder()
                .id(request.getId())
                .build(), NftAssetsDTO.class);

        return dto;
    }

    public void rebuildCache(Long assetsId){
        routeClient.send(
                NftSearch.NFT_ASSETS_REBUILD_IC.newBuilder()
                        .setAssetsId(assetsId)
                        .build(),
                AssetsRebuildRouteRequest.class
        );
    }


}
