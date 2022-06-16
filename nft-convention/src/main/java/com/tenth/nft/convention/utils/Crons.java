package com.tenth.nft.convention.utils;

import com.google.common.base.Joiner;

/**
 * cron expression
 * @author shijie
 */
public class Crons {

    public static String hour(int fullHour) {
        return String.format("0 0 %s * * ? *", fullHour);
    }

    public static String minute(Integer... minutes){
        if(null == minutes || minutes.length == 0){
            return "0 * * * * ? *";
        }else{
            return String.format("0 %s * * * ? *", Joiner.on(",").join(minutes));
        }
    }

}
