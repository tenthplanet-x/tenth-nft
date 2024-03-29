package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.convention.UnionIds;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.marketplace.common.dto.NftAseetsOwnerDTO;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.dto.NftMyAssetsDTO;
import com.tenth.nft.marketplace.common.service.AbsNftBelongService;
import com.tenth.nft.marketplace.common.vo.NftAssetsDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsOwnRequest;
import com.tenth.nft.marketplace.common.vo.NftOwnerListRequest;
import com.tenth.nft.marketplace.web3.dao.Web3NftBelongDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftBelong;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class Web3NftBelongService extends AbsNftBelongService<Web3NftBelong> {

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3UserProfileService web3UserProfileService;

    public Web3NftBelongService(
            Web3NftBelongDao nftBelongDao,
            @Lazy Web3NftAssetsService nftAssetsService,
            @Lazy Web3NftCollectionService web3NftCollectionService
            ) {
        super(nftBelongDao, nftAssetsService, web3NftCollectionService);
    }

    @Override
    protected void afterQuantityChange(Web3NftBelong nftBelong) {

    }

    public Page<NftAseetsOwnerDTO> ownerList(NftOwnerListRequest request) {

        Page<NftAseetsOwnerDTO> dataPage = ownerList(request, NftAseetsOwnerDTO.class);
        if(!dataPage.getData().isEmpty()){

            Set<String> addresses = dataPage.getData().stream().map(dto -> dto.getUid()).collect(Collectors.toSet());
            Map<String, NftUserProfileDTO> profileMap = web3UserProfileService.getUserProfiles(addresses);

            dataPage.getData().stream().forEach(dto -> {
                dto.setUserProfile(profileMap.get(dto.getUid()));
            });
        }

        return dataPage;
    }

    public Page<NftMyAssetsDTO> myAssets(NftAssetsOwnRequest request) {

        String creator = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(request.getOwner())
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        Page<NftMyAssetsDTO> dataPage = myAssets(request, creator, NftMyAssetsDTO.class);
        if(null != dataPage.getData() && !dataPage.getData().isEmpty()){

            Set<String> addresses = dataPage.getData().stream().map(dto -> dto.getCreator()).collect(Collectors.toSet());
            Map<String, NftUserProfileDTO> profileMap = web3UserProfileService.getUserProfiles(addresses);
            dataPage.getData().stream().forEach(dto -> {
                dto.setCreatorProfile(profileMap.get(dto.getCreator()));
            });
        }
        return dataPage;
    }
}
