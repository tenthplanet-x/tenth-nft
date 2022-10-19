package com.tenth.nft.convention.web3.sign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shijie
 */
public class ListingDataForSign extends DataForSign {

    private static Object messageTypeDefine;
    static {

        messageTypeDefine = DataForSignTypeDefine.newBuilder()
                .add("seller", "address")
                .add("assetsId", "uint256")
                .add("quantity", "uint256")
                .build();
    }

    private String seller;
    private Long assetsId;
    private Integer quantity;
    private String currency;
    private String price;
    private Long startAt;
    private Long expireAt;

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    protected Object getMessage() {

        Map<String, Object> message = new HashMap<>();
        message.put("seller", seller);
        message.put("assetsId", assetsId);
        message.put("quantity", quantity);

        return message;
    }

    @Override
    protected String getPrimaryType() {
        return "Listing";
    }

    @Override
    protected Map<String, Object> getCustomTypes() {
        Map<String, Object> types = new HashMap<>();
        types.put(getPrimaryType(), messageTypeDefine);
        return types;
    }

}
