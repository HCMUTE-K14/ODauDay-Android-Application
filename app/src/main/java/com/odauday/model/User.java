package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 2/27/18.
 */

public class User {
    
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_DISABLED = "disabled";
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("email")
    @Expose
    private String email;
    
    @SerializedName("display_name")
    @Expose
    private String displayName;
    
    @SerializedName("phone")
    @Expose
    private String phone;
    
    @SerializedName("avatar")
    @Expose
    private String avatar;
    
    @SerializedName("role")
    @Expose
    private String role;
    
    @SerializedName("status")
    @Expose
    private String status;
    
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    
    @SerializedName("amount")
    @Expose
    private long amount;
    
    
    public User() {

    }
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getFacebookId() {
        return facebookId;
    }
    
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
    
    public long getAmount() {
        return amount;
    }
    
    public void setAmount(long amount) {
        this.amount = amount;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        User user = (User) o;
        
        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
        return email != null ? email.equals(user.email) : user.email == null;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", email='" + email + '\'' +
               ", displayName='" + displayName + '\'' +
               ", phone='" + phone + '\'' +
               ", avatar='" + avatar + '\'' +
               ", role='" + role + '\'' +
               ", status='" + status + '\'' +
               ", facebookId='" + facebookId + '\'' +
               ", amount=" + amount +
               '}';
    }
}
