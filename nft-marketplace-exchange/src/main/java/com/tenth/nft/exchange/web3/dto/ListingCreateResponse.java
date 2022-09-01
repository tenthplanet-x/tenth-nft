package com.tenth.nft.exchange.web3.dto;

/**
 * @author shijie
 */
public class ListingCreateResponse {

    private String from;

    private String dataForSign;

    private String token;

    public ListingCreateResponse() {
    }

    public ListingCreateResponse(String content) {
        this.dataForSign = content;
    }

    public String getDataForSign() {
        return dataForSign;
    }

    public void setDataForSign(String dataForSign) {
        this.dataForSign = dataForSign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
