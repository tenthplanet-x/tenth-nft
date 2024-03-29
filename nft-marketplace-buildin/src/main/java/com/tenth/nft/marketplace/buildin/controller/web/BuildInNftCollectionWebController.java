package com.tenth.nft.marketplace.buildin.controller.web;

import com.tenth.nft.marketplace.buildin.BuildInNftCollectionPaths;
import com.tenth.nft.marketplace.buildin.service.BuildInNftCollectionService;
import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.common.dto.NftCollectionDetailDTO;
import com.tenth.nft.marketplace.common.vo.NftCollectionCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionListRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionOwnListRequest;
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
public class BuildInNftCollectionWebController {

    @Autowired
    private BuildInNftCollectionService nftCollectionService;

    @RequestMapping(BuildInNftCollectionPaths.NFTCOLLECTION_CREATE)
    public Response create(@RequestBody NftCollectionCreateRequest request){
        Validations.check(request);
        NftCollectionDTO collectionDTO = nftCollectionService.create(request);
        return Response.successBuilder().data(collectionDTO).build();
    }

    @RequestMapping(BuildInNftCollectionPaths.NFTCOLLECTION_LIST)
    public Response list(@RequestBody NftCollectionListRequest request){
        Validations.check(request);
        Page<NftCollectionDTO> dataPage = nftCollectionService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(BuildInNftCollectionPaths.NFTCOLLECTION_DETAIL)
    public Response detail(@RequestBody NftCollectionDetailRequest request){
        Validations.check(request);
        NftCollectionDetailDTO dto = nftCollectionService.detail(request);
        return Response.successBuilder().data(dto).build();
    }

    @RequestMapping(BuildInNftCollectionPaths.NFTCOLLECTION_OWN_LIST)
    public Response ownList(@RequestBody NftCollectionOwnListRequest request){
        Validations.check(request);
        Page<NftCollectionDTO> dataPage = nftCollectionService.ownList(request);
        return Response.successBuilder().data(dataPage).build();
    }

}
