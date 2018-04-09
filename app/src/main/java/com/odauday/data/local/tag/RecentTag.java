package com.odauday.data.local.tag;

import com.odauday.model.Tag;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by infamouSs on 4/9/18.
 */
@Entity(nameInDb = "recent_tag")
public class RecentTag {
    
    
    @Id
    @Property(nameInDb = "id")
    private String id;
    
    @Property(nameInDb = "name")
    private String name;
    
    @Property(nameInDb = "user_id")
    private String userId;
    
    @Generated(hash = 743294481)
    public RecentTag(String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
    
    @Generated(hash = 713780027)
    public RecentTag() {
    }
    
    public RecentTag(Tag tag, String userId) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.userId = userId;
    }
    
    public static Tag convertToTag(RecentTag recentTag) {
        return new Tag(recentTag.getId(), recentTag.getName());
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
        
        RecentTag recentTag = (RecentTag) o;
        
        if (id != null ? !id.equals(recentTag.id) : recentTag.id != null) {
            return false;
        }
        if (name != null ? !name.equals(recentTag.name) : recentTag.name != null) {
            return false;
        }
        return userId != null ? userId.equals(recentTag.userId) : recentTag.userId == null;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "RecentTag{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", userId='" + userId + '\'' +
               '}';
    }
}
