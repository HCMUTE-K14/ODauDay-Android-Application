package com.odauday.ui.search.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by infamouSs on 4/9/18.
 */

public class SearchCriteriaDisplay implements Parcelable {
    
    public static final Creator<SearchCriteriaDisplay> CREATOR = new Creator<SearchCriteriaDisplay>() {
        @Override
        public SearchCriteriaDisplay createFromParcel(Parcel in) {
            return new SearchCriteriaDisplay(in);
        }

        @Override
        public SearchCriteriaDisplay[] newArray(int size) {
            return new SearchCriteriaDisplay[size];
        }
    };
    private TextAndMoreTextValue displayPrice;
    private String displaySize;
    private TextAndMoreTextValue displayPropertyType;
    private String displayBedroom;
    private String displayBathroom;
    private String displayParking;
    private TextAndMoreTextValue displayTag;
    private String displayLocation;
    
    public SearchCriteriaDisplay() {

    }
    
    protected SearchCriteriaDisplay(Parcel in) {
        displayPrice = in.readParcelable(TextAndMoreTextValue.class.getClassLoader());
        displaySize = in.readString();
        displayPropertyType = in.readParcelable(TextAndMoreTextValue.class.getClassLoader());
        displayBedroom = in.readString();
        displayBathroom = in.readString();
        displayParking = in.readString();
        displayTag = in.readParcelable(TextAndMoreTextValue.class.getClassLoader());
        displayLocation = in.readString();
    }
    
    public TextAndMoreTextValue getDisplayPrice() {
        return displayPrice;
    }
    
    public void setDisplayPrice(TextAndMoreTextValue displayPrice) {
        this.displayPrice = displayPrice;
    }
    
    public String getDisplaySize() {
        return displaySize;
    }
    
    public void setDisplaySize(String displaySize) {
        this.displaySize = displaySize;
    }
    
    public TextAndMoreTextValue getDisplayPropertyType() {
        return displayPropertyType;
    }
    
    public void setDisplayPropertyType(
              TextAndMoreTextValue displayPropertyType) {
        this.displayPropertyType = displayPropertyType;
    }
    
    public String getDisplayBedroom() {
        return displayBedroom;
    }
    
    public void setDisplayBedroom(String displayBedroom) {
        this.displayBedroom = displayBedroom;
    }
    
    public String getDisplayBathroom() {
        return displayBathroom;
    }
    
    public void setDisplayBathroom(String displayBathroom) {
        this.displayBathroom = displayBathroom;
    }
    
    public String getDisplayParking() {
        return displayParking;
    }
    
    public void setDisplayParking(String displayParking) {
        this.displayParking = displayParking;
    }
    
    public TextAndMoreTextValue getDisplayTag() {
        return displayTag;
    }
    
    public void setDisplayTag(TextAndMoreTextValue displayTag) {
        this.displayTag = displayTag;
    }
    
    public String getDisplayLocation() {
        return displayLocation;
    }
    
    public void setDisplayLocation(String displayLocation) {
        this.displayLocation = displayLocation;
    }
    
    @Override
    public String toString() {
        return "SearchCriteriaDisplay{" +
               "displayPrice=" + displayPrice +
               ", displaySize='" + displaySize + '\'' +
               ", displayPropertyType=" + displayPropertyType +
               ", displayBedroom='" + displayBedroom + '\'' +
               ", displayBathroom='" + displayBathroom + '\'' +
               ", displayParking='" + displayParking + '\'' +
               ", displayTag=" + displayTag +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeParcelable(displayPrice, flags);
        dest.writeString(displaySize);
        dest.writeParcelable(displayPropertyType, flags);
        dest.writeString(displayBedroom);
        dest.writeString(displayBathroom);
        dest.writeString(displayParking);
        dest.writeParcelable(displayTag, flags);
        dest.writeString(displayLocation);
    }
}
