package com.tenth.nft.exchange.web3.vo;

/**
 * @author shijie
 */
public class Web3ExchangeListingConfirmRequest {

    private String dataForSign;

    private String token;

    private String signature;

    public String getDataForSign() {
        return dataForSign;
    }

    public void setDataForSign(String dataForSign) {
        this.dataForSign = dataForSign;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
