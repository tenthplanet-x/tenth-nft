package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.marketplace.common.dao.AbsNftCollectionDao;
import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.service.AbsNftCollectionService;
import com.tenth.nft.marketplace.common.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionListRequest;
import com.tenth.nft.marketplace.web3.entity.Web3NftCollection;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author shijie
 */
@Service
public class Web3NftCollectionService extends AbsNftCollectionService<Web3NftCollection> {

    @Autowired
    private RouteClient routeClient;

    public Web3NftCollectionService(AbsNftCollectionDao<Web3NftCollection> nftCollectionDao) {
        super(nftCollectionDao);
    }

    @Override
    protected Web3NftCollection newCollection() {
        return new Web3NftCollection();
    }

    @Override
    protected void afterInsert(AbsNftCollection collection) {
        rebuild(collection.getId());
    }

    private void rebuild(Long collectionId) {
        routeClient.send(
                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
                        .setCollectionId(collectionId)
                        .build(),
                CollectionRebuildRouteRequest.class
        );
    }

    public Page<NftCollectionDTO> list(NftCollectionListRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        return list(request, Optional.of(String.valueOf(uid)), NftCollectionDTO.class);
    }

    public NftCollectionDTO detail(NftCollectionDetailRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        return detail(request, NftCollectionDTO.class);
    }
}
