package com.tenth.nft.marketplace.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.routes.BlockchainRebuildRouteRequest;
import com.tenth.nft.marketplace.dao.NftBlockchainNoCacheDao;
import com.tenth.nft.marketplace.dao.expression.NftBlockchainQuery;
import com.tenth.nft.marketplace.dao.expression.NftBlockchainUpdate;
import com.tenth.nft.marketplace.dto.NftBlockchainDTO;
import com.tenth.nft.marketplace.entity.NftBlockchain;
import com.tenth.nft.marketplace.vo.NftBlockchainCreateRequest;
import com.tenth.nft.marketplace.vo.NftBlockchainDeleteRequest;
import com.tenth.nft.marketplace.vo.NftBlockchainEditRequest;
import com.tenth.nft.marketplace.vo.NftBlockchainListRequest;
import com.tenth.nft.orm.external.NftBlockchainVersions;
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
public class NftBlockchainService {

    @Autowired
    private NftBlockchainNoCacheDao nftBlockchainDao;
    @Autowired
    private RouteClient routeClient;

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
}
