package com.tenth.nft.marketplace.web3.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletProfileRouteRequest;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class Web3UserProfileService {

    @Autowired
    private RouteClient routeClient;


    public String getUidAddress(Long uid) {
        String seller = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        return seller;
    }

    public NftUserProfileDTO getUserProfile(Long uid) {

        return NftUserProfileDTO.from(
                routeClient.send(
                        Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                .addUids(uid)
                                .build(),
                        SearchUserProfileRouteRequest.class
                ).getProfiles(0)
        );
    }

    public NftUserProfileDTO getUserProfile(String address) {

        Long uid = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_RPOFILE_IC.newBuilder()
                        .addAddresses(address)
                        .build(),
                Web3WalletProfileRouteRequest.class
        ).getProfiles(0).getUid();

        return getUserProfile(uid);
    }

    public Map<String, NftUserProfileDTO> getUserProfiles(Collection<String> addresses) {

        List<NftWeb3Wallet.Web3WalletProfileDTO> walletProfiles = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_RPOFILE_IC.newBuilder()
                        .addAllAddresses(addresses)
                        .build(),
                Web3WalletProfileRouteRequest.class
        ).getProfilesList();

        Map<Long, String> uidAddressMapping = walletProfiles.stream().collect(Collectors.toMap(
                NftWeb3Wallet.Web3WalletProfileDTO::getUid,
                NftWeb3Wallet.Web3WalletProfileDTO::getAddress
        ));

        return routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder()
                        .addAllUids(uidAddressMapping.keySet())
                        .build(),
                SearchUserProfileRouteRequest.class
        ).getProfilesList().stream().collect(Collectors.toMap(
                profile -> uidAddressMapping.get(profile.getUid()),
                profile -> NftUserProfileDTO.from(profile)
        ));

    }
}
