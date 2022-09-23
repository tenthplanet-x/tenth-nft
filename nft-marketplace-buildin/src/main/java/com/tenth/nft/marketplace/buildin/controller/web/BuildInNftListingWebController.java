package com.tenth.nft.marketplace.buildin.controller.web;

import com.tenth.nft.marketplace.buildin.BuildInNftAssetsPaths;
import com.tenth.nft.marketplace.buildin.dto.BuildInNftListingDTO;
import com.tenth.nft.marketplace.buildin.service.BuildInNftListingService;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;
import com.tenth.nft.marketplace.common.dto.NftWalletPayTicket;
import com.tenth.nft.marketplace.common.vo.NftListingBuyRequest;
import com.tenth.nft.marketplace.common.vo.NftListingCancelRequest;
import com.tenth.nft.marketplace.common.vo.NftListingCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftListingListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@HttpRoute(userAuth = true)
public class BuildInNftListingWebController {

    @Autowired
    private BuildInNftListingService buildInNftListingService;

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_CREATE)
    public NftListingDTO create(NftListingCreateRequest request){
        return buildInNftListingService.create(request);
    }

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_CANCEL)
    public void cancel(NftListingCancelRequest request){
        buildInNftListingService.cancel(request);
    }

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_LIST)
    public Page<BuildInNftListingDTO> list(NftListingListRequest request){
        return buildInNftListingService.list(request);
    }

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_BUY)
    public NftWalletPayTicket buy(NftListingBuyRequest request){
        return buildInNftListingService.buy(request);
    }

}
