package com.tenth.nft.operation.controller.web;

import com.tenth.nft.operation.OpsSearchPaths;
import com.tenth.nft.operation.dto.BlockchainSearchDTO;
import com.tenth.nft.operation.service.NftBlockchainService;
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
    private NftBlockchainService blockchainSearchService;

    @RequestMapping(OpsSearchPaths.BLOCKCHAIN_LIST)
    public Response list(){
        List<BlockchainSearchDTO> currencies = blockchainSearchService.listAll();
        return Response.successBuilder().data(currencies).build();
    }
}
