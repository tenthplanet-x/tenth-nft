package com.tenth.nft.marketplace.dao;

import com.tenth.nft.marketplace.entity.PlayerCollection;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/28 19:03
 */
@Component
public class PlayerCollectionDao extends SimpleDao<PlayerCollection> {

    public PlayerCollectionDao() {
        super(PlayerCollection.class);
    }
}