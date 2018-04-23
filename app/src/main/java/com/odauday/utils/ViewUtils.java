package com.odauday.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.odauday.R;

/**
 * Created by infamouSs on 3/26/18.
 */

public class ViewUtils {
    
    public static <T> void startActivity(Activity activity, Class<T> targetActivity) {
        Intent intent = new Intent(activity, targetActivity);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in,
                  android.R.anim.fade_out);
    }
    
    public static <T> void startActivityForResult(Activity activity, Class<T> targetActivity,
              int requestCode) {
        Intent intent = new Intent(activity, targetActivity);
        activity.startActivityForResult(intent, requestCode);
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
    
    public static void hideSoftKeyboard(Context context, IBinder windowToken) {
        if (context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                      Context.INPUT_METHOD_SERVICE);
            if (imm == null) {
                return;
            }
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
