package com.tenth.nft.marketplace.controller;

import com.tenth.nft.marketplace.NftCollectionPaths;
import com.tenth.nft.marketplace.service.PlayerCollectionService;
import com.tenth.nft.marketplace.vo.NftCollectionCreateRequest;
import com.tenth.nft.orm.marketplace.dto.NftCollectionDTO;
import com.tpulse.commons.validation.Validations;
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
public class PlayerCollectionWebController {

    @Autowired
    private PlayerCollectionService nftCollectionService;

    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_TOKEN)
    public Response uploadToken(){
        OSSToken token = nftCollectionService.getUploadToken();
        return Response.successBuilder().data(token).build();
    }

    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_CREATE)
    public Response create(@RequestBody NftCollectionCreateRequest request){
        Validations.check(request);
        NftCollectionDTO nftCollectionDTO = nftCollectionService.create(request);
        return Response.successBuilder().data(nftCollectionDTO).build();
    }

}
