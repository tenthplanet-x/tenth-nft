package com.tenth.nft.solidity;

/**
 * @author shijie
 */
public class TpulseContractException extends RuntimeException {

    public TpulseContractException(String message, Exception e) {
        super(message, e);
    }
}
