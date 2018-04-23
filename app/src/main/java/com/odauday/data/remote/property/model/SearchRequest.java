package com.odauday.data.remote.property.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseRequest;
import com.odauday.ui.search.common.SearchCriteria;

/**
 * Created by infamouSs on 4/6/18.
 */

public class SearchRequest implements BaseRequest {
    
    @SerializedName("core")
    @Expose
    private CoreSearchRequest core;
    
    @SerializedName("criteria")
    @Expose
    private SearchCriteria criteriaNormalize;
    
    private SearchCriteria criteria;
    
    @SerializedName("zoom")
    @Expose
    private float zoom;
    
    public SearchRequest() {

    }
    
    public SearchRequest(CoreSearchRequest core, SearchCriteria criteria, float zoom) {
        this.core = core;
        this.criteria = criteria;
        this.criteriaNormalize = criteria.normalize();
        this.zoom = zoom;
    }
    
    public SearchRequest(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }
    
    public float getZoom() {
        return zoom;
    }
    
    public void setZoom(float zoom) {
        this.zoom = zoom;
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
        this.criteriaNormalize = criteria.normalize();
        this.criteria = criteria;
    }
}
