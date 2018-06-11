package com.odauday.utils;

import android.content.Context;
import com.odauday.R;

/**
 * Created by kunsubin on 6/8/2018.
 */

public class DateTimeUtils {
    public static String getTimeNotification(long dateMillisecondNotification,Context context){
        long millisecond=System.currentTimeMillis()-dateMillisecondNotification;
        if(millisecond<0){
            return "";
        }
        long day=getDay(millisecond);
        long hour=getHour(millisecond);
        long minute=getMinute(millisecond);
        if(day>0){
            return day+" "+context.getString(R.string.txt_day);
        }
        if(hour>0){
            return hour+" "+context.getString(R.string.txt_hour);
        }
        if(minute>0){
            return minute+" "+context.getString(R.string.txt_minute);
        }
        return context.getString(R.string.txt_now);
    }
    public static long getDay(long millisecond){
        return getHour(millisecond)/24;
    }
    public static long getHour(long millisecond){
        return getMinute(millisecond)/60;
    }
    public static long getMinute(long millisecond){
        return getSecond(millisecond)/60;
    }
    public static long getSecond(long millisecond){
        return millisecond/1000;
    }
    
}
