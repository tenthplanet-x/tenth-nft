package com.tenth.nft.marketplace.buildin.dao;

import com.tenth.nft.marketplace.buildin.entity.BuildInNftOffer;
import com.tenth.nft.marketplace.common.dao.AbsNftOfferDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInNftOfferDao extends AbsNftOfferDao<BuildInNftOffer> {

    public BuildInNftOfferDao() {
        super(BuildInNftOffer.class);
    }
}
