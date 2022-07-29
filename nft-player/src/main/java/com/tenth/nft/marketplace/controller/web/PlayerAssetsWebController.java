package com.tenth.nft.marketplace.controller.web;

import com.tenth.nft.marketplace.NftAssetsPaths;
import com.tenth.nft.marketplace.dto.AssetsOwnSearchDTO;
import com.tenth.nft.marketplace.service.PlayerAssetsBelongsService;
import com.tenth.nft.marketplace.vo.AssetsOwnSearchRequest;
import com.tenth.nft.orm.marketplace.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.service.PlayerAssetsService;
import com.tenth.nft.marketplace.vo.NftAssetsCreateRequest;
import com.tenth.nft.marketplace.vo.NftAssetsListRequest;
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
public class PlayerAssetsWebController {

    @Autowired
    private PlayerAssetsService nftAssetsService;
    @Autowired
    private PlayerAssetsBelongsService playerAssetsBelongsService;

    @RequestMapping(NftAssetsPaths.NFTASSETS_LIST)
    public Response list(@RequestBody NftAssetsListRequest request){
        Validations.check(request);
        Page<NftAssetsDTO> dataPage = nftAssetsService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(NftAssetsPaths.NFTASSETS_CREATE)
    public Response create(@RequestBody NftAssetsCreateRequest request){
        Validations.check(request);
        NftAssetsDTO assetsDTO = nftAssetsService.create(request);
        return Response.successBuilder().data(assetsDTO).build();
    }

    @RequestMapping(NftAssetsPaths.ASSETS_OWN_LIST)
    public Response list(@RequestBody AssetsOwnSearchRequest request){
        Validations.check(request);
        Page<AssetsOwnSearchDTO> collections = playerAssetsBelongsService.list(request);
        return Response.successBuilder().data(collections).build();
    }

}