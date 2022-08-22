package com.tenth.nft.wallet.controller.routes;

import com.tenth.nft.convention.routes.wallet.BillIncomeTriggerRouteRequest;
import com.tenth.nft.convention.routes.wallet.WalletPayRouteRequest;
import com.tenth.nft.convention.routes.wallet.WalletBillDetailRouteRequest;
import com.tenth.nft.convention.routes.wallet.WalletRechargeRouteRequest;
import com.tenth.nft.protobuf.NftWallet;
import com.tenth.nft.wallet.service.WalletBillService;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class WalletBillRoutesController {

    @Autowired
    private WalletBillService walletBillService;

    @RouteRequestMapping(WalletPayRouteRequest.class)
    public NftWallet.BILL_PAY_IS pay(NftWallet.BILL_PAY_IC request){
        return walletBillService.pay(request);
    }

    @RouteRequestMapping(WalletBillDetailRouteRequest.class)
    public NftWallet.BILL_DETAIL_IS detail(NftWallet.BILL_DETAIL_IC request){
        return walletBillService.detail(request);
    }

    @RouteRequestMapping(WalletRechargeRouteRequest.class)
    public NftWallet.RECHARGE_IS recharge(NftWallet.RECHARGE_IC request){
        return walletBillService.recharge(request);
    }

    @RouteRequestMapping(BillIncomeTriggerRouteRequest.class)
    public void incomeTrigger(NftWallet.BILL_INCOME_TRIGGER_IC request){
        walletBillService.incomeTrigger(request);
    }


}
