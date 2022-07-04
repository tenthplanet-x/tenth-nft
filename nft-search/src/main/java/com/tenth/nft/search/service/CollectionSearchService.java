package com.tenth.nft.search.service;

import com.tenth.nft.orm.external.dao.NftCollectionCacheDao;
import com.tenth.nft.orm.external.dao.NftItemCacheDao;
import com.tenth.nft.orm.external.dao.expression.ExternalNftCollectionQuery;
import com.tenth.nft.orm.external.dao.expression.ExternalNftItemQuery;
import com.tenth.nft.orm.external.entity.ExternalNftAssets;
import com.tenth.nft.orm.external.entity.ExternalNftCollection;
import com.tenth.nft.search.dto.CollectionSearchDTO;
import com.tenth.nft.search.dto.ItemSearchDTO;
import com.tenth.nft.search.dto.SearchCollectionListRequest;
import com.tenth.nft.search.lucene.NftCollectionLuceneDao;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class CollectionSearchService {

    @Autowired
    private NftCollectionLuceneDao nftCollectionLuceneDao;
    @Autowired
    private NftCollectionCacheDao nftCollectionCacheDao;
    @Autowired
    private NftItemCacheDao nftItemCacheDao;

    public Page<CollectionSearchDTO> list(SearchCollectionListRequest request) {

        List<Long> page = nftCollectionLuceneDao.list(request);
        if(!page.isEmpty()){
            List<ExternalNftCollection> collections = page.stream().map(id -> {
                return nftCollectionCacheDao.findOne(
                        ExternalNftCollectionQuery.newBuilder().id(id).build()
                );
            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    collections.stream().map(this::convertToDTO).collect(Collectors.toList())
            );
        }

        return new Page<>();
    }

    /**
     * 1. Make the current cache of the collection invalidate
     * 2. Reuild lucene indexes of the collection
     * @param collectionId
     */
    public void rebuild(Long collectionId){

        nftCollectionCacheDao.clearCache(collectionId);

        ExternalNftCollection nftCollection = nftCollectionCacheDao.findOne(ExternalNftCollectionQuery.newBuilder().id(collectionId).build());
        nftCollectionLuceneDao.rebuild(nftCollection);
    }

    /**
     * 1. Make current cache of items expired
     * @param collectionId
     */
    public void rebuildItems(Long collectionId){

        ExternalNftCollection collection = nftCollectionCacheDao.findOne(ExternalNftCollectionQuery.newBuilder().id(collectionId).build());
        nftItemCacheDao.clearCache(collection.getContractAddress());

    }

    private CollectionSearchDTO convertToDTO(ExternalNftCollection nftCollection) {

        CollectionSearchDTO dto = new CollectionSearchDTO();
        dto.setId(nftCollection.getId());
        dto.setName(nftCollection.getName());
        dto.setLogoImage(nftCollection.getLogoImage());
        dto.setFeaturedImage(nftCollection.getFeaturedImage());
        dto.setBannerImage(nftCollection.getBannerImage());
        List<ItemSearchDTO> items = nftItemCacheDao.find(ExternalNftItemQuery.newBuilder()
                .contractAddress(nftCollection.getContractAddress())
                        .setLimit(3)
                        .setSortField("tokenNo")
                        .setReverse(true)
                .build()
        ).stream().map(this::convertToDTO).collect(Collectors.toList());
        dto.setItems(items);
        dto.setTotalVolume(nftCollection.getTotalVolume());

        return dto;
    }

    private ItemSearchDTO convertToDTO(ExternalNftAssets item){
        ItemSearchDTO dto = new ItemSearchDTO();
        dto.setName(item.getName());
        dto.setUrl(item.getPreviewUrl());
        return dto;
    }
}
