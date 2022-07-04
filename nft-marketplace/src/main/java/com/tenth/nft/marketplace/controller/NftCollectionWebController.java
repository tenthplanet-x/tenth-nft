package com.tenth.nft.marketplace.controller;

import com.tenth.nft.marketplace.NftCollectionPaths;
import com.tenth.nft.marketplace.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.service.NftCollectionService;
import com.tenth.nft.marketplace.vo.NftCollectionCreateRequest;
import com.tenth.nft.marketplace.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.vo.NftCollectionEditRequest;
import com.tenth.nft.marketplace.vo.NftCollectionListRequest;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.oss.vo.OSSToken;
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
public class NftCollectionWebController {

    @Autowired
    private NftCollectionService nftCollectionService;

    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_TOKEN)
    public Response uploadToken(){

        OSSToken token = nftCollectionService.getUploadToken();
        return Response.successBuilder().data(token).build();
    }

    /**
     * 列表
     * @return
     */
    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_LIST)
    public Response list(@RequestBody NftCollectionListRequest request){
        Validations.check(request);
        Page<NftCollectionDTO> dataPage = nftCollectionService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    /**
     * 创建
     * @return
     */
    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_CREATE)
    public Response create(@RequestBody NftCollectionCreateRequest request){
        Validations.check(request);
        NftCollectionDTO nftCollectionDTO = nftCollectionService.create(request);
        return Response.successBuilder().data(nftCollectionDTO).build();
    }

    /**
     * 编辑
     * @return
     */
    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_EDIT)
    public Response edit(@RequestBody NftCollectionEditRequest request){
        Validations.check(request);
        nftCollectionService.edit(request);
        return Response.successBuilder().build();
    }


    /**
     * 详情
     * @return
     */
    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_DETAIL)
    public Response detail(@RequestBody NftCollectionDetailRequest request){
        Validations.check(request);
        NftCollectionDTO dto = nftCollectionService.detail(request);
        return Response.successBuilder().data(dto).build();
    }


}
