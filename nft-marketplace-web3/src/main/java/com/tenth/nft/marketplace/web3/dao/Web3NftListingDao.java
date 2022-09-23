package com.tenth.nft.marketplace.web3.dao;

import com.tenth.nft.marketplace.common.dao.AbsNftListingDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftListing;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3NftListingDao extends AbsNftListingDao<Web3NftListing> {

    public Web3NftListingDao() {
        super(Web3NftListing.class);
    }
}
