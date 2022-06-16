package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.crawler.dao.NftCollectionDao;
import com.tenth.nft.crawler.dao.NftItemDao;
import com.tenth.nft.crawler.dao.expression.NftCollectionQuery;
import com.tenth.nft.crawler.dao.expression.NftCollectionUpdate;
import com.tenth.nft.crawler.dao.expression.NftItemQuery;
import com.tenth.nft.crawler.dto.NftCollectionDTO;
import com.tenth.nft.crawler.dto.NftItemDTO;
import com.tenth.nft.crawler.entity.NftCollection;
import com.tenth.nft.crawler.vo.NftCollectionCreateRequest;
import com.tenth.nft.crawler.vo.NftCollectionDeleteRequest;
import com.tenth.nft.crawler.vo.NftCollectionEditRequest;
import com.tenth.nft.crawler.vo.NftCollectionListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Service
public class NftCollectionService {

    @Autowired
    private NftCollectionDao nftCollectionDao;
    @Autowired
    private NftItemDao nftItemDao;

    public Page<NftCollectionDTO> list(NftCollectionListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftCollectionDTO> dataPage = nftCollectionDao.findPage(
                NftCollectionQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftCollectionDTO.class
        );

        if(!dataPage.getData().isEmpty()){
            dataPage.getData().forEach(collection -> {
                List<NftItemDTO> items = nftItemDao.find(NftItemQuery.newBuilder()
                        .contractAddress(collection.getContractAddress())
                        .setLimit(3)
                        .setReverse(true)
                        .setSortField("tokenNo")
                        .build(), NftItemDTO.class);
                collection.setItems(items);
            });
        }

        return dataPage;
    }

    public void create(NftCollectionCreateRequest request) {

        NftCollection nftCollection = new NftCollection();
        nftCollection.setCreatedAt(System.currentTimeMillis());
        nftCollection.setUpdatedAt(System.currentTimeMillis());
        nftCollection.setName(request.getName());
        nftCollection.setLogoImage(request.getLogoImage());
        nftCollection.setFeaturedImage(request.getFeaturedImage());
        nftCollection.setBannerImage(request.getBannerImage());
        nftCollectionDao.insert(nftCollection);

    }

    public void edit(NftCollectionEditRequest request) {

        nftCollectionDao.update(
                NftCollectionQuery.newBuilder().id(request.getId()).build(),
                NftCollectionUpdate.newBuilder()
                                .setName(request.getName())
                                .setLogoImage(request.getLogoImage())
                                .setFeaturedImage(request.getFeaturedImage())
                                .setBannerImage(request.getBannerImage())
                        .build()
        );
    }

    public void delete(NftCollectionDeleteRequest request) {
        nftCollectionDao.remove(NftCollectionQuery.newBuilder().id(request.getId()).build());
    }

    public NftCollectionDTO detail(NftCollectionDeleteRequest request) {

        NftCollectionDTO dto = nftCollectionDao.findOne(NftCollectionQuery.newBuilder()
                        .id(request.getId())
                .build(), NftCollectionDTO.class);

        return dto;
    }
}
