package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.CategoryRebuildRouteRequest;
import com.tenth.nft.crawler.dto.NftCategoryDTO;
import com.tenth.nft.crawler.vo.NftCategoryCreateRequest;
import com.tenth.nft.crawler.vo.NftCategoryDeleteRequest;
import com.tenth.nft.crawler.vo.NftCategoryEditRequest;
import com.tenth.nft.crawler.vo.NftCategoryListRequest;
import com.tenth.nft.orm.NftCategoryVersions;
import com.tenth.nft.orm.dao.NftCategoryDao;
import com.tenth.nft.orm.dao.NftCategoryNoCacheDao;
import com.tenth.nft.orm.dao.expression.NftCategoryQuery;
import com.tenth.nft.orm.dao.expression.NftCategoryUpdate;
import com.tenth.nft.orm.entity.NftCategory;
import com.tenth.nft.protobuf.Search;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Service
public class NftCategoryService {

    @Autowired
    private NftCategoryNoCacheDao nftCategoryDao;
    @Autowired
    private RouteClient routeClient;

    public Page<NftCategoryDTO> list(NftCategoryListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftCategoryDTO> dataPage = nftCategoryDao.findPage(
                NftCategoryQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftCategoryDTO.class
        );

        return dataPage;
    }

    public void create(NftCategoryCreateRequest request) {

        NftCategory nftCategory = new NftCategory();
        nftCategory.setCreatedAt(System.currentTimeMillis());
        nftCategory.setUpdatedAt(System.currentTimeMillis());
        nftCategory.setName(request.getName());
        nftCategory.setOrder(request.getOrder());
        nftCategory.setVersion(NftCategoryVersions.VERSION);
        nftCategory = nftCategoryDao.insert(nftCategory);

        rebuildCache();
    }

    public void edit(NftCategoryEditRequest request) {

        nftCategoryDao.update(
                NftCategoryQuery.newBuilder().id(request.getId()).build(),
                NftCategoryUpdate.newBuilder()
                                .setName(request.getName())
                                .setOrder(request.getOrder())
                        .build()
        );

        rebuildCache();
    }

    public void delete(NftCategoryDeleteRequest request) {
        nftCategoryDao.remove(NftCategoryQuery.newBuilder().id(request.getId()).build());
    }

    public NftCategoryDTO detail(NftCategoryDeleteRequest request) {

        NftCategoryDTO dto = nftCategoryDao.findOne(NftCategoryQuery.newBuilder()
                        .id(request.getId())
                .build(), NftCategoryDTO.class);

        return dto;
    }

    private void rebuildCache() {
        routeClient.send(
                Search.NFT_CATEGORY_REBUILD_IC.newBuilder()
                        .setVersion(NftCategoryVersions.VERSION)
                        .build(),
                CategoryRebuildRouteRequest.class
        );
    }
}
