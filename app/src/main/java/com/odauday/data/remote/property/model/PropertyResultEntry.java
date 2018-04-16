package com.odauday.data.remote.property.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/12/18.
 */

public class PropertyResultEntry implements Parcelable {
    
    public static final Creator<PropertyResultEntry> CREATOR = new Creator<PropertyResultEntry>() {
        @Override
        public PropertyResultEntry createFromParcel(Parcel in) {
            return new PropertyResultEntry(in);
        }

        @Override
        public PropertyResultEntry[] newArray(int size) {
            return new PropertyResultEntry[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("type")
    @Expose
    private String searchType;
    @SerializedName("num_of_bedrooms")
    @Expose
    private int numOfBedRooms;
    @SerializedName("num_of_bathrooms")
    @Expose
    private int numOfBathRooms;
    @SerializedName("num_of_parking")
    @Expose
    private int numOfParkings;
    @SerializedName("location")
    @Expose
    private GeoLocation location;
    @SerializedName("is_favorite")
    @Expose
    private boolean isFavorite;
    @SerializedName("is_visited")
    @Expose
    private boolean isVisited;
    
    public PropertyResultEntry() {

    }
    
    public PropertyResultEntry(String id, String name, String address, int numOfBedRooms,
              int numOfBathRooms, int numOfParkings,
              GeoLocation location, boolean isFavorite, boolean isVisited) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numOfBedRooms = numOfBedRooms;
        this.numOfBathRooms = numOfBathRooms;
        this.numOfParkings = numOfParkings;
        this.location = location;
        this.isFavorite = isFavorite;
        this.isVisited = isVisited;
    }

    protected PropertyResultEntry(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        price = in.readDouble();
        searchType = in.readString();
        numOfBedRooms = in.readInt();
        numOfBathRooms = in.readInt();
        numOfParkings = in.readInt();
        location = in.readParcelable(GeoLocation.class.getClassLoader());
        isFavorite = in.readByte() != 0;
        isVisited = in.readByte() != 0;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getNumOfBedRooms() {
        return numOfBedRooms;
    }
    
    public void setNumOfBedRooms(int numOfBedRooms) {
        this.numOfBedRooms = numOfBedRooms;
    }
    
    public int getNumOfBathRooms() {
        return numOfBathRooms;
    }
    
    public void setNumOfBathRooms(int numOfBathRooms) {
        this.numOfBathRooms = numOfBathRooms;
    }
    
    public int getNumOfParkings() {
        return numOfParkings;
    }
    
    public void setNumOfParkings(int numOfParkings) {
        this.numOfParkings = numOfParkings;
    }
    
    public GeoLocation getLocation() {
        return location;
    }
    
    public void setLocation(GeoLocation location) {
        this.location = location;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    public boolean isVisited() {
        return isVisited;
    }
    
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PropertyResultEntry entry = (PropertyResultEntry) o;
        return numOfBedRooms == entry.numOfBedRooms &&
               numOfBathRooms == entry.numOfBathRooms &&
               numOfParkings == entry.numOfParkings &&
               isFavorite == entry.isFavorite &&
               isVisited == entry.isVisited &&
               ObjectUtils.equals(id, entry.id) &&
               ObjectUtils.equals(name, entry.name) &&
               ObjectUtils.equals(address, entry.address) &&
               ObjectUtils.equals(location, entry.location);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils
                  .hash(id, name, address, numOfBedRooms, numOfBathRooms, numOfParkings, location,
                            isFavorite, isVisited);
    }
    
    @Override
    public String toString() {
        return "PropertyResultEntry{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", numOfBedRooms=" + numOfBedRooms +
               ", numOfBathRooms=" + numOfBathRooms +
               ", numOfParkings=" + numOfParkings +
               ", location=" + location +
               ", isFavorite=" + isFavorite +
               ", isVisited=" + isVisited +
               '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeDouble(price);
        parcel.writeString(searchType);
        parcel.writeInt(numOfBedRooms);
        parcel.writeInt(numOfBathRooms);
        parcel.writeInt(numOfParkings);
        parcel.writeParcelable(location, i);
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
        parcel.writeByte((byte) (isVisited ? 1 : 0));
    }
}
