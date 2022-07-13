package com.tenth.nft.crawler.controller;

import com.tenth.nft.crawler.ExternalNftCollectionPaths;
import com.tenth.nft.crawler.service.ExternalNftCollectionService;
import com.tenth.nft.crawler.vo.ExternalNftCollectionListRequest;
import com.tpulse.commons.validation.Validations;
import com.tenth.nft.crawler.dto.ExternalNftCollectionDTO;
import com.tenth.nft.crawler.vo.ExternalNftCollectionDeleteRequest;
import com.tenth.nft.crawler.vo.ExternalNftCollectionEditRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
//@RestController
//@HttpRoute(userAuth = true)
public class ExternalNftCollectionWebController {

    @Autowired
    private ExternalNftCollectionService nftCollectionService;

    @RequestMapping(ExternalNftCollectionPaths.NFTCOLLECTION_LIST)
    public Response list(@RequestBody ExternalNftCollectionListRequest request){
        Validations.check(request);
        Page<ExternalNftCollectionDTO> dataPage = nftCollectionService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

//    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_CREATE)
//    public Response create(@RequestBody NftCollectionCreateRequest request){
//        Validations.check(request);
//        nftCollectionService.create(request);
//        return Response.successBuilder().build();
//    }

    @RequestMapping(ExternalNftCollectionPaths.NFTCOLLECTION_EDIT)
    public Response edit(@RequestBody ExternalNftCollectionEditRequest request){
        Validations.check(request);
        nftCollectionService.edit(request);
        return Response.successBuilder().build();
    }


    @RequestMapping(ExternalNftCollectionPaths.NFTCOLLECTION_DETAIL)
    public Response delete(@RequestBody ExternalNftCollectionDeleteRequest request){
        Validations.check(request);
        ExternalNftCollectionDTO dto = nftCollectionService.detail(request);
        return Response.successBuilder().data(dto).build();
    }


}
