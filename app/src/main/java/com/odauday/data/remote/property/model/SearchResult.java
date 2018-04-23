package com.odauday.data.remote.property.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseResponse;
import com.odauday.utils.ObjectUtils;
import java.util.List;

/**
 * Created by infamouSs on 4/6/18.
 */

public class SearchResult implements BaseResponse {
    
    @SerializedName("count")
    @Expose
    private long count;
    
    @SerializedName("pages")
    @Expose
    private int pages;
    
    @SerializedName("properties")
    @Expose
    private List<PropertyResultEntry> properties;
    
    public SearchResult() {

    }
    
    public SearchResult(long count, int page,
              List<PropertyResultEntry> result) {
        this.count = count;
        this.pages = page;
        this.properties = result;
    }
    
    public long getCount() {
        return count;
    }
    
    public void setCount(long count) {
        this.count = count;
    }
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int page) {
        this.pages = page;
    }
    
    public List<PropertyResultEntry> getResult() {
        return properties;
    }
    
    public void setResult(
              List<PropertyResultEntry> result) {
        this.properties = result;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchResult that = (SearchResult) o;
        return count == that.count &&
               pages == that.pages &&
               ObjectUtils.equals(properties, that.properties);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(count, pages, properties);
    }
    
    @Override
    public String toString() {
        return "SearchResult{" +
               "count=" + count +
               ", pages=" + pages +
               ", result=" + properties +
               '}';
    }
}
