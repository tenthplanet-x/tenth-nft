package com.tenth.nft.marketplace.stats.utils;

import com.google.common.collect.Lists;
import com.tpulse.gs.convention.utils.DateTimes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shijie
 */
public class DateSplitter {

    private static final int DAY_MILLIS = 86400000;

    public record DailyRange(long start, long end){}

    public static List<DailyRange> dailySplit(long start, long end) {

        int days = DateTimes.duration(start, end, DateTimes.TimeDimension.DAY);

        if(days == 0){
            return Lists.newArrayList(new DailyRange(start, end));
        }

        List<DailyRange> ranges = new ArrayList<>();
        Long firstDate = DateTimes.adjustFormatTime(start, DateTimes.TimeDimension.DAY);
        for(int i = 0; i <= days; i++){

            Long _start = firstDate + (i) * DAY_MILLIS;
            Long _end = DateTimes.getRange(_start, DateTimes.TimeDimension.DAY).getEnd();

            if(i == 0){
                _start = start;
            }
            if(i == days){
                _end = end;
            }
            ranges.add(new DailyRange(_start, _end));
        }

        return ranges;
    }
}
