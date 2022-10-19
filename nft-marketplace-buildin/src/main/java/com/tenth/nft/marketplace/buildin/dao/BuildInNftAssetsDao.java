package com.tenth.nft.marketplace.buildin.dao;

import com.tenth.nft.marketplace.buildin.entity.BuildInNftAssets;
import com.tenth.nft.marketplace.common.dao.AbsNftAssetsDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInNftAssetsDao extends AbsNftAssetsDao<BuildInNftAssets> {

    public BuildInNftAssetsDao() {
        super(BuildInNftAssets.class);
    }

}
