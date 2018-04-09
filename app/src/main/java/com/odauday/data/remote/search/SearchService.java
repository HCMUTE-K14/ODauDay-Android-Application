package com.odauday.data.remote.search;

import com.odauday.data.remote.search.model.SearchRequest;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/6/18.
 */

public class SearchService {
    
    private SearchRequest mSearchRequest;
    
    @Inject
    public SearchService() {
    
    }
    
    
    public SearchRequest getCurrentSearchRequest() {
        return mSearchRequest;
    }
    
    public void setCurrentSearchRequest(SearchRequest searchRequest) {
        this.mSearchRequest = searchRequest;
    }
}
