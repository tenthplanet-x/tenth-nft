package com.tenth.nft.marketplace.common.dto;

import com.tenth.nft.convention.blockchain.NullAddress;

/**
 * @author shijie
 */
public class NftUbtLogDTO {

    private Long id;

    private String event;

    private String from = NullAddress.TOKEN;

    private String to = NullAddress.TOKEN;

    private String price;

    private String currency;

    private Integer quantity;

//    private UserProfileDTO fromProfile;
//
//    private UserProfileDTO toProfile;

    private Boolean expired;

    private Boolean canceled;

    private Long createdAt;

    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

//    public UserProfileDTO getFromProfile() {
//        return fromProfile;
//    }
//
//    public void setFromProfile(UserProfileDTO fromProfile) {
//        this.fromProfile = fromProfile;
//    }
//
//    public UserProfileDTO getToProfile() {
//        return toProfile;
//    }
//
//    public void setToProfile(UserProfileDTO toProfile) {
//        this.toProfile = toProfile;
//    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
