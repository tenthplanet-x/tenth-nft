package com.tenth.nft.convention.web3.sign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shijie
 */
public class MintDataForSign extends DataForSign{

    private static Object messageTypeDefine;
    static {

        messageTypeDefine = DataForSignTypeDefine.newBuilder()
                .add("name", "string")
                .add("desc", "string")
                .add("supply", "uint256")
                .add("creatorFeeRate", "uint256")
                .add("creatorFeeRatePrecision", "uint8")
                .build();
    }

    private String name;

    private String desc;

    private Integer supply;

    private Integer creatorFeeRate;

    private Integer creatorFeeRatePrecision;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getSupply() {
        return supply;
    }

    public void setSupply(Integer supply) {
        this.supply = supply;
    }

    public Integer getCreatorFeeRate() {
        return creatorFeeRate;
    }

    public void setCreatorFeeRate(Integer creatorFeeRate) {
        this.creatorFeeRate = creatorFeeRate;
    }

    public Integer getCreatorFeeRatePrecision() {
        return creatorFeeRatePrecision;
    }

    public void setCreatorFeeRatePrecision(Integer creatorFeeRatePrecision) {
        this.creatorFeeRatePrecision = creatorFeeRatePrecision;
    }

    @Override
    protected Object getMessage() {
        Map<String, Object> output = new HashMap<>();

        Map<String, Object> message = new HashMap<>();
        message.put("name", name);
        message.put("desc", desc);
        message.put("supply", supply);
        message.put("creatorFeeRate", creatorFeeRate);
        message.put("creatorFeeRatePrecision", creatorFeeRatePrecision);

        return output;
    }

    @Override
    protected String getPrimaryType() {
        return "Mint";
    }

    @Override
    protected Map<String, Object> getCustomTypes() {
        Map<String, Object> types = new HashMap<>();
        types.put(getPrimaryType(), messageTypeDefine);
        return types;
    }
}
