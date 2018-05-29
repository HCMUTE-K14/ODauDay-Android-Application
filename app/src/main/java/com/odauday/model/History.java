package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;
import java.util.Date;
import java.util.Objects;

/**
 * Created by infamouSs on 5/28/18.
 */
public class History {
    
    
    @SerializedName("user_id")
    @Expose
    private String userId;
    
    @SerializedName("property_id")
    @Expose
    private String propertyId;
    
    @SerializedName("date_created")
    @Expose
    private Date dateCreated;
    
    
    public History() {
    
    }
    
    public History(String userId, String propertyId) {
        this.userId = userId;
        this.propertyId = propertyId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    public Date getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        History history = (History) o;
        return ObjectUtils.equals(userId, history.userId) &&
               ObjectUtils.equals(propertyId, history.propertyId);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(userId, propertyId);
    }
}
