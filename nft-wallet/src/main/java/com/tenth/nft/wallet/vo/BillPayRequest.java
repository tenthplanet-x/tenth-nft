package com.tenth.nft.wallet.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author shijie
 */
@Valid
public class BillPayRequest {

    @NotEmpty
    private String token;

    @NotEmpty
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
