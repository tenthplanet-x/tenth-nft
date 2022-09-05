package com.tenth.nft.exchange.common.service;

import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsUpdate;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tpulse.gs.convention.dao.SimpleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class NftAssetsService {

    @Autowired
    private NftAssetsDao nftAssetsDao;

    public NftAssets findOne(Long assetsId) {
        SimpleQuery query = NftAssetsQuery.newBuilder().id(assetsId).build();
        return nftAssetsDao.findOne(query);
    }

    public void updateMintStatus(Long assetsId, TokenMintStatus status) {
        SimpleQuery query = NftAssetsQuery.newBuilder().id(assetsId).build();
        nftAssetsDao.update(
                query,
                NftAssetsUpdate.newBuilder()
                        .mintStatus(status)
                        .build()
        );
    }
}
