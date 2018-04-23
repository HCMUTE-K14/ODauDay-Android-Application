package com.odauday.data.local.place;

import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.utils.ObjectUtils;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by infamouSs on 4/23/18.
 */

@Entity(nameInDb = "tbl_recent_search")
public class RecentSearchPlace {
    
    @Id
    private String id;
    
    @Property(nameInDb = "name")
    private String name;
    
    @Property(nameInDb = "lat")
    private double latitude;
    
    @Property(nameInDb = "lng")
    private double longitude;
    
    @Property(nameInDb = "user_id")
    private String userId;
    
    @Generated(hash = 331896107)
    public RecentSearchPlace() {
    }
    
    @Generated(hash = 74127323)
    public RecentSearchPlace(String id, String name, double latitude,
              double longitude, String userId) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
    }
    
    public RecentSearchPlace(AutoCompletePlace autoCompletePlace, String userId) {
        this.id = autoCompletePlace.getId();
        this.name = autoCompletePlace.getName();
        this.latitude = autoCompletePlace.getLocation().getLatitude();
        this.longitude = autoCompletePlace.getLocation().getLongitude();
        this.userId = userId;
    }
    
    public AutoCompletePlace convert() {
        AutoCompletePlace item = new AutoCompletePlace(this.id, this.name,
                  new GeoLocation(this.latitude, this.longitude));
        item.setSearched(true);
        
        return item;
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
        RecentSearchPlace that = (RecentSearchPlace) o;
        return ObjectUtils.equals(id, that.id) &&
               ObjectUtils.equals(userId, that.userId);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id, userId);
    }
    
    @Override
    public String toString() {
        return "RecentSearchPlace{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", latitude=" + latitude +
               ", longitude=" + longitude +
               ", userId='" + userId + '\'' +
               '}';
    }
}
