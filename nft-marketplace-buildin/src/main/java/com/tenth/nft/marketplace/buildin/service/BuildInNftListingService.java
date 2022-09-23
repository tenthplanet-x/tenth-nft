package com.tenth.nft.marketplace.buildin.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.wallet.PasswordCheckRouteRequest;
import com.tenth.nft.marketplace.buildin.dao.BuildInNftListingDao;
import com.tenth.nft.marketplace.buildin.dto.BuildInNftListingDTO;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftListing;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;
import com.tenth.nft.marketplace.common.dto.NftWalletPayTicket;
import com.tenth.nft.marketplace.common.service.AbsNftListingService;
import com.tenth.nft.marketplace.common.vo.NftListingBuyRequest;
import com.tenth.nft.marketplace.common.vo.NftListingCancelRequest;
import com.tenth.nft.marketplace.common.vo.NftListingCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftListingListRequest;
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
public class BuildInNftListingService extends AbsNftListingService<BuildInNftListing> {

    @Autowired
    private RouteClient routeClient;

    public BuildInNftListingService(
            BuildInNftAssetsService nftAssetsService,
            BuildInNftListingDao nftListingDao,
            BuildInNftBelongService nftBelongService,
            BuildInNftUbtLogService nftUbtLogService,
            BuildInNftBuyOrderService nftBuyOrderService
            ) {
        super(nftAssetsService, nftListingDao, nftBelongService, nftUbtLogService, nftBuyOrderService);
    }

    @Override
    protected BuildInNftListing newListingEntity() {
        return new BuildInNftListing();
    }

    public NftListingDTO create(NftListingCreateRequest request) {

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
            throw BizException.newInstance(NftExchangeErrorCodes.LISTING_CREATE_EXCEPTION_ILLEGAL_SIGNATURE);
        }

        NftListingDTO dto = super.create(String.valueOf(uid), request);
        BuildInNftListingDTO wrapper = BuildInNftListingDTO.from(dto);
        wrapper.setSellerProfile(
                NftUserProfileDTO.from(
                        routeClient.send(
                                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(Long.valueOf(dto.getSeller())).build(),
                                SearchUserProfileRouteRequest.class
                        ).getProfiles(0)
                )
        );

        return wrapper;
    }

    public void cancel(NftListingCancelRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        super.cancel(String.valueOf(uid), request);
    }

    public Page<BuildInNftListingDTO> list(NftListingListRequest request) {

        Page<BuildInNftListingDTO> dataPage = super.list(request, BuildInNftListingDTO.class);

        if(!dataPage.getData().isEmpty()){
            //Fill with user profile
            Collection<Long> sellerUids = dataPage.getData().stream().map(dto -> Long.valueOf(dto.getSeller())).collect(Collectors.toSet());
            Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
                    Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(sellerUids).build(),
                    SearchUserProfileRouteRequest.class
            ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
            dataPage.getData().stream().forEach(dto -> {
                dto.setSellerProfile(userProfileDTOMap.get(dto.getSeller()));
            });
        }

        return dataPage;

    }

    public NftWalletPayTicket buy(NftListingBuyRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        return super.buy(String.valueOf(uid), request);
    }
}
