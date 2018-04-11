package com.odauday.utils;

import com.odauday.data.remote.search.model.Location;

/**
 * Created by infamouSs on 4/11/18.
 */

public class NumberUtils {
    
    
    public static double distanceBetween2Location(Location a, Location b) {
        android.location.Location pointA = a.toAndroidLocation();
        android.location.Location pointB = b.toAndroidLocation();
        
        return pointA.distanceTo(pointB);
    }
}
