package com.tenth.nft.marketplace.controller;

import com.tenth.nft.marketplace.NftAssetsPaths;
import com.tenth.nft.marketplace.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.service.NftAssetsService;
import com.tenth.nft.marketplace.vo.NftAssetsCreateRequest;
import com.tenth.nft.marketplace.vo.NftAssetsDetailRequest;
import com.tenth.nft.marketplace.vo.NftAssetsEditRequest;
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
public class NftAssetsWebController {

    @Autowired
    private NftAssetsService nftAssetsService;

    /**
     * 列表
     * @return
     */
    @RequestMapping(NftAssetsPaths.NFTASSETS_LIST)
    public Response list(@RequestBody NftAssetsListRequest request){
        Validations.check(request);
        Page<NftAssetsDTO> dataPage = nftAssetsService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    /**
     * 创建
     * @return
     */
    @RequestMapping(NftAssetsPaths.NFTASSETS_CREATE)
    public Response create(@RequestBody NftAssetsCreateRequest request){
        Validations.check(request);
        NftAssetsDTO assetsDTO = nftAssetsService.create(request);
        return Response.successBuilder().data(assetsDTO).build();
    }

    /**
     * 编辑
     * @return
     */
    @RequestMapping(NftAssetsPaths.NFTASSETS_EDIT)
    public Response edit(@RequestBody NftAssetsEditRequest request){
        Validations.check(request);
        nftAssetsService.edit(request);
        return Response.successBuilder().build();
    }


    /**
     * 详情
     * @return
     */
    @RequestMapping(NftAssetsPaths.NFTASSETS_DETAIL)
    public Response delete(@RequestBody NftAssetsDetailRequest request){
        Validations.check(request);
        NftAssetsDTO dto = nftAssetsService.detail(request);
        return Response.successBuilder().data(dto).build();
    }


}
