package com.tenth.nft.search.web;

import com.tenth.nft.search.NftSearchPaths;
import com.tenth.nft.search.dto.BlockchainSearchDTO;
import com.tenth.nft.search.service.BlockchainSearchService;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shijie
 */
@RestController
public class BlockchainSearchController {

    @Autowired
    private BlockchainSearchService blockchainSearchService;

    @RequestMapping(NftSearchPaths.BLOCKCHAIN_LIST)
    public Response list(){
        List<BlockchainSearchDTO> currencies = blockchainSearchService.listAll();
        return Response.successBuilder().data(currencies).build();
    }
}
