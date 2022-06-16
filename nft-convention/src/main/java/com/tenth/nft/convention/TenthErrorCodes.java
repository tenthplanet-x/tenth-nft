package com.tenth.nft.convention;

import com.wallan.router.exception.ErrorCode;

public enum TenthErrorCodes implements ErrorCode {

    /**
     * opensea sdk
     */
    OPEANSEASDK_EXCEPTION("7001", "opeanseasdk exception"),


    ;


    private String code;
    private String desc;

    TenthErrorCodes(String code, String msg) {
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
