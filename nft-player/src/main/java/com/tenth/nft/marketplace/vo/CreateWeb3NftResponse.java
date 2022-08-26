package com.tenth.nft.marketplace.vo;

/**
 * @author shijie
 */
public class CreateWeb3NftResponse {

    private String from;

    private String dataForSign;

    private String token;

    public void setDataForSign(String dataForSign) {
        this.dataForSign = dataForSign;
    }

    public String getDataForSign() {
        return dataForSign;
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
