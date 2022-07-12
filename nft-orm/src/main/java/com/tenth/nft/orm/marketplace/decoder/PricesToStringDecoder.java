package com.tenth.nft.orm.marketplace.decoder;

import com.tenth.nft.convention.utils.Prices;
import com.tpulse.gs.convention.dao.defination.field.SimpleFieldDeserializer;

/**
 * @author shijie
 */
public class PricesToStringDecoder implements SimpleFieldDeserializer {

    @Override
    public Object deserialize(Object origin) {
        if(null != origin){
            return Prices.toString((Float)origin);
        }
        return origin;
    }
}
