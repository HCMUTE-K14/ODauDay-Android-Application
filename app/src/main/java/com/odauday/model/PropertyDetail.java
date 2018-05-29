package com.odauday.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.R;
import com.odauday.config.AppConfig;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.utils.TextUtils;
import java.util.List;

/**
 * Created by infamouSs on 5/7/18.
 */
public class PropertyDetail implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("images")
    @Expose
    private List<Image> images;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("contact_time")
    @Expose
    private String contactTime;
    @SerializedName("num_of_bedroom")
    @Expose
    private int numOfBedrooms;
    @SerializedName("num_of_bathroom")
    @Expose
    private int numOfBathrooms;
    @SerializedName("num_of_parking")
    @Expose
    private int numOfParkings;
    @SerializedName("land_size")
    @Expose
    private double size;
    @SerializedName("categories")
    @Expose
    private List<Category> categories;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("location")
    @Expose
    private GeoLocation location;
    @SerializedName("is_favorite")
    @Expose
    private boolean isFavorite;
    
    @SerializedName("emails")
    @Expose
    private List<MyEmail> emails;
    
    @SerializedName("phones")
    @Expose
    private List<MyPhone> phones;
    
    @SerializedName("note")
    @Expose
    private Note note;
    
    public PropertyDetail() {
    
    }
    
    
    public PropertyDetail(HistoryDetail historyDetail) {
        this.id = historyDetail.getPropertyId();
        this.address = historyDetail.getAddress();
        this.isFavorite = historyDetail.isFavorite();
        this.images = historyDetail.getImages();
        this.numOfBathrooms = historyDetail.getNumOfBathrooms();
        this.numOfBedrooms = historyDetail.getNumOfBedrooms();
        this.numOfParkings = historyDetail.getNumOfParkings();
    }
    
    protected PropertyDetail(Parcel in) {
        id = in.readString();
        images = in.createTypedArrayList(Image.CREATOR);
        type = in.readInt();
        price = in.readDouble();
        address = in.readString();
        contactTime = in.readString();
        numOfBedrooms = in.readInt();
        numOfBathrooms = in.readInt();
        numOfParkings = in.readInt();
        size = in.readDouble();
        tags = in.createTypedArrayList(Tag.CREATOR);
        description = in.readString();
        location = in.readParcelable(GeoLocation.class.getClassLoader());
        isFavorite = in.readByte() != 0;
        phones = in.createTypedArrayList(MyPhone.CREATOR);
        note = in.readParcelable(Note.class.getClassLoader());
    }
    
    public static final Creator<PropertyDetail> CREATOR = new Creator<PropertyDetail>() {
        @Override
        public PropertyDetail createFromParcel(Parcel in) {
            return new PropertyDetail(in);
        }
        
        @Override
        public PropertyDetail[] newArray(int size) {
            return new PropertyDetail[size];
        }
    };
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<Image> getImages() {
        return images;
    }
    
    public void setImages(List<Image> images) {
        this.images = images;
    }
    
    public String getContactTime() {
        return contactTime;
    }
    
    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public int getTextType() {
        return type == 1 ? R.string.txt_buy_uppercase : R.string.txt_rent_upppercase;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getTextPrice() {
        return TextUtils.formatIntToCurrency((float) getPrice() *
                                             AppConfig.RATE_VND);
    }
    
    public int getNumOfBedrooms() {
        return numOfBedrooms;
    }
    
    public void setNumOfBedrooms(int numOfBedrooms) {
        this.numOfBedrooms = numOfBedrooms;
    }
    
    public int getNumOfBathrooms() {
        return numOfBathrooms;
    }
    
    public void setNumOfBathrooms(int numOfBathrooms) {
        this.numOfBathrooms = numOfBathrooms;
    }
    
    public int getNumOfParkings() {
        return numOfParkings;
    }
    
    public void setNumOfParkings(int numOfParkings) {
        this.numOfParkings = numOfParkings;
    }
    
    public double getSize() {
        return size;
    }
    
    public void setSize(double size) {
        this.size = size;
    }
    
    public List<Category> getCategories() {
        return categories;
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public List<Tag> getTags() {
        return tags;
    }
    
    public void setTags(List<Tag> tags) {
        this.tags = tags;
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
    
    
    public Note getNote() {
        return note;
    }
    
    public void setNote(Note note) {
        this.note = note;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(id);
        dest.writeTypedList(images);
        dest.writeInt(type);
        dest.writeDouble(price);
        dest.writeString(address);
        dest.writeString(contactTime);
        dest.writeInt(numOfBedrooms);
        dest.writeInt(numOfBathrooms);
        dest.writeInt(numOfParkings);
        dest.writeDouble(size);
        dest.writeTypedList(tags);
        dest.writeString(description);
        dest.writeParcelable(location, flags);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeTypedList(phones);
        dest.writeParcelable(note, flags);
    }
}
