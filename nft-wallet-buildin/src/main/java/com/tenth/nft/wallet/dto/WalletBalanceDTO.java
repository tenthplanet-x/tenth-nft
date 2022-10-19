package com.tenth.nft.wallet.dto;

import com.tenth.nft.wallet.entity.Wallet;

/**
 * @author shijie
 */
public class WalletBalanceDTO {

    private String currency;

    private String value;

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

    public static WalletBalanceDTO from(Wallet wallet) {

        WalletBalanceDTO walletBalanceDTO = new WalletBalanceDTO();
        walletBalanceDTO.setCurrency(wallet.getCurrency());
        walletBalanceDTO.setValue(wallet.getValue());

        return walletBalanceDTO;
    }

    public static WalletBalanceDTO emptyOf(String currency) {
        WalletBalanceDTO walletBalanceDTO = new WalletBalanceDTO();
        walletBalanceDTO.setCurrency(currency);
        walletBalanceDTO.setValue("0");
        return walletBalanceDTO;
    }
}
