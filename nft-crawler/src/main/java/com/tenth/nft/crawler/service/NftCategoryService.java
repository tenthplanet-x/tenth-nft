package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.orm.dao.NftCategoryDao;
import com.tenth.nft.orm.dao.expression.NftCategoryQuery;
import com.tenth.nft.orm.dao.expression.NftCategoryUpdate;
import com.tenth.nft.crawler.dto.NftCategoryDTO;
import com.tenth.nft.orm.entity.NftCategory;
import com.tenth.nft.crawler.vo.NftCategoryCreateRequest;
import com.tenth.nft.crawler.vo.NftCategoryDeleteRequest;
import com.tenth.nft.crawler.vo.NftCategoryEditRequest;
import com.tenth.nft.crawler.vo.NftCategoryListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Service
public class NftCategoryService {

    @Autowired
    private NftCategoryDao nftCategoryDao;

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
        nftCategoryDao.insert(nftCategory);

    }

    public void edit(NftCategoryEditRequest request) {

        nftCategoryDao.update(
                NftCategoryQuery.newBuilder().id(request.getId()).build(),
                NftCategoryUpdate.newBuilder()
                                .setName(request.getName())
                                .setOrder(request.getOrder())
                        .build()
        );
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
}
