package com.odauday.ui.propertydetail.common;

import android.graphics.Color;
import com.google.gson.annotations.SerializedName;
import com.odauday.R;

/**
 * Created by infamouSs on 5/12/18.
 */
public enum DirectionMode {
    
    @SerializedName("0")
    DRIVING("driving", "Driving via ", "d", R.drawable.ic_directions_driving, 10, 30, 40),
    @SerializedName("1")
    TRANSIT("transit", "Via Public Transport", "r", R.drawable.ic_directions_bus, 10, 30, 60),
    @SerializedName("2")
    CYCLING("bicycling", "Cycling via ", "b", R.drawable.ic_directions_cycling, 5, 10, 15),
    @SerializedName("3")
    WALKING("walking", "Walking via ", "w", R.drawable.ic_directions_walking, 2, 5, 10);
    
    private final String mApiString;
    private final int mDrawableResourceId;
    private final String mInfoWindowString;
    private final String mMapsApiFlag;
    private final int mLongDuration;
    private final int mMediumDuration;
    private final int mShortDuration;
    
    DirectionMode(String apiString, String infoWindow, String mapsApiFlag,
        int drawableResourceId, int shortDuration, int mediumDuration, int longDuration) {
        this.mApiString = apiString;
        this.mInfoWindowString = infoWindow;
        this.mShortDuration = shortDuration;
        this.mMediumDuration = mediumDuration;
        this.mLongDuration = longDuration;
        this.mDrawableResourceId = drawableResourceId;
        this.mMapsApiFlag = mapsApiFlag;
    }
    
    public String getMapsApiFlag() {
        return this.mMapsApiFlag;
    }
    
    public String getApiString() {
        return this.mApiString;
    }
    
    public int getColorFromDuration(int duration) {
        double power = ((double) (duration / 60)) / ((double) this.mLongDuration);
        if (power > 1.0d) {
            power = 1.0d;
        }
        double H = (0.4d * (1.0d - power)) * 360.0d;
        return Color.HSVToColor(new float[]{(float) H, (float) 0.9d, (float) 0.9d});
    }
    
    
    public static DirectionMode getModeByIndex(int index) {
        switch (index) {
            case 0:
                return DRIVING;
            case 1:
                return TRANSIT;
            case 2:
                return CYCLING;
            case 3:
                return WALKING;
            default:
                return null;
        }
    }
    
    public String getInfoWindowString() {
        return this.mInfoWindowString;
    }
    
    public int getDrawableResourceId() {
        return this.mDrawableResourceId;
    }
    
    public int getLongDuration() {
        return mLongDuration;
    }
    
    public int getMediumDuration() {
        return mMediumDuration;
    }
    
    public int getShortDuration() {
        return mShortDuration;
    }
}
