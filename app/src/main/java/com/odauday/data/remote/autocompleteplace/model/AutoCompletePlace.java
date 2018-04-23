package com.odauday.data.remote.autocompleteplace.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseResponse;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/22/18.
 */
public class AutoCompletePlace implements BaseResponse, Parcelable {
    
    public static final Creator<AutoCompletePlace> CREATOR = new Creator<AutoCompletePlace>() {
        @Override
        public AutoCompletePlace createFromParcel(Parcel in) {
            return new AutoCompletePlace(in);
        }

        @Override
        public AutoCompletePlace[] newArray(int size) {
            return new AutoCompletePlace[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private GeoLocation location;
    private boolean isSearched;
    
    public AutoCompletePlace() {

    }
    
    public AutoCompletePlace(String id, String name,
              GeoLocation location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
    
    protected AutoCompletePlace(Parcel in) {
        id = in.readString();
        name = in.readString();
        location = in.readParcelable(GeoLocation.class.getClassLoader());
        isSearched = in.readByte() != 0;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public GeoLocation getLocation() {
        return location;
    }
    
    public void setLocation(GeoLocation location) {
        this.location = location;
    }
    
    public boolean isSearched() {
        return isSearched;
    }
    
    public void setSearched(boolean searched) {
        isSearched = searched;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AutoCompletePlace that = (AutoCompletePlace) o;
        return ObjectUtils.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id);
    }
    
    @Override
    public String toString() {
        return "AutoCompletePlace{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", location=" + location +
               ", isSearched=" + isSearched +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(id);
        dest.writeString(name);
        dest.writeParcelable(location, flags);
        dest.writeByte((byte) (isSearched ? 1 : 0));
    }
}
