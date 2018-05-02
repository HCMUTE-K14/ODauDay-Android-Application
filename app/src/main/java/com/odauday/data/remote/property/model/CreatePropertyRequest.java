package com.odauday.data.remote.property.model;

import com.odauday.model.MyProperty;
import java.util.List;
import okhttp3.MultipartBody;

/**
 * Created by infamouSs on 5/1/18.
 */
public class CreatePropertyRequest {
    
    private MyProperty property;
    private List<MultipartBody.Part> images;
    
    public CreatePropertyRequest() {
    
    }
    
    public CreatePropertyRequest(MyProperty property,
        List<MultipartBody.Part> images) {
        this.property = property;
        this.images = images;
    }
    
    public MyProperty getProperty() {
        return property;
    }
    
    public void setProperty(MyProperty property) {
        this.property = property;
    }
    
    public List<MultipartBody.Part> getImages() {
        return images;
    }
    
    public void setImages(List<MultipartBody.Part> images) {
        this.images = images;
    }
}
