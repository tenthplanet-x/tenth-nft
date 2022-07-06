package com.tenth.nft.convention.utils;

import org.joda.time.*;

public class Times {

    public static final Long DEFAULT_EXPIRE_DURATION = 86400000 * 3l;

    public static boolean isExpired(Long time) {
        return null != time && time < System.currentTimeMillis();
    }

    public static int getAge(DateTime birthDateTime) {
        LocalDate birthday = birthDateTime.toLocalDate();
        LocalDate now = new LocalDate();
        Period period = new Period(birthday, now, PeriodType.yearMonthDay());
        return period.getYears();
    }

    public static Long createExpiredAt(Long duration) {
        return System.currentTimeMillis() + duration;
    }

    /**
     * now earlier than specific timestamp
     * @param timestamp
     * @return
     */
    public static boolean earlierThan(Long timestamp) {
        return System.currentTimeMillis() < timestamp;
    }
}
