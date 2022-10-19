package com.tenth.nft.marketplace.buildin.controller.web;

import com.tenth.nft.marketplace.buildin.BuildInNftAssetsPaths;
import com.tenth.nft.marketplace.buildin.service.BuildInNftUbtLogService;
import com.tenth.nft.marketplace.common.vo.NftUbtLogListRequest;
import com.tpulse.commons.validation.Validations;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@HttpRoute(userAuth = true)
public class BuildInNftUbtLogWebController {

    @Autowired
    private BuildInNftUbtLogService buildInNftUbtLogService;

    @RequestMapping(BuildInNftAssetsPaths.NFT_UBT_LIST)
    public Response list(@RequestBody NftUbtLogListRequest request){
        Validations.check(request);
        return Response.successBuilder().data(buildInNftUbtLogService.list(request)).build();
    }

}
