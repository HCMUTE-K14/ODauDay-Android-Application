package com.odauday.data.local.notification;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by kunsubin on 6/4/2018.
 */
@Entity(nameInDb = "tbl_notification")
public class NotificationEntity {
    
    @Id
    @Property(nameInDb = "id")
    private String mId;
    
    @Property(nameInDb = "title")
    private String mTitle;
    
    @Property(nameInDb = "body")
    private String mBody;
    
    @Property(nameInDb = "user_id")
    private String mUserID;
    
    @Property(nameInDb = "property_id")
    private String mPropertyID;
    
    @Property(nameInDb = "image")
    private String mImage;
    
    @Property(nameInDb = "type")
    private String mType;
    
    @Property(nameInDb = "date")
    private long mDate;
    
    @Property(nameInDb = "status")
    private String mStatus;

    @Generated(hash = 812880405)
    public NotificationEntity(String mId, String mTitle, String mBody,
            String mUserID, String mPropertyID, String mImage, String mType,
            long mDate, String mStatus) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mBody = mBody;
        this.mUserID = mUserID;
        this.mPropertyID = mPropertyID;
        this.mImage = mImage;
        this.mType = mType;
        this.mDate = mDate;
        this.mStatus = mStatus;
    }

    @Generated(hash = 1877229834)
    public NotificationEntity() {
    }

    public String getId() {
        return mId;
    }
    
    public void setId(String id) {
        mId = id;
    }
    
    public String getTitle() {
        return mTitle;
    }
    
    public void setTitle(String title) {
        mTitle = title;
    }
    
    public String getBody() {
        return mBody;
    }
    
    public void setBody(String body) {
        mBody = body;
    }
    
    public String getUserID() {
        return mUserID;
    }
    
    public void setUserID(String userID) {
        mUserID = userID;
    }
    
    public String getPropertyID() {
        return mPropertyID;
    }
    
    public void setPropertyID(String propertyID) {
        mPropertyID = propertyID;
    }
    
    public String getImage() {
        return mImage;
    }
    
    public void setImage(String image) {
        mImage = image;
    }
    
    public String getType() {
        return mType;
    }
    
    public void setType(String type) {
        mType = type;
    }
    
    public long getDate() {
        return mDate;
    }
    
    public void setDate(long date) {
        mDate = date;
    }

    public String getMId() {
        return this.mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    public String getMTitle() {
        return this.mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMBody() {
        return this.mBody;
    }

    public void setMBody(String mBody) {
        this.mBody = mBody;
    }

    public String getMUserID() {
        return this.mUserID;
    }

    public void setMUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getMPropertyID() {
        return this.mPropertyID;
    }

    public void setMPropertyID(String mPropertyID) {
        this.mPropertyID = mPropertyID;
    }

    public String getMImage() {
        return this.mImage;
    }

    public void setMImage(String mImage) {
        this.mImage = mImage;
    }

    public String getMType() {
        return this.mType;
    }

    public void setMType(String mType) {
        this.mType = mType;
    }

    public long getMDate() {
        return this.mDate;
    }

    public void setMDate(long mDate) {
        this.mDate = mDate;
    }
    
    public String getStatus() {
        return mStatus;
    }
    
    public void setStatus(String status) {
        mStatus = status;
    }

    public String getMStatus() {
        return this.mStatus;
    }

    public void setMStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
