package com.xxl.job.admin.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtil {



    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateFormat dfDateTime = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT);


    public static String getFormatDate(final Date date) {
        if (date == null) {
            return "";
        }
        return DateUtil.dfDateTime.format(date);
    }


    public static String getFormatDate() {
        Calendar calendar = Calendar.getInstance();
        return DateUtil.dfDateTime.format(calendar.getTime());
    }


}