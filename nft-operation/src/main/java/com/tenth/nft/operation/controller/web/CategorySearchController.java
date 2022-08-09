package com.tenth.nft.operation.controller.web;

import com.tenth.nft.operation.OpsSearchPaths;
import com.tenth.nft.operation.dto.CategorySearchDTO;
import com.tenth.nft.operation.service.NftCategoryOpsService;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shijie
 */
@RestController
public class CategorySearchController {

    @Autowired
    private NftCategoryOpsService categorySearchService;

    @RequestMapping(OpsSearchPaths.CATEGORY_LIST)
    public Response list(){

        List<CategorySearchDTO> categories = categorySearchService.getAll();

        return Response.successBuilder().data(categories).build();
    }
}
