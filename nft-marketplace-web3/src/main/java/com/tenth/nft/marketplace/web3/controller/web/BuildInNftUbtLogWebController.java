package com.tenth.nft.marketplace.web3.controller.web;

import com.tenth.nft.marketplace.common.dto.NftUbtLogDTO;
import com.tenth.nft.marketplace.common.vo.NftUbtLogListRequest;
import com.tenth.nft.marketplace.web3.Web3NftAssetsPaths;
import com.tenth.nft.marketplace.web3.service.Web3NftUbtLogService;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@HttpRoute(userAuth = true)
public class BuildInNftUbtLogWebController {

    @Autowired
    private Web3NftUbtLogService web3NftUbtLogService;

    @RequestMapping(Web3NftAssetsPaths.NFT_UBT_LIST)
    public Page<NftUbtLogDTO> list(NftUbtLogListRequest request){
        return web3NftUbtLogService.list(request);
    }

}
