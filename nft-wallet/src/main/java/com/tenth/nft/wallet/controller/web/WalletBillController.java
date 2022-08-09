package com.tenth.nft.wallet.controller.web;

import com.tenth.nft.wallet.WalletPaths;
import com.tenth.nft.wallet.dto.WalletBillDTO;
import com.tenth.nft.wallet.dto.WalletBillSimpleDTO;
import com.tenth.nft.wallet.service.WalletBillService;
import com.tenth.nft.wallet.vo.BillListRequest;
import com.tenth.nft.wallet.vo.BillPayRequest;
import com.tenth.nft.wallet.vo.BillDetailRequest;
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
public class WalletBillController {

    @Autowired
    private WalletBillService walletBillService;

    @RequestMapping(WalletPaths.WALLET_BILL_PAY)
    public Response create(@RequestBody BillPayRequest request){
        Validations.check(request);
        WalletBillDTO billDTO = walletBillService.pay(request);
        return Response.successBuilder().data(billDTO).build();
    }

    @RequestMapping(WalletPaths.WALLET_BILL_DETAIL)
    public Response detail(@RequestBody BillDetailRequest request){
        Validations.check(request);
        WalletBillDTO walletBillDTO = walletBillService.detail(request);
        return Response.successBuilder().data(walletBillDTO).build();
    }

    @RequestMapping(WalletPaths.WALLET_BILL_LIST)
    public Response list(@RequestBody BillListRequest request){
        Validations.check(request);
        Page<WalletBillSimpleDTO> walletBillDTO = walletBillService.list(request);
        return Response.successBuilder().data(walletBillDTO).build();
    }

}
