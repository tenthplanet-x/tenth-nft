package com.tenth.nft.marketplace.buildin.dao;

import com.tenth.nft.marketplace.buildin.entity.BuildInNftBuyOrder;
import com.tenth.nft.marketplace.common.dao.AbsNftBuyOrderDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInNftOrderDao extends AbsNftBuyOrderDao<BuildInNftBuyOrder> {

    public BuildInNftOrderDao() {
        super(BuildInNftBuyOrder.class);
    }
}
