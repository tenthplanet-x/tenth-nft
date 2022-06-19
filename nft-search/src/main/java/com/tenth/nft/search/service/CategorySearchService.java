package com.tenth.nft.search.service;

import com.tenth.nft.orm.dao.NftCategoryDao;
import com.tenth.nft.orm.dao.expression.NftCategoryQuery;
import com.tenth.nft.orm.entity.NftCategory;
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
        nftCategoryDao.clearCache(NftCategory.VERSION);
    }
}
