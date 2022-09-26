package com.tenth.nft.marketplace.web3.controller.web;

import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;
import com.tenth.nft.marketplace.common.dto.NftWalletPayTicket;
import com.tenth.nft.marketplace.common.vo.*;
import com.tenth.nft.marketplace.web3.Web3NftAssetsPaths;
import com.tenth.nft.marketplace.web3.dto.WebListingSignTicket;
import com.tenth.nft.marketplace.web3.service.Web3NftBuyOrderService;
import com.tenth.nft.marketplace.web3.service.Web3NftListingService;
import com.tenth.nft.marketplace.web3.vo.Web3ListingCreateConfirmRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@HttpRoute(userAuth = true)
public class Web3NftListingWebController {

    @Autowired
    private Web3NftListingService nftListingService;
    @Autowired
    private Web3NftBuyOrderService nftBuyOrderService;

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_CREATE)
    public WebListingSignTicket create(NftListingCreateRequest request) throws Exception{
        return nftListingService.create(request);
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_CREATE_CONFIRM)
    public NftListingDTO createConfirm(Web3ListingCreateConfirmRequest request){
        return nftListingService.createConfirm(request);
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_CANCEL)
    public void cancel(NftListingCancelRequest request){
        nftListingService.cancel(request);
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_LIST)
    public Page<NftListingDTO> list(NftListingListRequest request){
        return nftListingService.list(request);
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_BUY)
    public NftWalletPayTicket buy(NftListingBuyRequest request){
        return nftListingService.buy(request);
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_BUY_STATUS)
    public Response status(NftOrderStatusRequest request){
        TxnStatus txnStatus = nftBuyOrderService.getStatus(request);
        return Response.successBuilder().data(txnStatus).build();
    }

}
