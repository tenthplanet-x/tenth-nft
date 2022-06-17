package com.tenth.nft.search.web;

import com.tenth.nft.search.NftSearchPaths;
import com.wallan.router.vo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
public class CategorySearchController {

    @RequestMapping(NftSearchPaths.CATEGORY_LIST)
    public Response list(){
        return Response.successBuilder().build();
    }
}
