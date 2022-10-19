package com.tenth.nft.web3.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shijie
 */
@Document("tpulse.web3_wallet")
public class Web3Wallet {

    @Id
    private Long id;

    @Indexed
    private Long uid;

    private String blockchain;

    private String wallet;

    private String walletAccountId;

    private Long createdAt;

    private Long updatedAt;

    private String contractAddress;

    private boolean contractApproved;

    private String wethContractAddress;

    private boolean wethContractApproved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getWalletAccountId() {
        return walletAccountId;
    }

    public void setWalletAccountId(String walletAccountId) {
        this.walletAccountId = walletAccountId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public boolean isContractApproved() {
        return contractApproved;
    }

    public void setContractApproved(boolean contractApproved) {
        this.contractApproved = contractApproved;
    }

    public String getWethContractAddress() {
        return wethContractAddress;
    }

    public void setWethContractAddress(String wethContractAddress) {
        this.wethContractAddress = wethContractAddress;
    }

    public boolean isWethContractApproved() {
        return wethContractApproved;
    }

    public void setWethContractApproved(boolean wethContractApproved) {
        this.wethContractApproved = wethContractApproved;
    }
}
