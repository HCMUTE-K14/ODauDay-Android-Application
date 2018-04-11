package com.odauday.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by infamouSs on 3/27/18.
 */

public class SnackBarUtils {
    
    
    public static void showSnackBar(View rootView, String mMessage) {
        Snackbar.make(rootView, mMessage, Snackbar.LENGTH_LONG)
                  .setAction("Action", null)
                  .show();
    }
    
    public static void showSnackBar(View rootView, String action, String message,
              OnClickListener listener) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
                  .setAction(action, listener)
                  .show();
    }
}
