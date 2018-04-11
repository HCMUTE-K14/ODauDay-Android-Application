package com.odauday.data.remote.search.model;

import com.odauday.ui.search.common.SearchCriteria;

/**
 * Created by infamouSs on 4/6/18.
 */

public class SearchRequest {
    
    private CoreSearchRequest core;
    
    private SearchCriteria criteria;
    
    public SearchRequest() {
    
    }
    
    public SearchRequest(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }
    
    public CoreSearchRequest getCore() {
        return core;
    }
    
    public void setCore(CoreSearchRequest core) {
        this.core = core;
    }
    
    public SearchCriteria getCriteria() {
        return criteria;
    }
    
    public void setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}
