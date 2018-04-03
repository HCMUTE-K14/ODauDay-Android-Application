package com.odauday.ui.search.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 4/1/18.
 */

public class Point implements Parcelable {

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;

    public Point(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Point(Parcel in) {
        latitude = in.readFloat();
        longitude = in.readFloat();
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;

        if (Float.compare(point.latitude, latitude) != 0) {
            return false;
        }
        return Float.compare(point.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        int result = (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeFloat(latitude);
        parcel.writeFloat(longitude);
    }
}
