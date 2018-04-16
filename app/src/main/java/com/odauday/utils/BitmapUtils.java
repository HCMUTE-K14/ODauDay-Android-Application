package com.odauday.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by infamouSs on 4/13/18.
 */
public class BitmapUtils {
    
    public static Bitmap resize(Context context, int resourceId, int height, int width) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources()
                  .getDrawable(resourceId);
        Bitmap b = bitmapDrawable.getBitmap();
        return Bitmap.createScaledBitmap(b, width, height, false);
    }
}
