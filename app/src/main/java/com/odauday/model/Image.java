package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 3/30/2018.
 */

public class Image {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("url")
    @Expose
    private String url;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return "Image{" +
               "id='" + id + '\'' +
               ", url='" + url + '\'' +
               '}';
    }
}
