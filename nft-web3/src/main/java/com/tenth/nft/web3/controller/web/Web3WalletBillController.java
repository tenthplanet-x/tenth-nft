package com.tenth.nft.web3.controller.web;

import com.tenth.nft.convention.wallet.WalletBillState;
import com.tenth.nft.convention.wallet.WalletOrderType;
import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.web3.Web3WalletPaths;
import com.tenth.nft.web3.dto.Web3WalletBillDTO;
import com.tenth.nft.web3.dto.Web3WalletBillEventDTO;
import com.tenth.nft.web3.dto.Web3WalletBillEventListDTO;
import com.tenth.nft.web3.service.Web3WalletBillEventService;
import com.tenth.nft.web3.service.Web3WalletBillService;
import com.tenth.nft.web3.vo.Web3WalletBillEventDetailRequest;
import com.tenth.nft.web3.vo.Web3WalletBillEventListRequest;
import com.tenth.nft.web3.vo.Web3WalletBillPayRequest;
import com.tenth.nft.web3.vo.Web3WalletBillStateRequest;
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
public class Web3WalletBillController {

    @Autowired
    private Web3WalletBillService web3WalletBillService;

    @RequestMapping(Web3WalletPaths.WALLET_BILL_PAY)
    public Response pay(@RequestBody Web3WalletBillPayRequest request){
        Validations.check(request);
        Web3WalletBillDTO bill = web3WalletBillService.pay(request);
        return Response.successBuilder().data(bill).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_BILL_STATE)
    public Response state(@RequestBody Web3WalletBillStateRequest request){
        Validations.check(request);
        TxnStatus status = web3WalletBillService.state(request);
        return Response.successBuilder().data(status).build();
    }


}
