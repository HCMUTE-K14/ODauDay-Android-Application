package com.odauday.ui.search.common.event;

import com.odauday.model.Search;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 6/4/18.
 */
public class OnNeedLoadWithSavedSearch extends BaseEvent {
    
    public static final int REQUEST_CODE = 126;
    
    
    private Search mSearch;
    
    public OnNeedLoadWithSavedSearch(Search search) {
        super(REQUEST_CODE);
        this.mSearch = search;
    }
    
    public Search getSearch() {
        return mSearch;
    }
}
