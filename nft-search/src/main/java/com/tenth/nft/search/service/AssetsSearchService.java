package com.tenth.nft.search.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.AssetsExchangeProfileRouteRequest;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.search.dto.AssetsSearchDTO;
import com.tenth.nft.search.lucenedao.NftAssetsLuceneDao;
import com.tenth.nft.search.dto.AssetsDetailSearchDTO;
import com.tenth.nft.search.vo.AssetsDetailSearchRequest;
import com.tenth.nft.search.vo.AssetsSearchRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class AssetsSearchService {

    @Autowired
    private NftAssetsLuceneDao nftAssetsLuceneDao;
    @Autowired
    private NftAssetsDao nftAssetsDao;
    @Autowired
    private RouteClient routeClient;

    public Page<AssetsSearchDTO> list(AssetsSearchRequest request) {

        List<Long> page = nftAssetsLuceneDao.list(request);
        if(!page.isEmpty()){
            List<AssetsSearchDTO> assets = page.stream().map(id -> {
                return nftAssetsDao.findOne(
                        NftAssetsQuery.newBuilder().id(id).build(),
                        AssetsSearchDTO.class
                );
            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    assets
            );
        }

        return new Page<>();
    }

    public void rebuild(Long assetsId){

        nftAssetsDao.clearCache(assetsId);

        NftAssets nftAssets = nftAssetsDao.findOne(NftAssetsQuery.newBuilder().id(assetsId).build());
        nftAssetsLuceneDao.rebuild(nftAssets);
    }

    public AssetsDetailSearchDTO detail(AssetsDetailSearchRequest request) {

        AssetsDetailSearchDTO dto = nftAssetsDao.findOne(
                NftAssetsQuery.newBuilder().id(request.getAssetsId()).build(),
                AssetsDetailSearchDTO.class
        );

        Search.SearchUserDTO searchUserDTO = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder()
                        .addUids(dto.getCreator())
                        .build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0);
        UserProfileDTO userProfileDTO = NftUserProfileDTO.from(searchUserDTO);
        dto.setCreatorProfile(userProfileDTO);

        NftExchange.NftAssetsProfileDTO exchangeProfile = routeClient.send(
                NftExchange.ASSETS_EXCHANGE_PROFILE_IC.newBuilder()
                        .setAssetsId(request.getAssetsId())
                        .build(),
                AssetsExchangeProfileRouteRequest.class
        ).getProfile();
        if(exchangeProfile.hasFloorPrice()){
            dto.setCurrency(exchangeProfile.getCurrency());
            dto.setFloorPrice(Prices.toString(exchangeProfile.getFloorPrice()));
        }
        if(exchangeProfile.hasTotalVolume()){
            dto.setTotalVolume(Prices.toString(exchangeProfile.getTotalVolume()));
        }

        return dto;
    }
}
