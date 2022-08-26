package com.tenth.nft.web3.dto;

/**
 * @author shijie
 */
public class Web3WalletProfile {

    private String wallet;
    private String address;
    private boolean contractApproved;

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getWallet() {
        return wallet;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setContractApproved(boolean contractApproved) {
        this.contractApproved = contractApproved;
    }

    public boolean getContractApproved() {
        return contractApproved;
    }
}
