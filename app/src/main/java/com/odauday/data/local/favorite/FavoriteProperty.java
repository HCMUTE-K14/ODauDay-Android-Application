package com.odauday.data.local.favorite;

import com.odauday.model.Favorite;
import com.odauday.utils.ObjectUtils;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by infamouSs on 5/5/18.
 */
@Entity(nameInDb = "tbl_favorite")
public class FavoriteProperty {
    
    @Id(autoincrement = true)
    private long id;
    
    @Property(nameInDb = "property_id")
    private String propertyId;
    
    @Property(nameInDb = "user_id")
    private String userId;
    
    @Generated(hash = 1780142039)
    public FavoriteProperty() {
    }
    
    public FavoriteProperty(String propertyId, String userId) {
        this.propertyId = propertyId;
        this.userId = userId;
    }
    
    @Generated(hash = 800916143)
    public FavoriteProperty(long id, String propertyId, String userId) {
        this.id = id;
        this.propertyId = propertyId;
        this.userId = userId;
    }
    
    public FavoriteProperty(Favorite favorite) {
        this.propertyId = favorite.getPropertyId();
        this.userId = favorite.getUserId();
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
        FavoriteProperty that = (FavoriteProperty) o;
        return ObjectUtils.equals(propertyId, that.propertyId) &&
               ObjectUtils.equals(userId, that.userId);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(propertyId, userId);
    }
    
    @Override
    public String toString() {
        return "FavoriteProperty{" +
               "propertyId='" + propertyId + '\'' +
               ", userId='" + userId + '\'' +
               '}';
    }
}
