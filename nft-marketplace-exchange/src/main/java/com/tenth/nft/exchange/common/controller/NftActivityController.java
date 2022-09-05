package com.tenth.nft.exchange.common.controller;

import com.tenth.nft.exchange.buildin.ExchangePaths;
import com.tenth.nft.exchange.buildin.controller.vo.*;
import com.tenth.nft.exchange.buildin.dto.NftActivityDTO;
import com.tenth.nft.exchange.common.service.NftActivityService;
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
public class NftActivityController {

    @Autowired
    private NftActivityService nftActivityService;

    @RequestMapping(ExchangePaths.ACTIVITY_LIST)
    public Response activityList(@RequestBody NftActivityListRequest request){
        Validations.check(request);
        Page<NftActivityDTO> dataPage = nftActivityService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

}
