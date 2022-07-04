package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.crawler.vo.ExternalNftItemCreateRequest;
import com.tenth.nft.orm.external.dao.ExternalNftItemDao;
import com.tenth.nft.orm.external.dao.expression.ExternalNftItemQuery;
import com.tenth.nft.orm.external.dao.expression.ExternalNftItemUpdate;
import com.tenth.nft.crawler.dto.ExternalNftItemDTO;
import com.tenth.nft.orm.external.entity.ExternalNftAssets;
import com.tenth.nft.crawler.vo.ExternalNftItemDeleteRequest;
import com.tenth.nft.crawler.vo.ExternalNftItemEditRequest;
import com.tenth.nft.crawler.vo.ExternalNftItemListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Service
public class ExternalNftItemService {

    @Autowired
    private ExternalNftItemDao nftItemDao;

    public Page<ExternalNftItemDTO> list(ExternalNftItemListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<ExternalNftItemDTO> dataPage = nftItemDao.findPage(
                ExternalNftItemQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(request.getSortField())
                        .setReverse(reverse)
                        .build(),
                ExternalNftItemDTO.class
        );

        return dataPage;
    }

    public void create(ExternalNftItemCreateRequest request) {

        ExternalNftAssets nftItem = new ExternalNftAssets();
        nftItem.setCreatedAt(System.currentTimeMillis());
        nftItem.setUpdatedAt(System.currentTimeMillis());
        nftItem.setName(request.getName());
        nftItem.setPreviewUrl(request.getPreviewUrl());
        nftItem.setThumbnailUrl(request.getThumbnailUrl());
        nftItem.setRawUrl(request.getRawUrl());
        nftItem.setContractAddress(request.getContactAddress());
        nftItem.setTokenId(request.getTokenId());
        nftItemDao.insert(nftItem);

    }

    public void edit(ExternalNftItemEditRequest request) {

        nftItemDao.update(
                ExternalNftItemQuery.newBuilder().id(request.getId()).build(),
                ExternalNftItemUpdate.newBuilder()
                                .setName(request.getName())
                                .setPreviewUrl(request.getPreviewUrl())
                                .setThumbnailUrl(request.getThumbnailUrl())
                                .setRawUrl(request.getRawUrl())
                                .setContractAddress(request.getContactAddress())
                                .setTokenId(request.getTokenId())
                        .build()
        );
    }

    public void delete(ExternalNftItemDeleteRequest request) {
        nftItemDao.remove(ExternalNftItemQuery.newBuilder().id(request.getId()).build());
    }

    public ExternalNftItemDTO detail(ExternalNftItemDeleteRequest request) {

        ExternalNftItemDTO dto = nftItemDao.findOne(ExternalNftItemQuery.newBuilder()
                        .id(request.getId())
                .build(), ExternalNftItemDTO.class);

        return dto;
    }
}
