package com.tenth.nft.web3.vo;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;

/**
 * @author shijie
 */
@Valid
public class Web3WalletBillStateRequest {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
