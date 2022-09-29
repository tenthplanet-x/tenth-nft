package com.tenth.nft.marketplace.common.dto;

import com.tpulse.gs.convention.dao.defination.field.SimpleFieldDeserializer;

import java.math.BigDecimal;

/**
 * @author shijie
 */
public class BigDecimalToStringDecoder implements SimpleFieldDeserializer {

    @Override
    public Object deserialize(Object origin) {
        if(null != origin){
            return ((BigDecimal)origin).toPlainString();
        }
        return "0";
    }
}
