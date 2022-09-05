package com.tenth.nft.wallet.controller.routes;

import com.tenth.nft.convention.routes.wallet.*;
import com.tenth.nft.protobuf.NftWallet;
import com.tenth.nft.wallet.service.WalletBillService;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import com.wallan.router.vo.Response;
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

    @RouteRequestMapping(BillPaymentNotifyRouteRequest.class)
    public void notifyRouteRequest(NftWallet.BILL_PAYMENT_NOTIFY_IC request){
        walletBillService.notifyBillPayment(request);
    }

    @RouteRequestMapping(WalletPayRouteRequest.class)
    public NftWallet.BILL_PAY_IS pay(NftWallet.BILL_PAY_IC request){
        return walletBillService.pay(request);
    }

}
