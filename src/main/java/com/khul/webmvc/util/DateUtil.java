package com.khul.webmvc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date getTime(String time){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        try {
            return format.parse(time);
        }catch (ParseException e){
            return null;
        }
    }
}
