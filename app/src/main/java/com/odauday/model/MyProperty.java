package com.odauday.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.ui.search.navigation.PropertyType;
import com.odauday.utils.ObjectUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by infamouSs on 4/25/18.
 */
public class MyProperty implements Parcelable {
    
    public static final String PENDING = "pending";
    public static final String ACTIVE = "active";
    public static final String EXPIRED = "expired";
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("address")
    @Expose
    private String address;
    
    @SerializedName("code")
    @Expose
    private String code;
    
    @SerializedName("location")
    @Expose
    private GeoLocation location;
    
    @SerializedName("postcode")
    @Expose
    private int postcode;
    
    @SerializedName("status")
    @Expose
    private String status;
    
    @SerializedName("price")
    @Expose
    private double price;
    
    @SerializedName("description")
    @Expose
    private String description;
    
    @SerializedName("num_of_bedroom")
    @Expose
    private int numOfBedRoom;
    
    @SerializedName("num_of_bathroom")
    @Expose
    private int numOfBathRoom;
    
    @SerializedName("num_of_parking")
    @Expose
    private int numOfParking;
    
    @SerializedName("land_size")
    @Expose
    private double size;
    
    @SerializedName("type_id")
    @Expose
    private String type_id;
    
    @SerializedName("time_contact")
    @Expose
    private String time_contact;
    
    @SerializedName("date_end")
    @Expose
    private Date date_end;
    
    @SerializedName("emails")
    @Expose
    private List<MyEmail> emails;
    
    @SerializedName("phones")
    @Expose
    private List<MyPhone> phones;
    
    @SerializedName("images")
    @Expose
    private List<Image> images;
    
    @SerializedName("tags")
    @Expose
    private List<Tag> tags;
    
    @SerializedName("categories")
    @Expose
    private List<PropertyType> categories;
    
    @SerializedName("user_id_created")
    @Expose
    private String userIdCreated;
    
    public MyProperty() {
    
    }
    
    protected MyProperty(Parcel in) {
        id = in.readString();
        address = in.readString();
        code = in.readString();
        location = in.readParcelable(GeoLocation.class.getClassLoader());
        postcode = in.readInt();
        status = in.readString();
        price = in.readDouble();
        description = in.readString();
        numOfBedRoom = in.readInt();
        numOfBathRoom = in.readInt();
        numOfParking = in.readInt();
        size = in.readDouble();
        type_id = in.readString();
        time_contact = in.readString();
        images = in.createTypedArrayList(Image.CREATOR);
        tags = in.createTypedArrayList(Tag.CREATOR);
        
        List<Integer> list = new ArrayList<>();
        in.readList(list, Integer.class.getClassLoader());
        
        categories = convertPropertyTypeByListInteger(list);
    }
    
    public List<PropertyType> convertPropertyTypeByListInteger(List<Integer> propertyType) {
        List<PropertyType> list = new ArrayList<>();
        if (propertyType == null) {
            return list;
        }
        
        for (int i : propertyType) {
            list.add(PropertyType.getById(i));
        }
        return list;
    }
    
    public static final Creator<MyProperty> CREATOR = new Creator<MyProperty>() {
        @Override
        public MyProperty createFromParcel(Parcel in) {
            return new MyProperty(in);
        }
        
        @Override
        public MyProperty[] newArray(int size) {
            return new MyProperty[size];
        }
    };
    
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
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public GeoLocation getLocation() {
        return location;
    }
    
    public void setLocation(GeoLocation location) {
        this.location = location;
    }
    
    public int getPostcode() {
        return postcode;
    }
    
    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getNumOfBedRoom() {
        return numOfBedRoom;
    }
    
    public void setNumOfBedRoom(int numOfBedRoom) {
        this.numOfBedRoom = numOfBedRoom;
    }
    
    public int getNumOfBathRoom() {
        return numOfBathRoom;
    }
    
    public void setNumOfBathRoom(int numOfBathRoom) {
        this.numOfBathRoom = numOfBathRoom;
    }
    
    public int getNumOfParking() {
        return numOfParking;
    }
    
    public void setNumOfParking(int numOfParking) {
        this.numOfParking = numOfParking;
    }
    
    public double getSize() {
        return size;
    }
    
    public void setSize(double size) {
        this.size = size;
    }
    
    public String getType_id() {
        return type_id;
    }
    
    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
    
    public String getTime_contact() {
        return time_contact;
    }
    
    public void setTime_contact(String time_contact) {
        this.time_contact = time_contact;
    }
    
    public Date getDate_end() {
        return date_end;
    }
    
    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }
    
    public List<MyEmail> getEmails() {
        return emails;
    }
    
    public void setEmails(List<MyEmail> emails) {
        this.emails = emails;
    }
    
    public List<MyPhone> getPhones() {
        return phones;
    }
    
    public void setPhones(List<MyPhone> phones) {
        this.phones = phones;
    }
    
    public List<Image> getImages() {
        return images;
    }
    
    public void setImages(List<Image> images) {
        this.images = images;
    }
    
    public List<Tag> getTags() {
        return tags;
    }
    
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    public List<PropertyType> getCategories() {
        return categories;
    }
    
    public void setCategories(List<PropertyType> categories) {
        this.categories = categories;
    }
    
    public void setCategoriesByIntegerList(List<Integer> categories) {
        
        this.categories = convertPropertyTypeByListInteger(categories);
    }
    
    public String getUserIdCreated() {
        return userIdCreated;
    }
    
    public void setUserIdCreated(String userIdCreated) {
        this.userIdCreated = userIdCreated;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyProperty that = (MyProperty) o;
        return postcode == that.postcode &&
               ObjectUtils.equals(id, that.id) &&
               ObjectUtils.equals(code, that.code) &&
               ObjectUtils.equals(location, that.location);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id, code, location, postcode);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(id);
        dest.writeString(address);
        dest.writeString(code);
        dest.writeParcelable(location, flags);
        dest.writeInt(postcode);
        dest.writeString(status);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeInt(numOfBedRoom);
        dest.writeInt(numOfBathRoom);
        dest.writeInt(numOfParking);
        dest.writeDouble(size);
        dest.writeString(type_id);
        dest.writeString(time_contact);
        dest.writeTypedList(images);
        dest.writeTypedList(tags);
        List<Integer> listCategory = PropertyType.convertToArrayInt(categories);
        dest.writeList(listCategory);
    }
    
    @Override
    public String toString() {
        return "MyProperty{" +
               "id='" + id + '\'' +
               ", address='" + address + '\'' +
               ", code='" + code + '\'' +
               ", location=" + location +
               ", postcode=" + postcode +
               ", status='" + status + '\'' +
               ", price=" + price +
               ", description='" + description + '\'' +
               ", numOfBedRoom=" + numOfBedRoom +
               ", numOfBathRoom=" + numOfBathRoom +
               ", numOfParking=" + numOfParking +
               ", size=" + size +
               ", type_id='" + type_id + '\'' +
               ", time_contact='" + time_contact + '\'' +
               ", date_end=" + date_end +
               ", emails=" + emails +
               ", phones=" + phones +
               ", images=" + images +
               ", tags=" + tags +
               ", categories=" + categories +
               '}';
    }
}
