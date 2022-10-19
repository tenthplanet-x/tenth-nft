package com.tenth.nft.marketplace.buildin.dao;

import com.tenth.nft.marketplace.buildin.entity.BuildInNftUbtLog;
import com.tenth.nft.marketplace.common.dao.AbsNftUbtLogDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInNftUbtLogDao extends AbsNftUbtLogDao<BuildInNftUbtLog> {

    public BuildInNftUbtLogDao() {
        super(BuildInNftUbtLog.class);
    }
}
