package com.odauday.ui.search.mapview;

import com.odauday.R;

/**
 * Created by infamouSs on 4/12/18.
 */

public enum MarkerType {
    
    FAVORITE(R.drawable.ic_map_pin_favorite),
    VISITED(R.drawable.ic_map_pin_visited),
    DEFAULT(R.drawable.ic_map_pin);
    
    
    private int resourceId;

    MarkerType(int resourceId) {
        this.resourceId = resourceId;
    }
    
    public int getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
