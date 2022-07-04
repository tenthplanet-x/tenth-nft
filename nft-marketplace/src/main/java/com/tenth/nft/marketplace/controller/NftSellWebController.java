package com.tenth.nft.marketplace.controller;

import com.tenth.nft.marketplace.NftSellPaths;
import com.tenth.nft.marketplace.dto.NftSellDTO;
import com.tenth.nft.marketplace.service.NftSellService;
import com.tenth.nft.marketplace.vo.NftSellCreateRequest;
import com.tenth.nft.marketplace.vo.NftSellDeleteRequest;
import com.tenth.nft.marketplace.vo.NftSellEditRequest;
import com.tenth.nft.marketplace.vo.NftSellListRequest;
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
 * @createdAt 2022/06/21 15:27
 */
@RestController
@HttpRoute(userAuth = true)
public class NftSellWebController {

    @Autowired
    private NftSellService nftSellService;

    /**
     * 列表
     * @return
     */
    @RequestMapping(NftSellPaths.NFTSELL_LIST)
    public Response list(@RequestBody NftSellListRequest request){
        Validations.check(request);
        Page<NftSellDTO> dataPage = nftSellService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    /**
     * 创建
     * @return
     */
    @RequestMapping(NftSellPaths.NFTSELL_CREATE)
    public Response create(@RequestBody NftSellCreateRequest request){
        Validations.check(request);
        nftSellService.create(request);
        return Response.successBuilder().build();
    }

    /**
     * 编辑
     * @return
     */
    @RequestMapping(NftSellPaths.NFTSELL_EDIT)
    public Response edit(@RequestBody NftSellEditRequest request){
        Validations.check(request);
        nftSellService.edit(request);
        return Response.successBuilder().build();
    }


    /**
     * 详情
     * @return
     */
    @RequestMapping(NftSellPaths.NFTSELL_DETAIL)
    public Response delete(@RequestBody NftSellDeleteRequest request){
        Validations.check(request);
        NftSellDTO dto = nftSellService.detail(request);
        return Response.successBuilder().data(dto).build();
    }


}
