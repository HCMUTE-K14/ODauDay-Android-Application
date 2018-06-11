package com.odauday.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 5/31/18.
 */
public class Premium implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("description")
    @Expose
    private String description;
    
    @SerializedName("value")
    @Expose
    private long value;
    
    @SerializedName("status")
    @Expose
    private String status;
    
    @SerializedName("real_value")
    @Expose
    private long realValue;
    
    
    protected Premium(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        value = in.readLong();
        status = in.readString();
        realValue = in.readLong();
    }
    
    public static final Creator<Premium> CREATOR = new Creator<Premium>() {
        @Override
        public Premium createFromParcel(Parcel in) {
            return new Premium(in);
        }
        
        @Override
        public Premium[] newArray(int size) {
            return new Premium[size];
        }
    };
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public long getValue() {
        return value;
    }
    
    public void setValue(long value) {
        this.value = value;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public long getRealValue() {
        return realValue;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Premium premium = (Premium) o;
        return value == premium.value &&
               ObjectUtils.equals(id, premium.id);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id, value);
    }
    
    @Override
    public String toString() {
        return "Premium{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", value=" + value +
               ", status='" + status + '\'' +
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
        dest.writeString(description);
        dest.writeLong(value);
        dest.writeString(status);
        dest.writeLong(realValue);
    }
}
