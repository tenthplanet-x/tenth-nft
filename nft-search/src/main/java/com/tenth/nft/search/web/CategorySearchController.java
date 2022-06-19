package com.tenth.nft.search.web;

import com.tenth.nft.search.NftSearchPaths;
import com.tenth.nft.search.dto.CategorySearchDTO;
import com.tenth.nft.search.service.CategorySearchService;
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
    private CategorySearchService categorySearchService;

    @RequestMapping(NftSearchPaths.CATEGORY_LIST)
    public Response list(){

        List<CategorySearchDTO> categories = categorySearchService.getAll();

        return Response.successBuilder().build();
    }
}
