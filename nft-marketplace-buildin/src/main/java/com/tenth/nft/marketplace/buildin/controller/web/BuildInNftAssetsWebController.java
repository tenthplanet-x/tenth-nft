package com.tenth.nft.marketplace.buildin.controller.web;

import com.tenth.nft.marketplace.buildin.BuildInNftAssetsPaths;
import com.tenth.nft.marketplace.buildin.service.BuildInNftAssetsService;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.vo.NftAssetsCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsListRequest;
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
public class BuildInNftAssetsWebController {

    @Autowired
    private BuildInNftAssetsService nftAssetsService;

    @RequestMapping(BuildInNftAssetsPaths.NFT_ASSETS_LIST)
    public Response list(@RequestBody NftAssetsListRequest request){
        Validations.check(request);
        Page<NftAssetsDTO> dataPage = nftAssetsService.list(request, NftAssetsDTO.class);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(BuildInNftAssetsPaths.NFT_ASSETS_CREATE)
    public Response create(@RequestBody NftAssetsCreateRequest request){
        Validations.check(request);
        NftAssetsDTO assetsDTO = nftAssetsService.create(request);
        return Response.successBuilder().data(assetsDTO).build();
    }

}
