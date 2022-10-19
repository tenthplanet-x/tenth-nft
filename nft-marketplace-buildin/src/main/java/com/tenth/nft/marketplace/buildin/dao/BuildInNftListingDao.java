package com.tenth.nft.marketplace.buildin.dao;

import com.tenth.nft.marketplace.buildin.entity.BuildInNftListing;
import com.tenth.nft.marketplace.common.dao.AbsNftListingDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInNftListingDao extends AbsNftListingDao<BuildInNftListing> {

    public BuildInNftListingDao() {
        super(BuildInNftListing.class);
    }



}
