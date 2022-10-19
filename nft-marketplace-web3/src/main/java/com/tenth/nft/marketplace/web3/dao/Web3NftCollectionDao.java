package com.tenth.nft.marketplace.web3.dao;

import com.tenth.nft.marketplace.common.dao.AbsNftCollectionDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftCollection;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3NftCollectionDao extends AbsNftCollectionDao<Web3NftCollection> {

    public Web3NftCollectionDao() {
        super(Web3NftCollection.class);
    }

}
