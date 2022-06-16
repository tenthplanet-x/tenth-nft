package com.tenth.nft.crawler.controler;

import com.tenth.nft.crawler.NftCollectionPaths;
import com.tenth.nft.crawler.dto.NftCollectionDTO;
import com.tenth.nft.crawler.service.NftCollectionService;
import com.tenth.nft.crawler.vo.NftCollectionListRequest;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@RestController
public class NftCollectionWebController {

    @Autowired
    private NftCollectionService nftCollectionService;

    @RequestMapping(NftCollectionPaths.NFTCOLLECTION_LIST)
    public Response list(@RequestBody NftCollectionListRequest request){
        Validations.check(request);
        Page<NftCollectionDTO> dataPage = nftCollectionService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }


}
