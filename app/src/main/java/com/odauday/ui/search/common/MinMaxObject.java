package com.odauday.ui.search.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/1/18.
 */

public class MinMaxObject<T> implements Parcelable {
    
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<MinMaxObject> CREATOR = new Parcelable.Creator<MinMaxObject>() {
        
        public MinMaxObject createFromParcel(Parcel in) {
            return new MinMaxObject(in);
        }
        
        public MinMaxObject[] newArray(int size) {
            return new MinMaxObject[size];
        }
    };
    private static ClassLoader mClassLoader;
    @SerializedName("min")
    @Expose
    private T min;
    @SerializedName("max")
    @Expose
    private T max;
    
    public MinMaxObject(T min, T max) {
        this.min = min;
        this.max = max;
        if (this.min != null && this.max != null) {
            MinMaxObject.mClassLoader = min.getClass().getClassLoader();
        }
    }
    
    @SuppressWarnings("unchecked")
    private MinMaxObject(Parcel parcelIn) {
        try {
            //reading the passed value
            min = (T) parcelIn.readValue(MinMaxObject.mClassLoader);
            max = (T) parcelIn.readValue(MinMaxObject.mClassLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public T getMin() {
        return min;
    }
    
    public void setMin(T min) {
        this.min = min;
    }
    
    public T getMax() {
        return max;
    }
    
    public void setMax(T max) {
        this.max = max;
    }
    
    
    public MinMaxObject normalize() {
        try {
            int maxValue = Integer.valueOf(String.valueOf(max));
            int minValue = Integer.valueOf(String.valueOf(min));
            if (maxValue == -1 && minValue == -1) {
                return null;
            }
            
            if (maxValue == -1 && minValue >= 0) {
                Timber.d("Normalize minmaxobject 2");
                
                return new MinMaxObject<Integer>(minValue, null);
            }
            
            if (maxValue >= 0 && minValue == -1) {
                Timber.d("Normalize minmaxobject 3");
                
                return new MinMaxObject<Integer>(null, maxValue);
            }
            Timber.d("Normalize minmaxobject 4");
            
            return new MinMaxObject<Integer>(minValue, maxValue);
            
        } catch (Exception ex) {
            return this;
        }
    }
    
    
    @Override
    public String toString() {
        return "MinMaxObject{" +
               "min=" + min +
               ", max=" + max +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcelOut, int flags) {
        parcelOut.writeValue(min);
        parcelOut.writeValue(max);
    }
    
    
}
