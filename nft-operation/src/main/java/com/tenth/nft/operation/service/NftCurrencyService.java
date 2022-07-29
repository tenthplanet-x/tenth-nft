package com.tenth.nft.operation.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.CurrencyRebuildRouteRequest;
import com.tenth.nft.operation.dto.BlockchainSearchDTO;
import com.tenth.nft.operation.dto.CurrencySearchDTO;
import com.tenth.nft.operation.dto.NftCurrencyDTO;
import com.tenth.nft.operation.vo.*;
import com.tenth.nft.orm.external.NftCurrencyVersions;
import com.tenth.nft.orm.marketplace.dao.NftCurrencyNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftCurrencyQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftCurrencyUpdate;
import com.tenth.nft.orm.marketplace.entity.NftCurrency;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Service
public class NftCurrencyService {

    @Autowired
    private NftCurrencyNoCacheDao nftCurrencyDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    @Lazy
    private NftBlockchainService nftBlockchainService;

    public Page<NftCurrencyDTO> list(NftCurrencyListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftCurrencyDTO> dataPage = nftCurrencyDao.findPage(
                NftCurrencyQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftCurrencyDTO.class
        );

        return dataPage;
    }

    public void create(NftCurrencyCreateRequest request) {

        NftCurrency nftCurrency = new NftCurrency();
        nftCurrency.setVersion(NftCurrencyVersions.VERSION);
        nftCurrency.setCreatedAt(System.currentTimeMillis());
        nftCurrency.setUpdatedAt(System.currentTimeMillis());
        nftCurrency.setBlockchain(request.getBlockchain());
        nftCurrency.setCode(request.getCode());
        nftCurrency.setLabel(request.getLabel());
        nftCurrency.setEnable(request.getEnable());
        nftCurrency.setOrder(request.getOrder());
        nftCurrency.setExchange(request.getExchange());
        nftCurrency.setBid(request.getBid());
        nftCurrency.setExchangeRate(request.getExchangeRate());
        nftCurrency.setIcon(request.getIcon());
        nftCurrency.setMin(request.getMin());
        nftCurrency.setMax(request.getMax());
        nftCurrency.setMain(request.getMain());
        nftCurrencyDao.insert(nftCurrency);

        rebuildCache(request.getBlockchain());

    }

    public void edit(NftCurrencyEditRequest request) {

        nftCurrencyDao.update(
                NftCurrencyQuery.newBuilder().version(NftCurrencyVersions.VERSION).id(request.getId()).build(),
                NftCurrencyUpdate.newBuilder()
                                .setBlockchain(request.getBlockchain())
                                .setCode(request.getCode())
                                .setLabel(request.getLabel())
                                .setEnable(request.getEnable())
                                .setIcon(request.getIcon())
                                .setExchange(request.getExchange())
                                .setBid(request.getBid())
                                .setMain(request.getMain())
                                .setExchangeRate(request.getExchangeRate())
                                .order(request.getOrder())
                                .setMin(request.getMin())
                                .setMax(request.getMax())
                        .build()
        );

        rebuildCache(request.getBlockchain());
    }

    public void delete(NftCurrencyDeleteRequest request) {
        NftCurrency currency = nftCurrencyDao.findAndRemove(NftCurrencyQuery.newBuilder().version(NftCurrencyVersions.VERSION).id(request.getId()).build());
        rebuildCache(currency.getBlockchain());
    }

    public NftCurrencyDTO detail(NftCurrencyDeleteRequest request) {

        NftCurrencyDTO dto = nftCurrencyDao.findOne(NftCurrencyQuery.newBuilder()
                        .version(NftCurrencyVersions.VERSION)
                        .id(request.getId())
                .build(), NftCurrencyDTO.class);

        return dto;
    }

    private void rebuildCache(String blockchain) {
        routeClient.send(
                NftSearch.NFT_CURRENCY_REBUILD_IC.newBuilder()
                        .setBlockchain(blockchain)
                        .build(),
                CurrencyRebuildRouteRequest.class
        );
    }

    public String getMainCurrency(String blockchain) {
        NftCurrency currency = nftCurrencyDao.findOne(NftCurrencyQuery.newBuilder().version(NftCurrencyVersions.VERSION).blockchain(blockchain).main(true).build());
        return currency.getCode();
    }

    public List<CurrencySearchDTO> listByBlockchain(CurrenySearchRequest request) {

        Map<String, BlockchainSearchDTO> blockchainMapping = nftBlockchainService.listAll().stream().collect(Collectors.toMap(BlockchainSearchDTO::getCode, Function.identity()));

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
