package com.odauday.utils;

import java.text.DecimalFormat;

/**
 * Created by infamouSs on 2/27/18.
 */

public class TextUtils {
    
    private static final String[] SHORT_MONEY = new String[]{" Ngàn", " Triệu", " Tỷ", " Trăm tỷ"};
    
    private static final String CURRENCY = " VND ";
    
    
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    public static String build(String... str) {
        StringBuilder builder = new StringBuilder();
        for (String _str : str) {
            builder.append(_str);
        }
        
        return builder.toString().trim();
    }
    
    public static String formatIntToCurrency(float value) {
        return formatIntToCurrency(value, false);
    }
    
    public static String formatIntToCurrency(float value, boolean shortHand) {
        DecimalFormat format = new DecimalFormat("#,##0");
        if (!shortHand || value < 1000.0f) {
            return build(format.format((double) value), CURRENCY);
        }
        return build(shortPriceFormat(Float.valueOf(value).doubleValue(), 0), CURRENCY);
    }
    
    public static String formatIntWithoutCurrency(float value, boolean shortHand) {
        DecimalFormat format = new DecimalFormat("#,##0");
        if (!shortHand || value < 1000.0f) {
            return build(format.format((double) value));
        }
        return build(shortPriceFormat(Float.valueOf(value).doubleValue(), 0));
    }
    
    public static String shortPriceFormat(double n, int iteration) {
        double d = ((double) (((long) n) / 100)) / 10.0d;
        boolean isRound = (d * 10.0d) % 10.0d == 0.0d;
        if (d >= 1000.0d) {
            return shortPriceFormat(d, iteration + 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object valueOf = (d > 99.9d || isRound || (!isRound && d > 9.99d))
                  ? Integer.valueOf((((int) d) * 10) / 10)
                  : d + "";
        return stringBuilder.append(valueOf).append("").append(SHORT_MONEY[iteration]).toString();
    }
    
    
}
