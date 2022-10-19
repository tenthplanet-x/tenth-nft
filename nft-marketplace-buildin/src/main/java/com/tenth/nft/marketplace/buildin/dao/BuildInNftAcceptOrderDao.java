package com.tenth.nft.marketplace.buildin.dao;

import com.tenth.nft.marketplace.buildin.entity.BuildInNftAcceptOrder;
import com.tenth.nft.marketplace.common.dao.AbsNftBuyOrderDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInNftAcceptOrderDao extends AbsNftBuyOrderDao<BuildInNftAcceptOrder> {

    public BuildInNftAcceptOrderDao() {
        super(BuildInNftAcceptOrder.class);
    }
}
