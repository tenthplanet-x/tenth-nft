package com.tenth.nft.search.web;

import com.tenth.nft.search.NftSearchPaths;
import com.tenth.nft.search.dto.AssetsSearchDTO;
import com.tenth.nft.search.service.AssetsSearchService;
import com.tenth.nft.search.vo.AssetsSearchRequest;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
public class AssetsSearchController {

    @Autowired
    private AssetsSearchService assetsSearchService;

    @RequestMapping(NftSearchPaths.ASSETS_LIST)
    public Response list(@RequestBody AssetsSearchRequest request){
        Validations.check(request);
        Page<AssetsSearchDTO> collections = assetsSearchService.list(request);
        return Response.successBuilder().data(collections).build();
    }
}
