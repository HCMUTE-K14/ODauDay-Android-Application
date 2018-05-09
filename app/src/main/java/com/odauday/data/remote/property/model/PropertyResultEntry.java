package com.odauday.data.remote.property.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.model.Image;
import com.odauday.utils.ObjectUtils;
import java.util.List;

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
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("type_id")
    @Expose
    private String searchType;
    @SerializedName("num_of_bedroom")
    @Expose
    private int numOfBedRooms;
    @SerializedName("num_of_bathroom")
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
    @SerializedName("images")
    @Expose
    private List<Image> images;
    @SerializedName("time_contact")
    @Expose
    private String timeContact;
    
    public PropertyResultEntry() {
    
    }
    
    protected PropertyResultEntry(Parcel in) {
        id = in.readString();
        address = in.readString();
        price = in.readDouble();
        searchType = in.readString();
        numOfBedRooms = in.readInt();
        numOfBathRooms = in.readInt();
        numOfParkings = in.readInt();
        location = in.readParcelable(GeoLocation.class.getClassLoader());
        isFavorite = in.readByte() != 0;
        isVisited = in.readByte() != 0;
        images = in.createTypedArrayList(Image.CREATOR);
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
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
    
    public List<Image> getImages() {
        return images;
    }
    
    public void setImages(List<Image> images) {
        this.images = images;
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
               ObjectUtils.equals(address, entry.address) &&
               ObjectUtils.equals(location, entry.location);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils
            .hash(id, address, numOfBedRooms, numOfBathRooms, numOfParkings, location,
                isFavorite, isVisited);
    }
    
    @Override
    public String toString() {
        return "PropertyResultEntry{" +
               "id='" + id + '\'' +
               ", address='" + address + '\'' +
               ", price=" + price +
               ", searchType='" + searchType + '\'' +
               ", numOfBedRooms=" + numOfBedRooms +
               ", numOfBathRooms=" + numOfBathRooms +
               ", numOfParkings=" + numOfParkings +
               ", location=" + location +
               ", isFavorite=" + isFavorite +
               ", isVisited=" + isVisited +
               ", images=" + images +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        
        parcel.writeString(id);
        parcel.writeString(address);
        parcel.writeDouble(price);
        parcel.writeString(searchType);
        parcel.writeInt(numOfBedRooms);
        parcel.writeInt(numOfBathRooms);
        parcel.writeInt(numOfParkings);
        parcel.writeParcelable(location, i);
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
        parcel.writeByte((byte) (isVisited ? 1 : 0));
        parcel.writeTypedList(images);
    }
}
