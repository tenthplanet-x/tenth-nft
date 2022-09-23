package com.tenth.nft.marketplace.buildin.service;

import com.tenth.nft.marketplace.buildin.dao.BuildInNftBuyOrderDao;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftBuyOrder;
import com.tenth.nft.marketplace.common.service.AbsNftBuyOrderService;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class BuildInNftBuyOrderService extends AbsNftBuyOrderService<BuildInNftBuyOrder> {

    public BuildInNftBuyOrderService(BuildInNftBuyOrderDao nftBuyOrderDao) {
        super(nftBuyOrderDao);
    }

    @Override
    protected BuildInNftBuyOrder newEntity() {
        return new BuildInNftBuyOrder();
    }
}
