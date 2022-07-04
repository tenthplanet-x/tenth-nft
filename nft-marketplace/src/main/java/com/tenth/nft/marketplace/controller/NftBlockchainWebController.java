package com.tenth.nft.marketplace.controller;

import com.tenth.nft.marketplace.NftBlockchainPaths;
import com.tenth.nft.marketplace.dto.NftBlockchainDTO;
import com.tenth.nft.marketplace.service.NftBlockchainService;
import com.tenth.nft.marketplace.vo.NftBlockchainCreateRequest;
import com.tenth.nft.marketplace.vo.NftBlockchainDeleteRequest;
import com.tenth.nft.marketplace.vo.NftBlockchainEditRequest;
import com.tenth.nft.marketplace.vo.NftBlockchainListRequest;
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
 * @createdAt 2022/06/21 16:58
 */
@RestController
@HttpRoute(userAuth = true)
public class NftBlockchainWebController {

    @Autowired
    private NftBlockchainService nftBlockchainService;

    /**
     * 列表
     * @return
     */
    @RequestMapping(NftBlockchainPaths.NFTBLOCKCHAIN_LIST)
    public Response list(@RequestBody NftBlockchainListRequest request){
        Validations.check(request);
        Page<NftBlockchainDTO> dataPage = nftBlockchainService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    /**
     * 创建
     * @return
     */
    @RequestMapping(NftBlockchainPaths.NFTBLOCKCHAIN_CREATE)
    public Response create(@RequestBody NftBlockchainCreateRequest request){
        Validations.check(request);
        nftBlockchainService.create(request);
        return Response.successBuilder().build();
    }

    /**
     * 编辑
     * @return
     */
    @RequestMapping(NftBlockchainPaths.NFTBLOCKCHAIN_EDIT)
    public Response edit(@RequestBody NftBlockchainEditRequest request){
        Validations.check(request);
        nftBlockchainService.edit(request);
        return Response.successBuilder().build();
    }


    /**
     * 详情
     * @return
     */
    @RequestMapping(NftBlockchainPaths.NFTBLOCKCHAIN_DETAIL)
    public Response delete(@RequestBody NftBlockchainDeleteRequest request){
        Validations.check(request);
        NftBlockchainDTO dto = nftBlockchainService.detail(request);
        return Response.successBuilder().data(dto).build();
    }


}
