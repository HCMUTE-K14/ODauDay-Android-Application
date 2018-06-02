package com.odauday.utils;

import android.net.Uri;
import com.odauday.R;
import com.odauday.RootApplication;
import com.odauday.api.EndPoint;
import com.odauday.config.AppConfig;
import com.odauday.config.AppConfig.LANGUAGE;
import com.odauday.data.remote.property.model.GeoLocation;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by infamouSs on 2/27/18.
 */

public class TextUtils {
    
    public static final Map<Integer, String> MAP_BATHROOMS = TextUtils
        .getRoomCountMap(R.array.bathroom_count_int, R.array.bathroom_count_string);
    public static final Map<Integer, String> MAP_BEDROOMS = TextUtils
        .getRoomCountMap(R.array.bedroom_count_int, R.array.bedroom_count_string);
    public static final Map<Integer, String> MAP_PARKING = TextUtils
        .getRoomCountMap(R.array.parking_count_int, R.array.parking_count_string);
    private static final String[] SHORT_MONEY = new String[]{" Ngàn", " Triệu", " Tỷ", " Trăm tỷ"};
    private static final String CURRENCY = " VND ";
    
    public static String buildUrlStaticMap(GeoLocation geoLocation, float zoom, String size) {
        if (zoom <= 0) {
            zoom = 15f;
        }
        if (TextUtils.isEmpty(size)) {
            size = "600x300";
        }
        
        return Uri.parse(EndPoint.BASE_URL + EndPoint.STATIC_MAP)
            .buildUpon()
            .appendQueryParameter("location",
                geoLocation.getLatitude() + "," + geoLocation.getLongitude())
            .appendQueryParameter("zoom", String.valueOf(zoom))
            .appendQueryParameter("size", size)
            .toString();
    }
    
    public static String getTagsById(int id) {
        return RootApplication.getContext().getResources().getStringArray(R.array.tags)[id];
    }
    
    public static String generatorUUID() {
        return UUID.randomUUID().toString();
    }
    
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
    
    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }
    
    public static String getPriceText(int minValue, int maxValue,
        boolean shortHand) {
        String anyText = RootApplication.getContext().getString(R.string.txt_any);
        if (minValue <= 0 && maxValue > 0) {
            return (shortHand ? "&lt;" : "Below ") +
                   formatIntToCurrency((float) maxValue, shortHand);
        } else if (maxValue <= 0 && minValue > 0) {
            String text = formatIntToCurrency((float) minValue, shortHand);
            return shortHand ? text + "+" : "Above " + text;
        } else if (maxValue <= 0 && minValue <= 0) {
            return shortHand ? "" : anyText;
        } else if (minValue == maxValue) {
            return formatIntToCurrency((float) minValue, shortHand);
        } else {
            return formatIntToCurrency((float) minValue, shortHand) + (shortHand ? "-" : " - ") +
                   formatIntToCurrency((float) maxValue, shortHand);
        }
    }
    
    public static String getBedRangeDisplayString(int minimumBeds, int maximumBeds) {
        return getCriteriaRangeDisplayString(MAP_BEDROOMS, minimumBeds, maximumBeds,
            R.plurals.beds);
    }
    
    public static String getBathRangeDisplayString(int minimumBaths, int maximumBaths) {
        try {
            return getCriteriaRangeDisplayString(MAP_BATHROOMS, minimumBaths,
                maximumBaths, R.plurals.baths);
        } catch (NullPointerException e) {
            return "";
        }
    }
    
    public static String getParkingRangeDisplayString(int parkingCount) {
        try {
            return getCriteriaRangeDisplayString(MAP_PARKING, parkingCount,
                parkingCount, R.plurals.parking)
                .replace(RootApplication.getContext().getString(R.string.txt_no_parking),
                    RootApplication.getContext().getString(R.string.txt_no));
        } catch (NullPointerException e) {
            return "";
        }
    }
    
    private static String getCriteriaRangeDisplayString(
        Map<Integer, String> intToStringMap,
        int minimumRoomCount, int maximumRoomCount, int pluralResId) {
        String minString = intToStringMap.get(minimumRoomCount);
        String maxString = intToStringMap.get(maximumRoomCount);
        if (!minString.equals(maxString)) {
            return minString + "-" + RootApplication.getContext().getResources()
                .getQuantityString(pluralResId, 2, maxString);
        } else if (maxString.equals(RootApplication.getContext().getString(R.string.txt_any))) {
            return "";
        } else {
            return RootApplication.getContext().getResources()
                .getQuantityString(pluralResId, maximumRoomCount, maxString);
        }
    }
    
    public static Map<Integer, String> getRoomCountMap(int intArrayRes,
        int stringArrayRes) {
        Map<Integer, String> map = new LinkedHashMap<>();
        int[] names = RootApplication.getContext().getResources().getIntArray(intArrayRes);
        int count = 0;
        for (String value : RootApplication.getContext().getResources()
            .getStringArray(stringArrayRes)) {
            
            map.put(names[count], value);
            count++;
        }
        return map;
    }
    
    public static String doubleFormat(double value) {
        BigDecimal number = new BigDecimal(value);
        return number.stripTrailingZeros().toPlainString();
    }
    
    public static String formatDecimal(double value) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        return formatter.format(value);
    }
    
    public static String formatNumber(double value, LANGUAGE locale) {
        NumberFormat formatter = new DecimalFormat("#,###.00");
        if (locale == LANGUAGE.EN) {
            return formatter.format(value);
        } else {
            String number = formatter.format(value).replace(".", "/");
            number = number.replace(",", ".");
            number = number.replace("/", ",");
            return number;
        }
    }
    
    public static String buildImageUrl(String fileName) {
        return EndPoint.IMAGE +
               "/" +
               fileName;
    }
    
    public static String formatDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(AppConfig.PATTERN_DATE);
        return dateFormat.format(date);
    }
    
    public static String formatGeoLocationForRequest(GeoLocation location) {
        return location.getLatitude() + "," + location.getLongitude();
    }
    
    public static String getImageUrl(String url) {
        return EndPoint.BASE_URL + url;
    }
    
    public static String formatDateForDisplayHistory(Date dateCreated) {
        long minDiff = (System.currentTimeMillis() - dateCreated.getTime()) / 60000;
        String text = "";
        if (minDiff < 60) {
            text = minDiff + "m";
        } else if (minDiff < 1440) {
            text = (minDiff / 60) + "h";
        } else {
            text = (minDiff / 1440) + "d";
        }
        
        return text;
    }
    
    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    
    public static String formatAddress(String address){
        return address.replaceAll("Unnamed Road,", "").trim();
    }
}
