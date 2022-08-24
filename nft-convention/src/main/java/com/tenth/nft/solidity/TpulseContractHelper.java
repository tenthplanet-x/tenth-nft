package com.tenth.nft.solidity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @author shijie
 */
@Component
public class TpulseContractHelper {

    public String getContractAddress() {
        throw new UnsupportedOperationException();
    }

    public String createPaymentTransactionData(
            String seller,
            Long assetsId,
            Integer quantity,
            String price,
            String signature) {
        throw new UnsupportedOperationException();
    }
}
