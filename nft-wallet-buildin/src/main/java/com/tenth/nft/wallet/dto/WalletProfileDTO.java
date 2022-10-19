package com.tenth.nft.wallet.dto;

import java.util.List;

/**
 * @author shijie
 */
public class WalletProfileDTO {

    private Boolean hasPassword;

    private List<WalletBalanceDTO> balances;

    public Boolean getHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(Boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public List<WalletBalanceDTO> getBalances() {
        return balances;
    }

    public void setBalances(List<WalletBalanceDTO> balances) {
        this.balances = balances;
    }
}
