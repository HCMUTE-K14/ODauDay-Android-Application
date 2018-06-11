package com.odauday.ui.savedsearch;

import com.odauday.model.Search;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 6/4/18.
 */
public class OnClickSavedSearch extends BaseEvent {
    
    public static final int REQUEST_CODE = 125;
    
    
    private Search mSearch;
    public OnClickSavedSearch(Search search){
        super(REQUEST_CODE);
        
        mSearch = search;
    }
    
    public Search getSearch() {
        return mSearch;
    }
}
