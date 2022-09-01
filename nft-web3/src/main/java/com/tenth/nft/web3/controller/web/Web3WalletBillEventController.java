package com.tenth.nft.web3.controller.web;

import com.tenth.nft.web3.Web3WalletPaths;
import com.tenth.nft.web3.dto.Web3WalletBillEventDTO;
import com.tenth.nft.web3.dto.Web3WalletBillEventListDTO;
import com.tenth.nft.web3.service.Web3WalletBillEventService;
import com.tenth.nft.web3.service.Web3WalletBillService;
import com.tenth.nft.web3.vo.Web3WalletBillEventDetailRequest;
import com.tenth.nft.web3.vo.Web3WalletBillEventListRequest;
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
public class Web3WalletBillEventController {

    @Autowired
    private Web3WalletBillEventService web3WalletBillService;

    @RequestMapping(Web3WalletPaths.WALLET_BILL_EVENT_LIST)
    public Response list(@RequestBody Web3WalletBillEventListRequest request){
        Validations.check(request);
        Page<Web3WalletBillEventListDTO> dataPage = web3WalletBillService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_BILL_EVENT_DETAIL)
    public Response detail(@RequestBody Web3WalletBillEventDetailRequest request){
        Validations.check(request);
        Web3WalletBillEventDTO detail = web3WalletBillService.detail(request);
        return Response.successBuilder().data(detail).build();
    }

}
