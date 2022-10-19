package com.tenth.nft.marketplace.stats.controller.web;

import com.tenth.nft.marketplace.stats.NftStatsPaths;
import com.tenth.nft.marketplace.stats.dto.NftCollectionRecommendDTO;
import com.tenth.nft.marketplace.stats.service.NftCollectionRecommendService;
import com.tenth.nft.marketplace.stats.vo.CollectionRecommentListSearchRequest;
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
public class NftCollectionRecommendController {

    @Autowired
    private NftCollectionRecommendService nftCollectionRecommendService;

    @RequestMapping(NftStatsPaths.COLLECTION_RECOMMEND_LIST)
    public Response recommendList(@RequestBody CollectionRecommentListSearchRequest request){
        Validations.check(request);
        Page<NftCollectionRecommendDTO> collections = nftCollectionRecommendService.recommendList(request);
        return Response.successBuilder().data(collections).build();
    }


}
