package com.odauday.utils;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.List;

/**
 * Created by infamouSs on 2/27/18.
 */

public class NetworkUtils {
    
    public static NetworkInfo getInformationNetwork(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
        
        return cm != null ? cm.getActiveNetworkInfo() : null;
    }
    
    public static boolean isNetworkAvailable(final Context context) {
        NetworkInfo info = getInformationNetwork(context);
        return (info != null && info.isConnected());
    }
    
    public static boolean isEnableGPS(Activity activity) {
        List<String> providers = ((LocationManager) activity
                  .getSystemService(Context.LOCATION_SERVICE))
                  .getProviders(true);
        if (providers == null ||
            !(providers.contains("gps") || providers.contains("network"))) {
            
            return false;
        }
        return true;
    }
    
    public boolean isOnWiFi(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo networkInfo = connManager != null ? connManager.getActiveNetworkInfo() : null;
        return networkInfo != null &&
               networkInfo.isConnected() &&
               networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        
    }
}
