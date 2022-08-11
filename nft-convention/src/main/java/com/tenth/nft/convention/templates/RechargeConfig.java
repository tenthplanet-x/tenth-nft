package com.tenth.nft.convention.templates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * @author shijie
 */
@JsonDeserialize(builder = RechargeConfig.Builder.class)
public class RechargeConfig {

    private Integer id;

    private String name;

    private String productId;

    private String type;

    private String tier;

    private String price;

    private String currency;

    private Integer amount;

    private String localizations;

    private String displayName;

    private String description;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }

    public String getPrice() {
        return price;
    }

    public String getLocalizations() {
        return localizations;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getTier() {
        return tier;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getAmount() {
        return amount;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder{

        private RechargeConfig config = new RechargeConfig();

        public void setId(Integer id) {
            config.id = id;
        }

        public void setName(String name) {
            config.name = name;
        }

        public void setProductId(String productId) {
            config.productId = productId;
        }

        public void setPrice(String price) {
            config.price = price;
        }

        public void setLocalizations(String localizations) {
            config.localizations = localizations;
        }

        public void setDisplayName(String displayName) {
            config.displayName = displayName;
        }

        public void setDescription(String description) {
            config.description = description;
        }

        public void setType(String type) {
            config.type = type;
        }

        public void setTier(String tier) {
            config.tier = tier;
        }

        public void setCurrency(String currency) {
            config.currency = currency;
        }

        public void setAmount(Integer amount) {
            config.amount = amount;
        }

        public RechargeConfig build(){
            return config;
        }
    }
}
