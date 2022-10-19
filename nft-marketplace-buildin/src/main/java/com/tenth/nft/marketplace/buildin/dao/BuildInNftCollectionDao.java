package com.tenth.nft.marketplace.buildin.dao;

import com.tenth.nft.marketplace.buildin.entity.BuildInNftCollection;
import com.tenth.nft.marketplace.common.dao.AbsNftCollectionDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInNftCollectionDao extends AbsNftCollectionDao<BuildInNftCollection> {

    public BuildInNftCollectionDao() {
        super(BuildInNftCollection.class);
    }
}
