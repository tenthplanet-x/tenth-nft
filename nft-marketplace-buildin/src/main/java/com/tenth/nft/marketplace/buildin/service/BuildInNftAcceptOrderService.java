package com.tenth.nft.marketplace.buildin.service;

import com.tenth.nft.marketplace.buildin.dao.BuildInNftAcceptOrderDao;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftAcceptOrder;
import com.tenth.nft.marketplace.common.service.AbsNftOrderService;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class BuildInNftAcceptOrderService extends AbsNftOrderService<BuildInNftAcceptOrder> {

    public BuildInNftAcceptOrderService(BuildInNftAcceptOrderDao nftAcceptOrderDao) {
        super(nftAcceptOrderDao);
    }

    @Override
    protected BuildInNftAcceptOrder newEntity() {
        return new BuildInNftAcceptOrder();
    }
}
