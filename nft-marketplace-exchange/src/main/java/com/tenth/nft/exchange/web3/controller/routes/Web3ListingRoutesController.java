package com.tenth.nft.exchange.web3.controller.routes;

import com.tenth.nft.exchange.web3.service.Web3ListingService;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class Web3ListingRoutesController {

    @Autowired
    private Web3ListingService web3ExchangeService;

}
