package com.tenth.nft.marketplace.dao;

import com.tenth.nft.marketplace.entity.PlayerAssets;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/28 19:03
 */
@Component
public class PlayerAssetsDao extends SimpleDao<PlayerAssets> {

    public PlayerAssetsDao() {
        super(PlayerAssets.class);
    }
}