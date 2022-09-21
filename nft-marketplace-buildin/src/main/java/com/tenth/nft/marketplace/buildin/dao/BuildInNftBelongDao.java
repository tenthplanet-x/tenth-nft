package com.tenth.nft.marketplace.buildin.dao;

import com.tenth.nft.marketplace.buildin.entity.BuildInNftBelong;
import com.tenth.nft.marketplace.common.dao.AbsNftBelongDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInNftBelongDao extends AbsNftBelongDao<BuildInNftBelong> {

    public BuildInNftBelongDao() {
        super(BuildInNftBelong.class);
    }
}
