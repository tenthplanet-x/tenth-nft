package com.tenth.nft.marketplace.common.service;


/**
 * @author shijie
 */
public class NftOuterProduct {

    private String outerOrderId;

    private String seller;

    private String currency;

    private String price;

    private Integer quantity;

    public String getOuterOrderId() {
        return outerOrderId;
    }

    public void setOuterOrderId(String outerOrderId) {
        this.outerOrderId = outerOrderId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder{

        private NftOuterProduct product = new NftOuterProduct();

        public Builder outerOrderId(String outerOrderId) {
            product.outerOrderId = outerOrderId;
            return this;
        }

        public Builder seller(String seller) {
            product.seller = seller;
            return this;
        }

        public Builder price(String price) {
            product.price = price;
            return this;
        }

        public Builder quantity(Integer quantity) {
            product.quantity = quantity;
            return this;
        }

        public Builder currency(String currency) {
            product.currency = currency;
            return this;
        }

        public NftOuterProduct build() {
            return product;
        }
    }
}
