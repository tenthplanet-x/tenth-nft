package com.tenth.nft.convention.web3.sign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shijie
 */
public class OfferDataForSign extends DataForSign{

    private static Object messageTypeDefine;
    static {

        messageTypeDefine = DataForSignTypeDefine.newBuilder()
                .add("assetsId", "uint64")
                .add("quantity", "uint32")
                .add("price", "uint256")
                .add("expireAt", "uint64")
                .build();
    }

    private Long assetsId;

    private Integer quantity;

    private String price;

    private Long expireAt;

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public static Object getMessageTypeDefine() {
        return messageTypeDefine;
    }

    public static void setMessageTypeDefine(Object messageTypeDefine) {
        OfferDataForSign.messageTypeDefine = messageTypeDefine;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }


    @Override
    protected Object getMessage() {
        Map<String, Object> output = new HashMap<>();

        Map<String, Object> message = new HashMap<>();
        message.put("assetsId", assetsId);
        message.put("quantity", quantity);
        message.put("price", price);
        message.put("expireAt", expireAt);

        return output;
    }

    @Override
    protected String getPrimaryType() {
        return "Offer";
    }

    @Override
    protected Map<String, Object> getCustomTypes() {
        Map<String, Object> types = new HashMap<>();
        types.put(getPrimaryType(), messageTypeDefine);
        return types;
    }

}
