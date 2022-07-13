package com.tenth.nft.operation.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.search.CurrencyRateRebuildRouteRequest;
import com.tenth.nft.operation.dto.CurrencyRateDTO;
import com.tenth.nft.operation.vo.*;
import com.tenth.nft.orm.marketplace.dao.CurrencyRateDao;
import com.tenth.nft.orm.marketplace.dao.CurrencyRateNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.CurrencyRateQuery;
import com.tenth.nft.orm.marketplace.dao.expression.CurrencyRateUpdate;
import com.tenth.nft.orm.marketplace.entity.CurrencyRate;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
@Service
public class CurrencyRateService {

    @Autowired
    private CurrencyRateNoCacheDao currencyRateDao;
    @Autowired
    private RouteClient routeClient;

    public Page<CurrencyRateDTO> list(CurrencyRateListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<CurrencyRateDTO> dataPage = currencyRateDao.findPage(
                CurrencyRateQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                CurrencyRateDTO.class
        );

        return dataPage;
    }

    public void create(CurrencyRateCreateRequest request) {

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCreatedAt(System.currentTimeMillis());
        currencyRate.setUpdatedAt(System.currentTimeMillis());
        currencyRate.setToken(request.getToken());
        currencyRate.setUnit(request.getUnit());
        currencyRate.setCurrency(request.getCurrency());
        currencyRate.setRate(request.getRate());
        currencyRate.setCountry(request.getCountry());
        currencyRate.setPrecision(request.getPrecision());
        currencyRateDao.insert(currencyRate);

        rebuildCache(request.getToken());

    }

    public void edit(CurrencyRateEditRequest request) {

        CurrencyRate currencyRate = currencyRateDao.findAndModify(
                CurrencyRateQuery.newBuilder().id(request.getId()).build(),
                CurrencyRateUpdate.newBuilder()
                                .setToken(request.getToken())
                                .setUnit(request.getUnit())
                                .setCurrency(request.getCurrency())
                                .setRate(request.getRate())
                                .setCountry(request.getCountry())
                                .setPrecision(request.getPrecision())
                        .build(),
                UpdateOptions.options().returnNew(true)
        );

        rebuildCache(currencyRate.getToken());
    }

    public void delete(CurrencyRateDeleteRequest request) {
        CurrencyRate currencyRate = currencyRateDao.findAndRemove(CurrencyRateQuery.newBuilder().id(request.getId()).build());
        rebuildCache(currencyRate.getToken());
    }

    public CurrencyRateDTO detail(CurrencyRateDetailRequest request) {

        CurrencyRateDTO dto = currencyRateDao.findOne(CurrencyRateQuery.newBuilder()
                        .id(request.getId())
                .build(), CurrencyRateDTO.class);

        return dto;
    }

    private void rebuildCache(String token) {
        routeClient.send(
                NftSearch.NFT_CURRENCY_RATE_REBUILD_IC.newBuilder()
                        .setToken(token)
                        .build(),
                CurrencyRateRebuildRouteRequest.class
        );
    }
}
