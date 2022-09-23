package com.tenth.nft.marketplace.web3.vo;

/**
 * @author shijie
 */
public class Web3ListingCreateConfirmRequest {

    private String dataForSign;

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
