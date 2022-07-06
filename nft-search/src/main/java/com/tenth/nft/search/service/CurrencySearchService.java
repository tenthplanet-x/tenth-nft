package com.tenth.nft.search.service;

import com.tenth.nft.orm.external.NftCurrencyVersions;
import com.tenth.nft.orm.marketplace.dao.NftCurrencyDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftCurrencyQuery;
import com.tenth.nft.protobuf.NftSearch;
import com.tenth.nft.search.dto.BlockchainSearchDTO;
import com.tenth.nft.search.dto.CurrencySearchDTO;
import com.tenth.nft.search.vo.CurrenySearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class CurrencySearchService {

    @Autowired
    private NftCurrencyDao nftCurrencyDao;
    @Autowired
    private BlockchainSearchService blockchainSearchService;

    public void rebuildCache(NftSearch.NFT_CURRENCY_REBUILD_IC request) {
        nftCurrencyDao.clearCache(NftCurrencyVersions.VERSION);
    }

    public List<CurrencySearchDTO> listByBlockchain(CurrenySearchRequest request) {

        Map<String, BlockchainSearchDTO> blockchainMapping = blockchainSearchService.listAll().stream().collect(Collectors.toMap(BlockchainSearchDTO::getCode, Function.identity()));

        List<CurrencySearchDTO> dtos = nftCurrencyDao.find(NftCurrencyQuery.newBuilder()
                        .blockchain(request.getBlockchain())
                        .setSortField("order")
                        .setReverse(false)
                        .build(),
                CurrencySearchDTO.class
        );

        dtos.forEach(dto -> {
            String blockchain = dto.getBlockchain();
            String blockchainLabel = blockchainMapping.get(blockchain).getLabel();
            dto.setBlockchainLabel(blockchainLabel);
        });

        return dtos;
    }
}
