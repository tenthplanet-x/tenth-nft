package com.tenth.nft.marketplace.buildin.service;

import com.tenth.nft.marketplace.buildin.dao.BuildInNftOrderDao;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftBuyOrder;
import com.tenth.nft.marketplace.common.service.AbsNftOrderService;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class BuildInNftBuyOrderService extends AbsNftOrderService<BuildInNftBuyOrder> {

    public BuildInNftBuyOrderService(BuildInNftOrderDao nftBuyOrderDao) {
        super(nftBuyOrderDao);
    }

    @Override
    protected BuildInNftBuyOrder newEntity() {
        return new BuildInNftBuyOrder();
    }
}
