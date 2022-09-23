package com.tenth.nft.marketplace.web3.dto;

/**
 * @author shijie
 */
public class WebListingSignTicket {

    private String from;

    private String dataForSign;

    private String content;

    private String walletBridgeUrl;

    public WebListingSignTicket() {

    }

    public WebListingSignTicket(String content) {
        this.dataForSign = content;
    }

    public String getDataForSign() {
        return dataForSign;
    }

    public void setDataForSign(String dataForSign) {
        this.dataForSign = dataForSign;
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

    public String getWalletBridgeUrl() {
        return walletBridgeUrl;
    }

    public void setWalletBridgeUrl(String walletBridgeUrl) {
        this.walletBridgeUrl = walletBridgeUrl;
    }
}
