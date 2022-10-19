package com.tenth.nft.marketplace.web3.dto;

/**
 * @author shijie
 */
public class Web3OfferCreateSignTicket {

    private String from;

    private String dataForSign;

    private String content;

    private String walletBridgeUrl;

    public Web3OfferCreateSignTicket() {
    }

    public Web3OfferCreateSignTicket(String from, String dataForSign, String content) {
        this.from = from;
        this.dataForSign = dataForSign;
        this.content = content;
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

    public void setWalletBridgeUrl(String walletBridgeUrl) {
        this.walletBridgeUrl = walletBridgeUrl;
    }

    public String getWalletBridgeUrl() {
        return walletBridgeUrl;
    }
}
