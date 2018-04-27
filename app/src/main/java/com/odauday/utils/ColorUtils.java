package com.odauday.utils;

import android.graphics.Color;

/**
 * Created by infamouSs on 4/2/18.
 */

public class ColorUtils {
    
    public static int alpha(int color, int alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }
}
