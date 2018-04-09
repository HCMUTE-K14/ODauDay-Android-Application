package com.odauday.utils;

import java.util.Arrays;

/**
 * Created by infamouSs on 4/3/2018.
 */
public class ObjectUtils {
    
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
    
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }
    
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }
    
    public static boolean isNull(Object obj) {
        return obj == null;
    }
    
    public static boolean nonNull(Object obj) {
        return obj != null;
    }
}
