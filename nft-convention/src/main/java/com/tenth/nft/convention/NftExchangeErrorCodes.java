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
    SELL_CANCEL_EXCEPTION_NOT_EXIST("20303", "does not exist"),
    SELL_CANCEL_EXCEPTION_EXPIRED("20304", "expire"),
    /**
     * 20400 buy
     */
    BUY_EXCEPTION_NOT_START("20401", "not start"),
    BUY_EXCEPTION_EXPIRED("20402", "expired"),
    BUY_EXCEPTION_NO_EXIST("20403", "does not exist"),
    BUY_EXCEPTION_NO_ENOUGH_QUANTITY("20404", "no enough quantity"),
    BUY_EXCEPTION_BELONGS_TO_YOU("20405", "belongs to you"),
    /**
     * 20500 offer
     */
    OFFER_EXCEPTION_INVALID_PARAMS("20501", "invalid params"),
    OFFER_EXCEPTION_BELONGS_TO_YOU("20502", "belongs to you"),
    OFFER_EXCEPTION_EXPIRED("20503", "expired"),
    OFFER_EXCEPTION_NOT_EXIST("20504", "does not exist"),

    /**
     * 20600 accept
     */
    ACCEPT_EXCEPTION_OWNS("20601", "offer belongs to you")


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
