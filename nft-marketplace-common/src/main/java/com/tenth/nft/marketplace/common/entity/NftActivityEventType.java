package com.tenth.nft.marketplace.common.entity;

/**
 * @author shijie
 */
public enum NftActivityEventType {

    Sale("Sale"),
    Transfer("Transfer"),
    Bid("Bid"),
    List("List"),
    Minted("Minted"),
    Cancel("List Cancel"),
    OFFER("Offer"),
    OFFER_CANCEL("Offer Canceled");

    String label;
    NftActivityEventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
