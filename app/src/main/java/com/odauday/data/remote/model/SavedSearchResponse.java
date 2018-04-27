package com.odauday.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.model.Search;
import java.util.List;

/**
 * Created by kunsubin on 4/9/2018.
 */

public class SavedSearchResponse {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("searches")
    @Expose
    private List<Search> mSearches;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<Search> getSearches() {
        return mSearches;
    }
    
    public void setSearches(List<Search> searches) {
        mSearches = searches;
    }
    
    @Override
    public String toString() {
        return "SavedSearchResponse{" +
               "id='" + id + '\'' +
               ", mSearches=" + mSearches +
               '}';
    }
}
