package com.tenth.nft.convention;

import com.wallan.router.exception.ErrorCode;

public enum NftExchangeErrorCodes implements ErrorCode {

    /**
     * 20100
     * opensea sdk
     */
    OPEANSEASDK_EXCEPTION("020101", "opeanseasdk exception"),

    /**
     * 20200 mint
     */
    MINT_EXCEPTION("020201", "mint excepiton"),
    MINT_EXCEPTION_INVALID_PARAMS("020202", "invalid mint params"),
    CREATE_EXCEPTION_ILEGAL_BLOCKCHAIN("020203", "illegal blockchain"),

    /**
     * 20300 sell
     */
    LISTING_EXCEPTION_INVALID_PARAMS("020301", "Illegal params"),
    LISTING_EXCEPTION_CREATE_CONTRACT_ERROR("020302", "create contract error"),
    LISTING_CANCEL_EXCEPTION_NOT_EXIST("020303", "Does not exist"),
    LISTING_CANCEL_EXCEPTION_EXPIRED("020304", "Expired"),
    LISTING_BUY_EXPCETION_NO_ENOUGH_ASSETS("020305", "Doesn't have enough assets"),
    LISTING_CREATE_EXCEPTION_ILLEGAL_SIGNATURE("020306", "Illegal signature"),

    /**
     * 20400 buy
     */
    BUY_EXCEPTION_NOT_START("020401", "not start"),
    BUY_EXCEPTION_EXPIRED("020402", "expired"),
    BUY_EXCEPTION_NO_EXIST("020403", "does not exist"),
    BUY_EXCEPTION_NO_ENOUGH_QUANTITY("020404", "no enough quantity"),
    BUY_EXCEPTION_BELONGS_TO_YOU("020405", "belongs to you"),
    BUY_EXCEPTION_ILLEGAL_MINT_STATUS("020406", "Illegal mint status"),


    /**
     * 20500 offer
     */
    OFFER_EXCEPTION_INVALID_PARAMS("020501", "invalid params"),
    OFFER_EXCEPTION_BELONGS_TO_YOU("020502", "belongs to you"),
    OFFER_EXCEPTION_EXPIRED("020503", "expired"),
    OFFER_EXCEPTION_NOT_EXIST("020504", "does not exist"),
    OFFER_EXCEPTION_INVALID_SIGNATURE("020505", "invalid signature"),


    /**
     * 20600 accept
     */
    ACCEPT_EXCEPTION_OWNS("020601", "offer belongs to you"),

    /**
     * 20700
     */
    WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN("020700", "uncorrect pay token"),
    WALLET_PAY_EXCEPTION_LACK_OF_BALANCE("0207001", "don't have enough balance"),
    WALLET_EXCEPTION_UNCORRECT_PASSWORD("0207002", "uncorrect password"),
    WALLET_PAY_EXCEPTION_BIZ_VERIFY_FAILED("0207003", "biz verify failed"),


    /**
     * 20800
     */
    WEB3WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN("020800", "uncorrect pay token"),
    WEB3WALLET_PAY_EXCEPTION_LACK_OF_BALANCE("020801", "don't have enough balance"),
    WEB3WALLET_DONT_HAVE_BIND("020802", "don't have bind wallet"),
    WEB3WALLET_BILL_DOES_NOT_EXIST("020803", "bill doesn't exist"),
    WEB3WALLET_BILL_HAS_FAIL_STATUS("020804", "bill doesn't exist"),


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
