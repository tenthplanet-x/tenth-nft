package com.tenth.nft.search.service;

import com.tenth.nft.orm.external.NftBlockchainVersions;
import com.tenth.nft.orm.marketplace.dao.NftBlockchainDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftBlockchainQuery;
import com.tenth.nft.search.dto.BlockchainSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shijie
 */
@Service
public class BlockchainSearchService {

    @Autowired
    private NftBlockchainDao nftBlockchainDao;

    public void rebuildCache() {
        nftBlockchainDao.clearCache(NftBlockchainVersions.VERSION);
    }

    public List<BlockchainSearchDTO> listAll() {
        return nftBlockchainDao.find(NftBlockchainQuery.newBuilder()
                        .version(NftBlockchainVersions.VERSION)
                        .setSortField("order")
                        .setReverse(false)
                        .build(),
                BlockchainSearchDTO.class
        );
    }

}
