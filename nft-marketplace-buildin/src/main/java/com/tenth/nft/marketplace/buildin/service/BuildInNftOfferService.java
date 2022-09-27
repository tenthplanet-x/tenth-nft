package com.tenth.nft.marketplace.buildin.service;


import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.wallet.PasswordCheckRouteRequest;
import com.tenth.nft.convention.routes.wallet.WalletPayRouteRequest;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.marketplace.buildin.dao.BuildInNftOfferDao;
import com.tenth.nft.marketplace.buildin.dto.BuildInNftOfferDTO;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftOffer;
import com.tenth.nft.marketplace.common.dto.NftOfferDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tenth.nft.marketplace.common.entity.AbsNftOrder;
import com.tenth.nft.marketplace.common.service.AbsNftOfferService;
import com.tenth.nft.marketplace.common.vo.NftMakeOfferRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferAcceptRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferCancelRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferListRequest;
import com.tenth.nft.marketplace.common.wallet.IWalletProvider;
import com.tenth.nft.marketplace.common.wallet.WalletProviderFactory;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class BuildInNftOfferService extends AbsNftOfferService<BuildInNftOffer> {

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private WalletProviderFactory walletProviderFactory;
    @Autowired
    private BuildInNftAssetsService nftAssetsService;

    public BuildInNftOfferService(
            BuildInNftOfferDao nftOfferDao,
            BuildInNftAssetsService nftAssetsService,
            BuildInNftBelongService nftBelongService,
            BuildInNftUbtLogService nftUbtLogService,
            BuildInNftAcceptOrderService nftAcceptOrderService,
            RouteClient routeClient) {
        super(nftOfferDao, nftAssetsService, nftBelongService, nftUbtLogService, nftAcceptOrderService, routeClient);
    }

    @Override
    protected BuildInNftOffer newNftOffer() {
        return new BuildInNftOffer();
    }

    public Page<BuildInNftOfferDTO> list(NftOfferListRequest request) {

        Page<BuildInNftOfferDTO> dataPage = list(request, BuildInNftOfferDTO.class);

        if(!dataPage.getData().isEmpty()){
            Collection<Long> sellerUids = dataPage.getData().stream().map(dto -> Long.valueOf(dto.getBuyer())).collect(Collectors.toSet());
            Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
                    Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(sellerUids).build(),
                    SearchUserProfileRouteRequest.class
            ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
            dataPage.getData().stream().forEach(dto -> {
                dto.setUserProfile(userProfileDTOMap.get(dto.getBuyer()));
            });
        }

        return dataPage;

    }

    public NftOfferDTO makeOffer(NftMakeOfferRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        //signature check
        boolean success = routeClient.send(
                NftWallet.PASSWORD_CHECK_IC.newBuilder()
                        .setUid(uid)
                        .setPassword(request.getSignature())
                        .build(),
                PasswordCheckRouteRequest.class
        ).getSuccess();
        if(!success){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INVALID_SIGNATURE);
        }

        BuildInNftOfferDTO dto = (BuildInNftOfferDTO)makeOffer(String.valueOf(uid), request);
        dto.setUserProfile(
                NftUserProfileDTO.from(
                        routeClient.send(
                                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(Long.valueOf(dto.getBuyer())).build(),
                                SearchUserProfileRouteRequest.class
                        ).getProfiles(0)
                )
        );
        return dto;
    }

    @Override
    protected NftOfferDTO newDTO() {
        return new BuildInNftOfferDTO();
    }

    public void cancel(NftOfferCancelRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        cancel(String.valueOf(uid), request);
    }

    public Long accept(NftOfferAcceptRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String seller = String.valueOf(uid);
        AbsNftOrder order = accept(String.valueOf(uid), request);
        AbsNftAssets nftAssets = nftAssetsService.findOne(request.getAssetsId());
        BuildInNftOffer nftOffer = findOne(request.getAssetsId(), request.getOfferId());
        afterCreateAcceptOrder(
                seller,
                nftAssets,
                nftOffer,
                order
        );
        return order.getId();
    }


    protected void afterCreateAcceptOrder(String seller, AbsNftAssets nftAssets, BuildInNftOffer nftOffer, AbsNftOrder order) {

        //Get pay channel and token
        WalletOrderBizContent walletOrder = WalletOrderBizContent.newBuilder()
                .activityCfgId(WalletOrderType.NftExpense.getActivityCfgId())
                .productCode(WalletProductCode.NFT_OFFER.name())
                .productId(String.valueOf(nftOffer.getAssetsId()))
                .outOrderId(String.valueOf(order.getId()))
                .merchantType(WalletMerchantType.PERSONAL.name())
                .merchantId(seller)
                .currency(nftOffer.getCurrency())
                .value(nftOffer.getPrice())
                .expiredAt(order.getExpiredAt())
                .remark("")
                .profits(createProfits(seller, nftOffer))
                .build();

        IWalletProvider walletProvider = walletProviderFactory.get(nftAssets.getBlockchain());
        String token = walletProvider.createToken(walletOrder);

        //Delegate payment to system
        NftWallet.BillDTO billDTO = routeClient.send(
                NftWallet.BILL_PAY_IC.newBuilder()
                        .setUid(Long.valueOf(nftOffer.getBuyer()))
                        .setToken(token)
                        .setPassword("")
                        .build(),
                WalletPayRouteRequest.class
        ).getBill();
    }


}
