package com.tenth.nft.exchange.common.service;

import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderQuery;
import com.tenth.nft.orm.marketplace.entity.NftOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class NftOrderService {

    @Autowired
    private NftOrderDao nftOrderDao;

    public void insert(NftOrder order) {
        nftOrderDao.insert(order);
    }

    public NftOrder getOrder(Long assetsId, Long orderId) {
        return nftOrderDao.findOne(NftOrderQuery.newBuilder()
                        .assetsId(assetsId)
                        .id(orderId)
                .build());
    }
}
