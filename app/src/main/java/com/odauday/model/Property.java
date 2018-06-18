package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

/**
 * Created by kunsubin on 4/4/2018.
 */

public class Property {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("address")
    @Expose
    private String address;
    
    @SerializedName("code")
    @Expose
    private String code;
    
    @SerializedName("latitude")
    @Expose
    private double latitude;
    
    @SerializedName("longitude")
    @Expose
    private double longitude;
    
    @SerializedName("postcode")
    @Expose
    private int postcode;
    
    @SerializedName("status")
    @Expose
    private String status;
    
    @SerializedName("price")
    @Expose
    private Double price;
    
    @SerializedName("description")
    @Expose
    private String description;
    
    @SerializedName("num_of_bedroom")
    @Expose
    private int num_of_bedroom;
    
    @SerializedName("num_of_bathroom")
    @Expose
    private int num_of_bathroom;
    
    @SerializedName("num_of_parking")
    @Expose
    private int num_of_parking;
    
    @SerializedName("land_size")
    @Expose
    private double land_size;
    
    @SerializedName("user_id_created")
    @Expose
    private String user_id_created;
    
    @SerializedName("type_id")
    @Expose
    private String type_id;
    
    @SerializedName("time_contact")
    @Expose
    private String time_contact;
    
    @SerializedName("date_created")
    @Expose
    private Date date_created;
    
    @SerializedName("date_end")
    @Expose
    private Date date_end;
    
    @SerializedName("emails")
    @Expose
    private List<Email> mEmails;
    
    @SerializedName("phones")
    @Expose
    private List<Phone> mPhones;
    
    @SerializedName("images")
    @Expose
    private List<Image> mImages;
    
    @SerializedName("tags")
    @Expose
    private List<Tag> mTags;
    
    @SerializedName("categories")
    @Expose
    private List<Category> mCategories;
    
    @SerializedName("Favorite")
    @Expose
    private Favorite mFavorite;
    
    
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
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getNum_of_bedroom() {
        return num_of_bedroom;
    }
    
    public void setNum_of_bedroom(int num_of_bedroom) {
        this.num_of_bedroom = num_of_bedroom;
    }
    
    public int getNum_of_bathroom() {
        return num_of_bathroom;
    }
    
    public void setNum_of_bathroom(int num_of_bathroom) {
        this.num_of_bathroom = num_of_bathroom;
    }
    
    public int getNum_of_parking() {
        return num_of_parking;
    }
    
    public void setNum_of_parking(int num_of_parking) {
        this.num_of_parking = num_of_parking;
    }
    
    public double getLand_size() {
        return land_size;
    }
    
    public void setLand_size(double land_size) {
        this.land_size = land_size;
    }
    
    public String getType_id() {
        return type_id;
    }
    
    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
    
    public Date getDate_created() {
        return date_created;
    }
    
    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }
    
    public Date getDate_end() {
        return date_end;
    }
    
    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }
    
    public String getTime_contact() {
        return time_contact;
    }
    
    public void setTime_contact(String time_contact) {
        this.time_contact = time_contact;
    }
    
    public List<Email> getEmails() {
        return mEmails;
    }
    
    public void setEmails(List<Email> emails) {
        mEmails = emails;
    }
    
    public List<Phone> getPhones() {
        return mPhones;
    }
    
    public void setPhones(List<Phone> phones) {
        mPhones = phones;
    }
    
    public List<Image> getImages() {
        return mImages;
    }
    
    public void setImages(List<Image> images) {
        mImages = images;
    }
    
    public List<Tag> getTags() {
        return mTags;
    }
    
    public void setTags(List<Tag> tags) {
        mTags = tags;
    }
    
    public List<Category> getCategories() {
        return mCategories;
    }
    
    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }
    
    public Favorite getFavorite() {
        return mFavorite;
    }
    
    public void setFavorite(Favorite favorite) {
        mFavorite = favorite;
    }
    
    public String getUser_id_created() {
        return user_id_created;
    }
    
    public void setUser_id_created(String user_id_created) {
        this.user_id_created = user_id_created;
    }
    
    @Override
    public String toString() {
        return "Property{" +
               "id='" + id + '\'' +
               ", name='" + address + '\'' +
               ", code='" + code + '\'' +
               ", latitude=" + latitude +
               ", longitude=" + longitude +
               ", postcode=" + postcode +
               ", status='" + status + '\'' +
               ", price=" + price +
               ", description='" + description + '\'' +
               ", num_of_bedrom=" + num_of_bedroom +
               ", num_of_bathrom=" + num_of_bathroom +
               ", num_of_packing=" + num_of_parking +
               ", land_size=" + land_size +
               ", type_id='" + type_id + '\'' +
               ", date_created=" + date_created +
               ", date_end=" + date_end +
               ", mEmails=" + mEmails +
               ", mPhones=" + mPhones +
               ", mImages=" + mImages +
               ", mTags=" + mTags +
               ", mCategories=" + mCategories +
               '}';
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        
        Property property = (Property) object;
        
        if (Double.compare(property.latitude, latitude) != 0) {
            return false;
        }
        if (Double.compare(property.longitude, longitude) != 0) {
            return false;
        }
        return id != null ? id.equals(property.id) : property.id == null;
    }
    
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    
    public class Favorite {
        
        @SerializedName("date_created")
        @Expose
        private Date date_created;
        
        public Date getDate_created() {
            return date_created;
        }
        
        public void setDate_created(Date date_created) {
            this.date_created = date_created;
        }
        
    }
}
