package com.odauday.data.local.history;

import com.odauday.utils.ObjectUtils;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by infamouSs on 4/14/18.
 */
@Entity(nameInDb = "history")
public class HistoryProperty {
    
    @Id(autoincrement = true)
    private long id;
    
    @Property(nameInDb = "property_id")
    @Unique
    private String propertyId;
    
    @Property(nameInDb = "user_id")
    private String userId;
    
    @Generated(hash = 1747135296)
    public HistoryProperty() {
    }

    @Generated(hash = 251593081)
    public HistoryProperty(long id, String propertyId, String userId) {
        this.id = id;
        this.propertyId = propertyId;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryProperty that = (HistoryProperty) o;
        return id == that.id &&
               ObjectUtils.equals(propertyId, that.propertyId) &&
               ObjectUtils.equals(userId, that.userId);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id, propertyId, userId);
    }
    
    @Override
    public String toString() {
        return "HistoryProperty{" +
               "id=" + id +
               ", propertyId='" + propertyId + '\'' +
               ", userId='" + userId + '\'' +
               '}';
    }
}
