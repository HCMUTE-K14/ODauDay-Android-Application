package com.odauday.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by infamouSs on 2/27/18.
 */

public class TextUtils {
    
    private static char[] SHORT_MONEY = new char[]{'k', 'm', 'b', 't'};
    
    
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    public static String formatIntToCurrency(float value) {
        return formatIntToCurrency(value, false);
    }
    
    public static String formatIntToCurrency(float value, boolean shortHand) {
        DecimalFormat format = new DecimalFormat("#,##0");
        if (!shortHand || value < 1000.0f) {
            return "$" + format.format((double) value);
        }
        return "$" + shortPriceFormat(Float.valueOf(value).doubleValue(), 0);
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
    public static String doubleFormat(double value){
        BigDecimal number = new BigDecimal(value);
        String result=number.stripTrailingZeros().toPlainString();
        return result;
    }
    public static String formatDecimal(float value){
        NumberFormat formatter = new DecimalFormat("#0.000");
        return formatter.format(value);
    }
}
