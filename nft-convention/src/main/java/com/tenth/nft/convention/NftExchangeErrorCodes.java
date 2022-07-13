package com.tenth.nft.convention;

import com.wallan.router.exception.ErrorCode;

public enum NftExchangeErrorCodes implements ErrorCode {

    /**
     * opensea sdk
     */
    OPEANSEASDK_EXCEPTION("7001", "opeanseasdk exception"),

    EXCHANGE_EXCEPTION_NO_ENOUGH_QUANTITY("8001", "no enough quantity"),
    SELL_EXCEPTION_CREATE_CONTRACT_ERROR("8002", "create contract error"),
    BUY_EXCEPTION_NOT_START("8003", "not start"),
    BUY_EXCEPTION_EXPIRED("8004", "expired"),
    BUY_EXCEPTION_CANCELED("8005", "canceled"),
    BUY_EXCEPTION_NO_ENOUGH_QUANTITY("8006", "no enough quantity"),
    MINT_EXCEPTION("8007", "mint excepiton")
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
