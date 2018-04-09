package com.odauday.data.remote.search.model;

import com.odauday.ui.search.common.SearchCriteria;

/**
 * Created by infamouSs on 4/6/18.
 */

public class SearchRequest {
    
    private Point location;
    private double radius;
    private SearchCriteria criteria;
    
    public SearchRequest() {
    
    }
    
    public SearchRequest(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }
    
    public Point getLocation() {
        return location;
    }
    
    public void setLocation(Point location) {
        this.location = location;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    public SearchCriteria getCriteria() {
        return criteria;
    }
    
    public void setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}
