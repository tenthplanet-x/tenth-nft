package com.tenth.nft.exchange.web3.vo;

/**
 * @author shijie
 */
public class Web3OfferCreateResponse {

    private String from;

    private String dataForSign;

    private String content;

    public Web3OfferCreateResponse() {
    }

    public Web3OfferCreateResponse(String from, String dataForSign, String token) {
        this.from = from;
        this.dataForSign = dataForSign;
        this.content = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDataForSign() {
        return dataForSign;
    }

    public void setDataForSign(String dataForSign) {
        this.dataForSign = dataForSign;
    }
}
