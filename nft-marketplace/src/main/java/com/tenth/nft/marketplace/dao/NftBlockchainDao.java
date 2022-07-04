package com.tenth.nft.marketplace.dao;

import com.tenth.nft.marketplace.entity.NftBlockchain;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Component
public class NftBlockchainDao extends SimpleDao<NftBlockchain> {

    public NftBlockchainDao() {
        super(NftBlockchain.class);
    }

    public void clearCache(Integer version) {
        IDaoOperator<NftBlockchain> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<NftBlockchain>) daoOperator).clearCache(version);
        }
    }
}