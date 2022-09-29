package com.tenth.nft.marketplace.stats.service;

import com.tenth.nft.marketplace.stats.dao.NftAssetsExchangeLogDao;
import com.tenth.nft.marketplace.stats.entity.NftAssetsExchangeLog;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author shijie
 */
@Service
public class NftAssetsExchangeLogService {

    @Autowired
    private NftAssetsExchangeLogDao nftAssetsExchangeLogDao;

    public void insert(NftMarketplaceStats.EXCHANGE_LOG_IC request) {

        NftAssetsExchangeLog log = new NftAssetsExchangeLog();
        log.setBlockchain(request.getLog().getBlockchain());
        log.setCollectionId(request.getLog().getCollectionId());
        log.setAssetsId(request.getLog().getAssetsId());
        log.setPrice(new BigDecimal(request.getLog().getPrice()));
        log.setExchangedAt(request.getLog().getExchangedAt());
        log.setQuantity(request.getLog().getQuantity());
        log.setCreatedAt(System.currentTimeMillis());
        log.setUpdatedAt(log.getCreatedAt());
        nftAssetsExchangeLogDao.insert(log);

    }

}
