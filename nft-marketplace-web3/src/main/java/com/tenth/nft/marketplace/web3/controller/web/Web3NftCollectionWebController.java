package com.tenth.nft.marketplace.web3.controller.web;

import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.common.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionListRequest;
import com.tenth.nft.marketplace.web3.Web3NftCollectionPaths;
import com.tenth.nft.marketplace.web3.service.Web3NftCollectionService;
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
public class Web3NftCollectionWebController {

    @Autowired
    private Web3NftCollectionService nftCollectionService;

    @RequestMapping(Web3NftCollectionPaths.NFTCOLLECTION_LIST)
    public Response list(@RequestBody NftCollectionListRequest request){
        Validations.check(request);
        Page<NftCollectionDTO> dataPage = nftCollectionService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(Web3NftCollectionPaths.NFTCOLLECTION_DETAIL)
    public Response detail(@RequestBody NftCollectionDetailRequest request){
        Validations.check(request);
        NftCollectionDTO dto = nftCollectionService.detail(request);
        return Response.successBuilder().data(dto).build();
    }

}
