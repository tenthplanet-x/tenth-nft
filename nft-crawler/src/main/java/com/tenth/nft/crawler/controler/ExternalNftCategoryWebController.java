package com.tenth.nft.crawler.controller;

import com.tenth.nft.crawler.ExternalNftCategoryPaths;
import com.tenth.nft.crawler.dto.ExternalNftCategoryDTO;
import com.tenth.nft.crawler.service.ExternalNftCategoryService;
import com.tenth.nft.crawler.vo.ExternalNftCategoryEditRequest;
import com.tenth.nft.crawler.vo.ExternalNftCategoryListRequest;
import com.tpulse.commons.validation.Validations;
import com.tenth.nft.crawler.vo.ExternalNftCategoryCreateRequest;
import com.tenth.nft.crawler.vo.ExternalNftCategoryDeleteRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@RestController
@HttpRoute(userAuth = true)
public class ExternalNftCategoryWebController {

    @Autowired
    private ExternalNftCategoryService nftCategoryService;

    @RequestMapping(ExternalNftCategoryPaths.NFTCATEGORY_LIST)
    public Response list(@RequestBody ExternalNftCategoryListRequest request){
        Validations.check(request);
        Page<ExternalNftCategoryDTO> dataPage = nftCategoryService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(ExternalNftCategoryPaths.NFTCATEGORY_CREATE)
    public Response create(@RequestBody ExternalNftCategoryCreateRequest request){
        Validations.check(request);
        nftCategoryService.create(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(ExternalNftCategoryPaths.NFTCATEGORY_EDIT)
    public Response edit(@RequestBody ExternalNftCategoryEditRequest request){
        Validations.check(request);
        nftCategoryService.edit(request);
        return Response.successBuilder().build();
    }


    @RequestMapping(ExternalNftCategoryPaths.NFTCATEGORY_DETAIL)
    public Response delete(@RequestBody ExternalNftCategoryDeleteRequest request){
        Validations.check(request);
        ExternalNftCategoryDTO dto = nftCategoryService.detail(request);
        return Response.successBuilder().data(dto).build();
    }


}
