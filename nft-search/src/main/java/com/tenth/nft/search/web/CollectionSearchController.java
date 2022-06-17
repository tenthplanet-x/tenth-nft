package com.tenth.nft.search.web;

import com.tenth.nft.search.NftSearchPaths;
import com.tenth.nft.search.dto.CollectionSearchDTO;
import com.tenth.nft.search.dto.SearchCollectionListRequest;
import com.tenth.nft.search.service.CollectionSearchService;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
public class CollectionSearchController {

    @Autowired
    private CollectionSearchService collectionSearchService;

    @RequestMapping(NftSearchPaths.COLLECTION_LIST)
    public Response list(@RequestBody SearchCollectionListRequest request){
        Page<CollectionSearchDTO> dataPage = collectionSearchService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }
}
