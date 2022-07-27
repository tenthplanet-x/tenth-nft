package com.tenth.nft.convention;

import com.wallan.router.exception.ErrorCode;

public enum NftExchangeErrorCodes implements ErrorCode {

    /**
     * 20100
     * opensea sdk
     */
    OPEANSEASDK_EXCEPTION("20101", "opeanseasdk exception"),

    /**
     * 20200 mint
     */
    MINT_EXCEPTION("20201", "mint excepiton"),
    /**
     * 20300 sell
     */
    SELL_EXCEPTION_INVALID_PARAMS("20301", "invalid params"),
    SELL_EXCEPTION_CREATE_CONTRACT_ERROR("20302", "create contract error"),
    SELL_CANCEL_EXCEPTION_NOT_EXIST("20303", "listing not exist"),
    SELL_CANCEL_EXCEPTION_EXPIRED("20304", "expire"),
    /**
     * 20400 buy
     */
    BUY_EXCEPTION_NOT_START("20401", "not start"),
    BUY_EXCEPTION_EXPIRED("20402", "expired"),
    BUY_EXCEPTION_CANCELED("20403", "canceled"),
    BUY_EXCEPTION_NO_ENOUGH_QUANTITY("20404", "no enough quantity"),
    /**
     * 20500 offer
     */
    OFFER_EXCEPTION_INVALID_PARAMS("20501", "invalid params"),
    OFFER_ACCEPT_EXCEPTION_NOT_EXIST("20502", "offer not exist"),
    OFFER_ACCEPT_EXCEPTION_EXPIRED("20503", "expired"),
    OFFER_EXCEPTION_EXPIRED("20504", "expired")
    /**
     * 20600 accept
     */

    ;



    private String code;
    private String desc;

    NftExchangeErrorCodes(String code, String msg) {
        this.code = code;
        this.desc = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
