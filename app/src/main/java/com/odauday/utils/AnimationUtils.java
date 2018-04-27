package com.odauday.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by infamouSs on 3/31/18.
 */

public class AnimationUtils {
    
    public static void fadeOut(View view, float translationY, long duration) {
        view.animate()
            .translationY(translationY)
            .alpha(0.0f)
            .setDuration(duration)
            .start();
    }
    
    public static void fadeIn(View view, float translationY, long duration) {
        view.animate()
            .translationY(translationY)
            .alpha(1.0f)
            .setDuration(duration)
            .start();
    }
    
    public static Animation loadAnimation(Context context, int resourceId) {
        return android.view.animation.AnimationUtils.loadAnimation(context, resourceId);
    }
}
