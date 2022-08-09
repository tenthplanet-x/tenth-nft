package com.tenth.nft.convention.routes.operation;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftOperation;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BlockchainRouteRequest extends AbsRouteRequest<NftOperation.NFT_BLOCKCHAIN_IC, NftOperation.NFT_BLOCKCHAIN_IS> {

    public BlockchainRouteRequest() {
        super(NftInnerCmds.NFT_BLOCKCHAIN_IC, false, false);
    }
}
