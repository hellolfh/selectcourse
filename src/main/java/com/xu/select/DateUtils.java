package com.xu.select;


import org.springframework.util.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static Date getString2Date(String format, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        ParsePosition parseposition = new ParsePosition(0);

        return simpleDateFormat.parse(str, parseposition);
    }

    public static final String getDate2String(String format, Date date) {
        if (date != null) {
            if (StringUtils.isEmpty(format)) {
                format = YYYY_MM_DD_HH_MM_SS;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }
}
