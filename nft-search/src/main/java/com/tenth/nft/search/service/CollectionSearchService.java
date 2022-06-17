package com.tenth.nft.search.service;

import com.tenth.nft.orm.dao.NftCategoryDao;
import com.tenth.nft.orm.dao.NftCollectionCacheDao;
import com.tenth.nft.orm.dao.NftItemCacheDao;
import com.tenth.nft.orm.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.dao.expression.NftItemQuery;
import com.tenth.nft.orm.entity.NftCollection;
import com.tenth.nft.orm.entity.NftItem;
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
            List<NftCollection> collections = page.stream().map(id -> {
                return nftCollectionCacheDao.findOne(
                        NftCollectionQuery.newBuilder().id(id).build()
                );
            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    collections.stream().map(this::convertToDTO).collect(Collectors.toList())
            );
        }

        return new Page<>();
    }

    private CollectionSearchDTO convertToDTO(NftCollection nftCollection) {

        CollectionSearchDTO dto = new CollectionSearchDTO();
        dto.setName(nftCollection.getName());
        dto.setLogoImage(nftCollection.getLogoImage());
        dto.setFeaturedImage(nftCollection.getFeaturedImage());
        dto.setBannerImage(nftCollection.getBannerImage());
        List<ItemSearchDTO> items = nftItemCacheDao.find(NftItemQuery.newBuilder()
                .contractAddress(nftCollection.getContractAddress())
                        .setLimit(3)
                        .setSortField("tokenNo")
                        .setReverse(true)
                .build()
        ).stream().map(this::convertToDTO).collect(Collectors.toList());
        dto.setItems(items);

        return dto;
    }

    private ItemSearchDTO convertToDTO(NftItem item){
        ItemSearchDTO dto = new ItemSearchDTO();
        dto.setName(item.getName());
        dto.setUrl(item.getPreviewUrl());
        return dto;
    }
}
