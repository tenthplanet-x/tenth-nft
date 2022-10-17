package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.UnionIds;
import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.service.AbsNftCollectionService;
import com.tenth.nft.marketplace.common.vo.NftCollectionCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionListRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionOwnListRequest;
import com.tenth.nft.marketplace.web3.dao.Web3NftCollectionDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftCollection;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author shijie
 */
@Service
public class Web3NftCollectionService extends AbsNftCollectionService<Web3NftCollection> {

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3UserProfileService web3UserProfileService;

    public Web3NftCollectionService(
            Web3NftCollectionDao nftCollectionDao,
            @Lazy Web3NftAssetsService web3NftAssetsService
    ) {
        super(nftCollectionDao, web3NftAssetsService);
    }

    public NftCollectionDTO create(NftCollectionCreateRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String creator = getUidAddress(uid);
        NftCollectionDTO collectionDTO = create(creator, request, NftCollectionDTO.class);
        return collectionDTO;
    }

    @Override
    protected Web3NftCollection newCollection() {
        return new Web3NftCollection();
    }

    @Override
    protected void afterInsert(AbsNftCollection collection) {
//        rebuild(collection.getId());
    }

    private void rebuild(Long collectionId) {
//        routeClient.send(
//                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
//                        .setCollectionId(collectionId)
//                        .build(),
//                CollectionRebuildRouteRequest.class
//        );
    }

    public Page<NftCollectionDTO> list(NftCollectionListRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String address = getUidAddress(uid);

        Page<NftCollectionDTO> dataPage = list(request, Optional.of(address), NftCollectionDTO.class);
        if(!dataPage.getData().isEmpty()){
            NftUserProfileDTO creatorProfileDTO = web3UserProfileService.getUserProfile(uid);
            dataPage.getData().forEach(dto -> {
                dto.setCreatorProfile(creatorProfileDTO);
            });
        }

        return dataPage;
    }

    public NftCollectionDTO detail(NftCollectionDetailRequest request) {
        NftCollectionDTO collectionDTO = detail(request, NftCollectionDTO.class);
        collectionDTO.setCreatorProfile(web3UserProfileService.getUserProfile(collectionDTO.getCreator()));
        return collectionDTO;
    }

    protected String getUidAddress(Long uid) {
        String seller = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        return seller;
    }


    public Page<NftCollectionDTO> ownList(NftCollectionOwnListRequest request) {

        String address = getUidAddress(request.getOwner());
        return list(request, Optional.of(address), NftCollectionDTO.class);

    }

    @Override
    protected String getUnionId(Long id) {
        return UnionIds.wrap(UnionIds.CHANNEL_WEB3, id);
    }
}
