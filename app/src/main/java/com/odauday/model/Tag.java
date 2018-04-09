package com.odauday.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/4/2018.
 */
public class Tag implements Parcelable {
    
    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }
        
        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    
    public Tag(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    protected Tag(Parcel in) {
        id = in.readString();
        name = in.readString();
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return ObjectUtils.equals(id, tag.id) &&
               ObjectUtils.equals(name, tag.name);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id, name);
    }
    
    @Override
    public String toString() {
        return "Tag{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
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
    }
}
