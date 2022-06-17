package com.tenth.nft.crawler.controller;

import com.tpulse.commons.validation.Validations;
import com.tenth.nft.crawler.NftCollectionPaths;
import com.tenth.nft.crawler.dto.NftCollectionDTO;
import com.tenth.nft.crawler.service.NftCollectionService;
import com.tenth.nft.crawler.vo.NftCollectionCreateRequest;
import com.tenth.nft.crawler.vo.NftCollectionDeleteRequest;
import com.tenth.nft.crawler.vo.NftCollectionEditRequest;
import com.tenth.nft.crawler.vo.NftCollectionListRequest;
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
@RestController
@HttpRoute(userAuth = true)
public class NftCollectionWebController {

    @Autowired
    private NftCollectionService nftCollectionService;

    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_LIST)
    public Response list(@RequestBody NftCollectionListRequest request){
        Validations.check(request);
        Page<NftCollectionDTO> dataPage = nftCollectionService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

//    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_CREATE)
//    public Response create(@RequestBody NftCollectionCreateRequest request){
//        Validations.check(request);
//        nftCollectionService.create(request);
//        return Response.successBuilder().build();
//    }

    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_EDIT)
    public Response edit(@RequestBody NftCollectionEditRequest request){
        Validations.check(request);
        nftCollectionService.edit(request);
        return Response.successBuilder().build();
    }


    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_DETAIL)
    public Response delete(@RequestBody NftCollectionDeleteRequest request){
        Validations.check(request);
        NftCollectionDTO dto = nftCollectionService.detail(request);
        return Response.successBuilder().data(dto).build();
    }


}
