package com.tenth.nft.crawler.controller;

import com.tpulse.commons.validation.Validations;
import com.tenth.nft.crawler.NftCategoryPaths;
import com.tenth.nft.crawler.dto.NftCategoryDTO;
import com.tenth.nft.crawler.service.NftCategoryService;
import com.tenth.nft.crawler.vo.NftCategoryCreateRequest;
import com.tenth.nft.crawler.vo.NftCategoryDeleteRequest;
import com.tenth.nft.crawler.vo.NftCategoryEditRequest;
import com.tenth.nft.crawler.vo.NftCategoryListRequest;
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
public class NftCategoryWebController {

    @Autowired
    private NftCategoryService nftCategoryService;

    @RequestMapping(NftCategoryPaths.NFTCATEGORY_LIST)
    public Response list(@RequestBody NftCategoryListRequest request){
        Validations.check(request);
        Page<NftCategoryDTO> dataPage = nftCategoryService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(NftCategoryPaths.NFTCATEGORY_CREATE)
    public Response create(@RequestBody NftCategoryCreateRequest request){
        Validations.check(request);
        nftCategoryService.create(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(NftCategoryPaths.NFTCATEGORY_EDIT)
    public Response edit(@RequestBody NftCategoryEditRequest request){
        Validations.check(request);
        nftCategoryService.edit(request);
        return Response.successBuilder().build();
    }


    @RequestMapping(NftCategoryPaths.NFTCATEGORY_DETAIL)
    public Response delete(@RequestBody NftCategoryDeleteRequest request){
        Validations.check(request);
        NftCategoryDTO dto = nftCategoryService.detail(request);
        return Response.successBuilder().data(dto).build();
    }


}
