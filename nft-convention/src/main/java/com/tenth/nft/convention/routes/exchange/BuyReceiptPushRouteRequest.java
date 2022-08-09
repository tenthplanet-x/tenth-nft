package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuyReceiptPushRouteRequest extends AbsRouteRequest<NftExchange.PAY_RECEIPT_PUSH_IC, NftExchange.PAY_RECEIPT_PUSH_IS> {

    public BuyReceiptPushRouteRequest() {
        super(NftInnerCmds.PAY_RECEIPT_PUSH_IC, false, false);
    }
}
