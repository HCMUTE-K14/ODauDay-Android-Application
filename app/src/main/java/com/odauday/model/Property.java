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
    
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("code")
    @Expose
    private String code;
    
    @SerializedName("latitude")
    @Expose
    private Float latitude;
    
    @SerializedName("longitude")
    @Expose
    private Float longitude;
    
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
    
    @SerializedName("num_of_bedrom")
    @Expose
    private int num_of_bedrom;
    
    @SerializedName("num_of_bathrom")
    @Expose
    private int num_of_bathrom;
    
    @SerializedName("num_of_packing")
    @Expose
    private int num_of_packing;
    
    @SerializedName("land_size")
    @Expose
    private double land_size;
    
    @SerializedName("type_id")
    @Expose
    private String type_id;
    
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
    
    @SerializedName("categorys")
    @Expose
    private List<Category> mCategories;
    
    
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
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public Float getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    
    public Float getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Float longitude) {
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
    
    public int getNum_of_bedrom() {
        return num_of_bedrom;
    }
    
    public void setNum_of_bedrom(int num_of_bedrom) {
        this.num_of_bedrom = num_of_bedrom;
    }
    
    public int getNum_of_bathrom() {
        return num_of_bathrom;
    }
    
    public void setNum_of_bathrom(int num_of_bathrom) {
        this.num_of_bathrom = num_of_bathrom;
    }
    
    public int getNum_of_packing() {
        return num_of_packing;
    }
    
    public void setNum_of_packing(int num_of_packing) {
        this.num_of_packing = num_of_packing;
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
    
    @Override
    public String toString() {
        return "Property{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", code='" + code + '\'' +
               ", latitude=" + latitude +
               ", longitude=" + longitude +
               ", postcode=" + postcode +
               ", status='" + status + '\'' +
               ", price=" + price +
               ", description='" + description + '\'' +
               ", num_of_bedrom=" + num_of_bedrom +
               ", num_of_bathrom=" + num_of_bathrom +
               ", num_of_packing=" + num_of_packing +
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
}
