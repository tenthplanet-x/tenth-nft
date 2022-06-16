package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.crawler.dao.NftItemDao;
import com.tenth.nft.crawler.dao.expression.NftItemQuery;
import com.tenth.nft.crawler.dao.expression.NftItemUpdate;
import com.tenth.nft.crawler.dto.NftItemDTO;
import com.tenth.nft.crawler.entity.NftItem;
import com.tenth.nft.crawler.vo.NftItemCreateRequest;
import com.tenth.nft.crawler.vo.NftItemDeleteRequest;
import com.tenth.nft.crawler.vo.NftItemEditRequest;
import com.tenth.nft.crawler.vo.NftItemListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Service
public class NftItemService {

    @Autowired
    private NftItemDao nftItemDao;

    public Page<NftItemDTO> list(NftItemListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftItemDTO> dataPage = nftItemDao.findPage(
                NftItemQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(request.getSortField())
                        .setReverse(reverse)
                        .build(),
                NftItemDTO.class
        );

        return dataPage;
    }

    public void create(NftItemCreateRequest request) {

        NftItem nftItem = new NftItem();
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

    public void edit(NftItemEditRequest request) {

        nftItemDao.update(
                NftItemQuery.newBuilder().id(request.getId()).build(),
                NftItemUpdate.newBuilder()
                                .setName(request.getName())
                                .setPreviewUrl(request.getPreviewUrl())
                                .setThumbnailUrl(request.getThumbnailUrl())
                                .setRawUrl(request.getRawUrl())
                                .setContractAddress(request.getContactAddress())
                                .setTokenId(request.getTokenId())
                        .build()
        );
    }

    public void delete(NftItemDeleteRequest request) {
        nftItemDao.remove(NftItemQuery.newBuilder().id(request.getId()).build());
    }

    public NftItemDTO detail(NftItemDeleteRequest request) {

        NftItemDTO dto = nftItemDao.findOne(NftItemQuery.newBuilder()
                        .id(request.getId())
                .build(), NftItemDTO.class);

        return dto;
    }
}
