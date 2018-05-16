package com.odauday.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import java.io.ByteArrayOutputStream;
import java.io.File;

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
    
    public static byte[] compressImage(File imageFile) {
        try {
            Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.JPEG, 70, bos);
            
            return bos.toByteArray();
        } finally {
        
        }
        
    }
}
