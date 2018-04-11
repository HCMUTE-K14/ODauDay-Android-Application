package com.odauday.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import com.odauday.R;

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
    
    public static void showHideView(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    
    public static void showGoToSettingsDialog(AppCompatActivity activity) {
        if (activity == null) {
            return;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(R.string.message_location_services_disabled)
                  .setCancelable(false)
                  .setPositiveButton(activity.getString(R.string.txt_settings),
                            (dialog, id) -> activity.startActivity(
                                      new Intent("android.settings.LOCATION_SOURCE_SETTINGS")));
        alertDialogBuilder
                  .setNegativeButton(activity.getString(R.string.txt_cancel),
                            (dialog, id) -> dialog.cancel());
        alertDialogBuilder.create().show();
    }
}
