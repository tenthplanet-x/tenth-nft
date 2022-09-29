package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.marketplace.common.dto.NftAseetsOwnerDTO;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
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

/**
 * @author shijie
 */
@Service
public class Web3NftBelongService extends AbsNftBelongService<Web3NftBelong> {

    @Autowired
    private RouteClient routeClient;

    public Web3NftBelongService(
            Web3NftBelongDao nftBelongDao,
            @Lazy Web3NftAssetsService nftAssetsService
            ) {
        super(nftBelongDao, nftAssetsService);
    }

    @Override
    protected void afterQuantityChange(Web3NftBelong nftBelong) {

    }

    public Page<NftAseetsOwnerDTO> ownerList(NftOwnerListRequest request) {
        return ownerList(request, NftAseetsOwnerDTO.class);
    }

    public Page<NftAssetsDTO> myAssets(NftAssetsOwnRequest request) {

        String creator = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(request.getOwner())
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        return myAssets(request, creator, NftAssetsDTO.class);
    }
}
