package com.tenth.nft.orm.external.dao;

import com.tenth.nft.orm.external.entity.ExternalNftAssets;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Component
public class ExternalNftItemDao extends SimpleDao<ExternalNftAssets> {

    public ExternalNftItemDao() {
        super(ExternalNftAssets.class);
    }
}