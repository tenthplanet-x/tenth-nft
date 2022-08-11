package com.tenth.nft.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.wallet.WalletOrderType;
import com.tenth.nft.protobuf.NftWallet;
import com.tenth.nft.wallet.entity.WalletBill;
import org.apache.logging.log4j.util.Strings;

/**
 * @author shijie
 */
public class WalletBillDTO {

    private Long id;

    @JsonIgnore
    private Integer activityCfgId;

    private String type;

    private Long productId;

    private String productName;

    private UserProfileDTO uidProfile;

    private Long createdAt;

    private String currency;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UserProfileDTO getUidProfile() {
        return uidProfile;
    }

    public void setUidProfile(UserProfileDTO uidProfile) {
        this.uidProfile = uidProfile;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getActivityCfgId() {
        return activityCfgId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setActivityCfgId(Integer activityCfgId) {
        this.activityCfgId = activityCfgId;
    }

    public static NftWallet.BillDTO toProto(WalletBill walletBill) {
        NftWallet.BillDTO.Builder builder = NftWallet.BillDTO.newBuilder();
        builder.setUid(walletBill.getUid());
        builder.setBillId(walletBill.getId());
        builder.setActivityCfgId(walletBill.getActivityCfgId());
        builder.setProductCode(walletBill.getProductCode());
        builder.setOutOrderId(walletBill.getOutOrderId());
        builder.setProductId(walletBill.getProductId());
        builder.setState(walletBill.getState());
        builder.setCurrency(walletBill.getCurrency());
        builder.setValue(walletBill.getValue());
        builder.setCreatedAt(walletBill.getCreatedAt());
        if(!Strings.isEmpty(walletBill.getMerchantType())){
            builder.setMerchantType(walletBill.getMerchantType());
            builder.setMerchantId(walletBill.getMerchantId());
        }

        return builder.build();
    }

    public static WalletBillDTO from(NftWallet.BillDTO bill) {
        WalletBillDTO walletBillDTO = new WalletBillDTO();
        walletBillDTO.setId(bill.getBillId());
        walletBillDTO.setActivityCfgId(bill.getActivityCfgId());
        walletBillDTO.setProductId(bill.getProductId());
        walletBillDTO.setCurrency(bill.getCurrency());
        walletBillDTO.setValue(bill.getValue());
        walletBillDTO.setCreatedAt(bill.getCreatedAt());
        return walletBillDTO;
    }

    public static WalletBillDTO from(WalletBill walletBill) {
        WalletBillDTO walletBillDTO = new WalletBillDTO();
        walletBillDTO.setId(walletBill.getId());
        walletBillDTO.setActivityCfgId(walletBill.getActivityCfgId());
        walletBillDTO.setProductId(walletBill.getProductId());
        walletBillDTO.setCurrency(walletBill.getCurrency());
        walletBillDTO.setValue(walletBill.getValue());
        walletBillDTO.setCreatedAt(walletBill.getCreatedAt());
        return walletBillDTO;
    }
}
