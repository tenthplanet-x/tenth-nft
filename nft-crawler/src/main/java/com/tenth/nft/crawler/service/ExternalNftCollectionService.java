package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.crawler.dto.ExternalNftCollectionDTO;
import com.tenth.nft.crawler.vo.ExternalNftCollectionDeleteRequest;
import com.tenth.nft.orm.external.dao.NftCollectionNoCacheDao;
import com.tenth.nft.orm.external.dao.expression.ExternalNftCollectionQuery;
import com.tenth.nft.orm.external.dao.expression.ExternalNftCollectionUpdate;
import com.tenth.nft.orm.external.entity.ExternalNftCollection;
import com.tenth.nft.crawler.vo.ExternalNftCollectionCreateRequest;
import com.tenth.nft.crawler.vo.ExternalNftCollectionEditRequest;
import com.tenth.nft.crawler.vo.ExternalNftCollectionListRequest;
import com.tenth.nft.protobuf.Search;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Service
public class ExternalNftCollectionService {

    @Autowired
    private NftCollectionNoCacheDao nftCollectionDao;
    @Autowired
    private RouteClient routeClient;

    public Page<ExternalNftCollectionDTO> list(ExternalNftCollectionListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<ExternalNftCollectionDTO> dataPage = nftCollectionDao.findPage(
                ExternalNftCollectionQuery.newBuilder()
                        .categoryId(request.getCategoryId())
                        .nameRegex(request.getName())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                ExternalNftCollectionDTO.class
        );

        return dataPage;
    }

    public void create(ExternalNftCollectionCreateRequest request) {

        ExternalNftCollection nftCollection = new ExternalNftCollection();
        nftCollection.setCreatedAt(System.currentTimeMillis());
        nftCollection.setUpdatedAt(System.currentTimeMillis());
        nftCollection.setName(request.getName());
        nftCollection.setDesc(request.getDesc());
        nftCollection.setContractAddress(request.getContractAddress());
        nftCollection.setLogoImage(request.getLogoImage());
        nftCollection.setFeaturedImage(request.getFeaturedImage());
        nftCollection.setBannerImage(request.getBannerImage());
        nftCollection.setTotalVolume(request.getTotalVolume());
        nftCollection.setFloorPrice(request.getFloorPrice());
        nftCollection.setTotalSupply(request.getTotalSupply());
        nftCollection.setCategoryId(request.getCategoryId());
        nftCollectionDao.insert(nftCollection);

    }

    public void edit(ExternalNftCollectionEditRequest request) {

        nftCollectionDao.update(
                ExternalNftCollectionQuery.newBuilder().id(request.getId()).build(),
                ExternalNftCollectionUpdate.newBuilder()
                                .setName(request.getName())
                                .setDesc(request.getDesc())
                                .setContractAddress(request.getContractAddress())
                                .setLogoImage(request.getLogoImage())
                                .setFeaturedImage(request.getFeaturedImage())
                                .setBannerImage(request.getBannerImage())
                                .setTotalVolume(request.getTotalVolume())
                                .setFloorPrice(request.getFloorPrice())
                                .setTotalSupply(request.getTotalSupply())
                                .setCategoryId(request.getCategoryId())
                        .build()
        );

        rebuildCache(request.getId());
    }


    public void delete(ExternalNftCollectionDeleteRequest request) {
        nftCollectionDao.remove(ExternalNftCollectionQuery.newBuilder().id(request.getId()).build());
    }

    public ExternalNftCollectionDTO detail(ExternalNftCollectionDeleteRequest request) {

        ExternalNftCollectionDTO dto = nftCollectionDao.findOne(ExternalNftCollectionQuery.newBuilder()
                        .id(request.getId())
                .build(), ExternalNftCollectionDTO.class);

        return dto;
    }

    private void rebuildCache(Long id) {
        routeClient.send(
                Search.NFT_COLLECTION_REBUILD_IC.newBuilder()
                        .setCollectionId(id)
                        .build(),
                CollectionRebuildRouteRequest.class
        );
    }
}
