package com.tenth.nft.marketplace.web3.controller.web;

import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.vo.NftAssetsCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsListRequest;
import com.tenth.nft.marketplace.web3.Web3NftAssetsPaths;
import com.tenth.nft.marketplace.web3.dto.Web3NftCreateSignTicket;
import com.tenth.nft.marketplace.web3.service.Web3NftAssetsService;
import com.tenth.nft.marketplace.web3.vo.Web3NftAssetsCreateConfirmRequest;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@RestController
@HttpRoute(userAuth = true)
public class Web3NftAssetsWebController {

    @Autowired
    private Web3NftAssetsService nftAssetsService;

    @RequestMapping(Web3NftAssetsPaths.NFTASSETS_LIST)
    public Response list(@RequestBody NftAssetsListRequest request){
        Validations.check(request);
        Page<NftAssetsDTO> dataPage = nftAssetsService.list(request, NftAssetsDTO.class);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(Web3NftAssetsPaths.NFTASSETS_CREATE)
    public Response create(@RequestBody NftAssetsCreateRequest request) throws Exception{
        Validations.check(request);
        Web3NftCreateSignTicket assetsDTO = nftAssetsService.create(request);
        return Response.successBuilder().data(assetsDTO).build();
    }

    @RequestMapping(Web3NftAssetsPaths.NFTASSETS_CREATE_CONFIRM)
    public Response createConfirm(@RequestBody Web3NftAssetsCreateConfirmRequest request) throws Exception{

        Validations.check(request);
        NftAssetsDTO assetsDTO = nftAssetsService.createConfirm(request);
        return Response.successBuilder().data(assetsDTO).build();
    }


}
