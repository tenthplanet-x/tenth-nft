package com.tenth.nft.crawler.dao;

import com.tenth.nft.crawler.entity.NftCategory;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Component
public class NftCategoryDao extends SimpleDao<NftCategory> {

    public NftCategoryDao() {
        super(NftCategory.class);
    }
}