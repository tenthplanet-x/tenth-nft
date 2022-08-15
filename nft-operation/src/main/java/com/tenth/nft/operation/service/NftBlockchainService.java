package com.tenth.nft.operation.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.BlockchainRebuildRouteRequest;
import com.tenth.nft.operation.dto.BlockchainSearchDTO;
import com.tenth.nft.operation.dto.CurrencySearchDTO;
import com.tenth.nft.operation.vo.*;
import com.tenth.nft.orm.marketplace.dao.NftBlockchainNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftBlockchainQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftBlockchainUpdate;
import com.tenth.nft.operation.dto.NftBlockchainDTO;
import com.tenth.nft.orm.marketplace.entity.NftBlockchain;
import com.tenth.nft.orm.external.NftBlockchainVersions;
import com.tenth.nft.protobuf.NftOperation;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Service
public class NftBlockchainService {

    @Autowired
    private NftBlockchainNoCacheDao nftBlockchainDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftCurrencyService nftCurrencyService;

    public Page<NftBlockchainDTO> list(NftBlockchainListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftBlockchainDTO> dataPage = nftBlockchainDao.findPage(
                NftBlockchainQuery.newBuilder()
                        .code(request.getCode())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftBlockchainDTO.class
        );

        return dataPage;
    }

    public void create(NftBlockchainCreateRequest request) {

        NftBlockchain nftBlockchain = new NftBlockchain();
        nftBlockchain.setCreatedAt(System.currentTimeMillis());
        nftBlockchain.setUpdatedAt(System.currentTimeMillis());
        nftBlockchain.setCode(request.getCode());
        nftBlockchain.setLabel(request.getLabel());
        nftBlockchain.setEnable(request.getEnable());
        nftBlockchain.setOrder(request.getOrder());
        nftBlockchain.setIcon(request.getIcon());
        nftBlockchainDao.insert(nftBlockchain);

        rebuildCache();

    }

    public void edit(NftBlockchainEditRequest request) {

        nftBlockchainDao.update(
                NftBlockchainQuery.newBuilder().id(request.getId()).build(),
                NftBlockchainUpdate.newBuilder()
                                .setCode(request.getCode())
                                .setLabel(request.getLabel())
                                .setEnable(request.getEnable())
                                .setIcon(request.getIcon())
                        .order(request.getOrder())
                        .build()
        );

        rebuildCache();
    }

    public void delete(NftBlockchainDeleteRequest request) {
        nftBlockchainDao.remove(NftBlockchainQuery.newBuilder().id(request.getId()).build());
    }

    public NftBlockchainDTO detail(NftBlockchainDeleteRequest request) {

        NftBlockchainDTO dto = nftBlockchainDao.findOne(NftBlockchainQuery.newBuilder()
                        .id(request.getId())
                .build(), NftBlockchainDTO.class);

        return dto;
    }

    private void rebuildCache() {
        routeClient.send(
                NftSearch.NFT_BLOCKCHAIN_REBUILD_IC.newBuilder()
                        .setVersion(NftBlockchainVersions.VERSION)
                        .build(),
                BlockchainRebuildRouteRequest.class
        );
    }

    public NftOperation.NFT_BLOCKCHAIN_IS detail(NftOperation.NFT_BLOCKCHAIN_IC request) {

        String mainCurrency = nftCurrencyService.getMainCurrency(request.getBlockchain());

        CurrenySearchRequest currenySearchRequest = new CurrenySearchRequest();
        currenySearchRequest.setBlockchain(request.getBlockchain());
        List<NftOperation.CurrencyDTO> currencyDTOS = nftCurrencyService.listByBlockchain(currenySearchRequest).stream().map(CurrencySearchDTO::toProto).collect(Collectors.toList());
        return NftOperation.NFT_BLOCKCHAIN_IS.newBuilder()
                .setBlockchain(NftOperation.BlockchainDTO.newBuilder()
                        .setMainCurrency(mainCurrency)
                        .addAllCurrencies(currencyDTOS)
                        .build())
                .build();

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
