package com.tenth.nft.operation.dto;

import com.tpulse.gs.convention.dao.defination.field.SimpleFieldDeserializer;

/**
 * @author shijie
 */
public class FloatToStringDecoder implements SimpleFieldDeserializer {

    @Override
    public Object deserialize(Object origin) {
        if(null != origin){
            return String.format("%s", origin);
        }
        return origin;
    }
}
