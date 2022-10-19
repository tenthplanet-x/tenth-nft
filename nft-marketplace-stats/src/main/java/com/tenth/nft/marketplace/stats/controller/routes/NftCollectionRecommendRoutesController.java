package com.tenth.nft.marketplace.stats.controller.routes;

import com.tenth.nft.convention.routes.marketplace.rec.RecDoStatsRouteRequest;
import com.tenth.nft.marketplace.stats.NftStatsPaths;
import com.tenth.nft.marketplace.stats.dto.NftCollectionRecommendDTO;
import com.tenth.nft.marketplace.stats.service.NftCollectionRecommendService;
import com.tenth.nft.marketplace.stats.vo.CollectionRecommentListSearchRequest;
import com.tenth.nft.protobuf.NftMarketplaceRec;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@Component
@Route
public class NftCollectionRecommendRoutesController {

    @Autowired
    private NftCollectionRecommendService nftCollectionRecommendService;

    @RouteRequestMapping(RecDoStatsRouteRequest.class)
    public void recommendList(NftMarketplaceRec.REC_DO_STATS_IC request){
        nftCollectionRecommendService.doStats();
    }

}
