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
    
    @SerializedName("page")
    @Expose
    private int page;
    
    @SerializedName("result")
    @Expose
    private List<PropertyResultEntry> result;
    
    public SearchResult() {

    }
    
    public SearchResult(long count, int page,
              List<PropertyResultEntry> result) {
        this.count = count;
        this.page = page;
        this.result = result;
    }
    
    public long getCount() {
        return count;
    }
    
    public void setCount(long count) {
        this.count = count;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public List<PropertyResultEntry> getResult() {
        return result;
    }
    
    public void setResult(
              List<PropertyResultEntry> result) {
        this.result = result;
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
               page == that.page &&
               ObjectUtils.equals(result, that.result);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(count, page, result);
    }
    
    @Override
    public String toString() {
        return "SearchResult{" +
               "count=" + count +
               ", page=" + page +
               ", result=" + result +
               '}';
    }
}
