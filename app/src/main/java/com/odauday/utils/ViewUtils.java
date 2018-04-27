package com.odauday.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

/**
 * Created by infamouSs on 3/26/18.
 */

public class ViewUtils {
    
    public static <T> void startActivity(Activity activity, Class<T> targetActivity) {
        Intent intent = new Intent(activity, targetActivity);
        activity.startActivity(intent);
    }
    
    public static void disabledUserInteraction(Activity view) {
        view.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    
    public static void disabledUserInteraction(Fragment view) {
        if (view.getActivity() == null) {
            return;
        }
        view.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    
    public static void enabledUserInteraction(Activity view) {
        view.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    
    public static void enabledUserInteraction(Fragment view) {
        if (view.getActivity() == null) {
            return;
        }
        view.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
