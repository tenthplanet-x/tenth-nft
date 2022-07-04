package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.CategoryRebuildRouteRequest;
import com.tenth.nft.crawler.dto.ExternalNftCategoryDTO;
import com.tenth.nft.crawler.vo.ExternalNftCategoryCreateRequest;
import com.tenth.nft.crawler.vo.ExternalNftCategoryDeleteRequest;
import com.tenth.nft.crawler.vo.ExternalNftCategoryEditRequest;
import com.tenth.nft.crawler.vo.ExternalNftCategoryListRequest;
import com.tenth.nft.orm.ExternalNftCategoryVersions;
import com.tenth.nft.orm.dao.NftCategoryNoCacheDao;
import com.tenth.nft.orm.dao.expression.ExternalNftCategoryQuery;
import com.tenth.nft.orm.dao.expression.ExternalNftCategoryUpdate;
import com.tenth.nft.orm.entity.ExternalNftCategory;
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
public class ExternalNftCategoryService {

    @Autowired
    private NftCategoryNoCacheDao nftCategoryDao;
    @Autowired
    private RouteClient routeClient;

    public Page<ExternalNftCategoryDTO> list(ExternalNftCategoryListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<ExternalNftCategoryDTO> dataPage = nftCategoryDao.findPage(
                ExternalNftCategoryQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                ExternalNftCategoryDTO.class
        );

        return dataPage;
    }

    public void create(ExternalNftCategoryCreateRequest request) {

        ExternalNftCategory nftCategory = new ExternalNftCategory();
        nftCategory.setCreatedAt(System.currentTimeMillis());
        nftCategory.setUpdatedAt(System.currentTimeMillis());
        nftCategory.setName(request.getName());
        nftCategory.setOrder(request.getOrder());
        nftCategory.setVersion(ExternalNftCategoryVersions.VERSION);
        nftCategory = nftCategoryDao.insert(nftCategory);

        rebuildCache();
    }

    public void edit(ExternalNftCategoryEditRequest request) {

        nftCategoryDao.update(
                ExternalNftCategoryQuery.newBuilder().id(request.getId()).build(),
                ExternalNftCategoryUpdate.newBuilder()
                                .setName(request.getName())
                                .setOrder(request.getOrder())
                        .build()
        );

        rebuildCache();
    }

    public void delete(ExternalNftCategoryDeleteRequest request) {
        nftCategoryDao.remove(ExternalNftCategoryQuery.newBuilder().id(request.getId()).build());
    }

    public ExternalNftCategoryDTO detail(ExternalNftCategoryDeleteRequest request) {

        ExternalNftCategoryDTO dto = nftCategoryDao.findOne(ExternalNftCategoryQuery.newBuilder()
                        .id(request.getId())
                .build(), ExternalNftCategoryDTO.class);

        return dto;
    }

    private void rebuildCache() {
        routeClient.send(
                Search.NFT_CATEGORY_REBUILD_IC.newBuilder()
                        .setVersion(ExternalNftCategoryVersions.VERSION)
                        .build(),
                CategoryRebuildRouteRequest.class
        );
    }
}
