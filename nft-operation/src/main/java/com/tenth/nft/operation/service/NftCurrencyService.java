package com.tenth.nft.operation.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.CurrencyRebuildRouteRequest;
import com.tenth.nft.operation.dto.NftCurrencyDTO;
import com.tenth.nft.operation.vo.NftCurrencyCreateRequest;
import com.tenth.nft.operation.vo.NftCurrencyDeleteRequest;
import com.tenth.nft.operation.vo.NftCurrencyEditRequest;
import com.tenth.nft.operation.vo.NftCurrencyListRequest;
import com.tenth.nft.orm.external.NftCurrencyVersions;
import com.tenth.nft.orm.marketplace.dao.NftCurrencyNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftCurrencyQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftCurrencyUpdate;
import com.tenth.nft.orm.marketplace.entity.NftCurrency;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                                .order(request.getOrder())
                        .build()
        );

        rebuildCache(request.getBlockchain());
    }

    public void delete(NftCurrencyDeleteRequest request) {
        nftCurrencyDao.remove(NftCurrencyQuery.newBuilder().version(NftCurrencyVersions.VERSION).id(request.getId()).build());
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
}
