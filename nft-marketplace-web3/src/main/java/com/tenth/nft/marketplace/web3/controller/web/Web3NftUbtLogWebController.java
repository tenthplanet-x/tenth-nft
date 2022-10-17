package com.tenth.nft.marketplace.web3.controller.web;

import com.tenth.nft.marketplace.common.dto.NftUbtLogDTO;
import com.tenth.nft.marketplace.common.vo.NftUbtLogListRequest;
import com.tenth.nft.marketplace.web3.Web3NftAssetsPaths;
import com.tenth.nft.marketplace.web3.service.Web3NftUbtLogService;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
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
public class Web3NftUbtLogWebController {

    @Autowired
    private Web3NftUbtLogService web3NftUbtLogService;

    @RequestMapping(Web3NftAssetsPaths.NFT_UBT_LIST)
    public Response list(@RequestBody NftUbtLogListRequest request){
        Validations.check(request);
        return Response.successBuilder().data(web3NftUbtLogService.list(request)).build();
    }

}
