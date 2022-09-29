package com.tenth.nft.search.web;

import com.tenth.nft.search.NftSearchPaths;
import com.tenth.nft.search.dto.CollectionDetailSearchDTO;
import com.tenth.nft.search.dto.CollectionSearchDTO;
import com.tenth.nft.search.service.CollectionSearchService;
import com.tenth.nft.search.vo.CollectionDetailSearchRequest;
import com.tenth.nft.search.vo.CollectionListSearchRequest;
import com.tenth.nft.search.vo.CollectionRecommentListSearchRequest;
import com.tpulse.commons.biz.dto.PageRequest;
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
public class CollectionSearchController {

    @Autowired
    private CollectionSearchService collectionSearchService;

//    @RequestMapping(NftSearchPaths.COLLECTION_RECOMMEND_LIST)
//    public Response recommendList(@RequestBody CollectionRecommentListSearchRequest request){
//        Validations.check(request);
//        Page<CollectionSearchDTO> collections = collectionSearchService.recommendList(request);
//        return Response.successBuilder().data(collections).build();
//    }

    @RequestMapping(NftSearchPaths.COLLECTION_LIST)
    public Response list(@RequestBody CollectionListSearchRequest request){
        Validations.check(request);
        Page<CollectionSearchDTO> collections = collectionSearchService.list(request);
        return Response.successBuilder().data(collections).build();
    }

    @RequestMapping(NftSearchPaths.COLLECTION_DETAIL)
    public Response detail(@RequestBody CollectionDetailSearchRequest request){

        CollectionDetailSearchDTO collection = collectionSearchService.detail(request);
        return Response.successBuilder().data(collection).build();
    }
}
