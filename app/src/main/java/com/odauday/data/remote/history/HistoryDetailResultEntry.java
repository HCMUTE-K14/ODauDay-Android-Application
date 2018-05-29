package com.odauday.data.remote.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.model.HistoryDetail;
import com.odauday.utils.ObjectUtils;
import java.util.List;

/**
 * Created by infamouSs on 5/29/18.
 */
public class HistoryDetailResultEntry {
    
    @SerializedName("count")
    @Expose
    private long count;
    
    @SerializedName("pages")
    @Expose
    private int pages;
    
    @SerializedName("histories")
    @Expose
    private List<HistoryDetail> histories;
    
    public HistoryDetailResultEntry() {
    
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
    
    public void setPages(int pages) {
        this.pages = pages;
    }
    
    public List<HistoryDetail> getHistories() {
        return histories;
    }
    
    public void setHistories(List<HistoryDetail> histories) {
        this.histories = histories;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryDetailResultEntry that = (HistoryDetailResultEntry) o;
        return count == that.count &&
               pages == that.pages &&
               ObjectUtils.equals(histories, that.histories);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(count, pages, histories);
    }
    
    @Override
    public String toString() {
        return "HistoryDetailResultEntry{" +
               "count=" + count +
               ", pages=" + pages +
               ", histories=" + histories +
               '}';
    }
}
