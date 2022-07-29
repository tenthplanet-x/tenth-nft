package com.tenth.nft.marketplace.dao;

import com.tenth.nft.marketplace.entity.PlayerAssetsBelongs;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/29 11:51
 */
@Component
public class PlayerAssetsBelongsDao extends SimpleDao<PlayerAssetsBelongs> {

    public PlayerAssetsBelongsDao() {
        super(PlayerAssetsBelongs.class);
    }
}