package com.tenth.nft.search.service;

import com.tenth.nft.orm.external.NftCategoryVersions;
import com.tenth.nft.orm.marketplace.dao.NftCategoryDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftCategoryQuery;
import com.tenth.nft.search.dto.CategorySearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shijie
 */
@Service
public class CategorySearchService {

    @Autowired
    private NftCategoryDao nftCategoryDao;

    public List<CategorySearchDTO> getAll() {

        return nftCategoryDao.find(NftCategoryQuery.newBuilder()
                .setVersion(NftCategoryVersions.VERSION)
                .setSortField("order")
                .setReverse(false)
                .build(),
                CategorySearchDTO.class
        );
    }

    /**
     * 缓存重构
     */
    public void rebuildCache(){
        nftCategoryDao.clearCache(NftCategoryVersions.VERSION);
    }
}
