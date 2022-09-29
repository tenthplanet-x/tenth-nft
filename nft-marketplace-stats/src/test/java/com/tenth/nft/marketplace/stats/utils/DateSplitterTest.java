package com.tenth.nft.marketplace.stats.utils;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

/**
 * @author shijie
 */
public class DateSplitterTest {

    @Test
    public void splitSameDate(){

        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis() + 60000;

        List<DateSplitter.DailyRange> rages = DateSplitter.dailySplit(start, end);
        for(DateSplitter.DailyRange range: rages){
            System.out.println(
                    String.format(
                            "from: %s, to: %s",
                            new DateTime(range.start()).toString("yyyy-MM-dd HH:mm: ss"),
                            new DateTime(range.end()).toString("yyyy-MM-dd HH:mm: ss")
                    )
            );
        }

    }

}
