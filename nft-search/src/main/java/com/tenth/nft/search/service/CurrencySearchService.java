package com.tenth.nft.search.service;

import com.tenth.nft.orm.external.NftCurrencyVersions;
import com.tenth.nft.orm.marketplace.dao.CurrencyRateDao;
import com.tenth.nft.orm.marketplace.dao.NftCurrencyDao;
import com.tenth.nft.orm.marketplace.dao.expression.CurrencyRateQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftCurrencyQuery;
import com.tenth.nft.orm.marketplace.entity.CurrencyRate;
import com.tenth.nft.orm.marketplace.entity.NftCurrency;
import com.tenth.nft.protobuf.NftSearch;
import com.tenth.nft.search.dto.BlockchainSearchDTO;
import com.tenth.nft.search.dto.CurrencyRateSearchDTO;
import com.tenth.nft.search.dto.CurrencySearchDTO;
import com.tenth.nft.search.vo.CurrenyRateRequest;
import com.tenth.nft.search.vo.CurrenySearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    @Autowired
    private CurrencyRateDao currencyRateDao;
    @Value("${game.language:en_US}")
    private String language;

    public void rebuildCache(NftSearch.NFT_CURRENCY_REBUILD_IC request) {
        nftCurrencyDao.clearCache(NftCurrencyVersions.VERSION);
    }

    public List<CurrencySearchDTO> listByBlockchain(CurrenySearchRequest request) {

        Map<String, BlockchainSearchDTO> blockchainMapping = blockchainSearchService.listAll().stream().collect(Collectors.toMap(BlockchainSearchDTO::getCode, Function.identity()));

        List<CurrencySearchDTO> dtos = nftCurrencyDao.find(NftCurrencyQuery.newBuilder()
                        .version(NftCurrencyVersions.VERSION)
                        .blockchain(request.getBlockchain())
                        .setSortField("order")
                        .setReverse(false)
                        .build(),
                CurrencySearchDTO.class
        );

        dtos.forEach(dto -> {
            String blockchain = dto.getBlockchain();
            BlockchainSearchDTO blockchainSearchDTO = blockchainMapping.get(blockchain);
            dto.setBlockchainLabel(blockchainSearchDTO.getLabel());
            dto.setBlockchainIcon(blockchainSearchDTO.getIcon());

        });

        return dtos;
    }

    public CurrencyRateSearchDTO rate(CurrenyRateRequest request) {
        return currencyRateDao.findOne(
                CurrencyRateQuery.newBuilder().token(request.getToken()).country(request.getCountry()).build(),
                CurrencyRateSearchDTO.class
        );
    }

    public void currencyRateReBuild(NftSearch.NFT_CURRENCY_RATE_REBUILD_IC request) {
        currencyRateDao.clearCache(request.getToken());
    }

    public String getMainCurrency(String blockchain) {
        Optional<NftCurrency> opt = nftCurrencyDao.find(NftCurrencyQuery.newBuilder()
                        .version(NftCurrencyVersions.VERSION)
                        .blockchain(blockchain)
                        .main(true)
                        .build()).stream().findFirst();

        return opt.get().getCode();
    }

    public NftSearch.NFT_CURRENCY_RATES_IS currencyRates(NftSearch.NFT_CURRENCY_RATES_IC request) {

        Map<String, Float> rates = nftCurrencyDao.find(NftCurrencyQuery.newBuilder()
                        .version(NftCurrencyVersions.VERSION)
                        .blockchain(request.getBlockchain())
                        .build()
        ).stream().collect(Collectors.toMap(NftCurrency::getCode, NftCurrency::getExchangeRate));

        return NftSearch.NFT_CURRENCY_RATES_IS.newBuilder()
                .putAllRates(rates)
                .build();

    }
}
