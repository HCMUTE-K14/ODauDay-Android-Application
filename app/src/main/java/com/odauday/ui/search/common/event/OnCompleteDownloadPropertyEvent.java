package com.odauday.ui.search.common.event;

import com.odauday.data.SearchPropertyState;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.data.remote.property.model.SearchResult;
import com.odauday.ui.base.BaseEvent;
import java.util.List;

/**
 * Created by infamouSs on 4/15/18.
 */
public class OnCompleteDownloadPropertyEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 90;
    
    private final SearchResult result;
    private final SearchPropertyState state;
    
    public OnCompleteDownloadPropertyEvent(SearchPropertyState state, SearchResult result) {
        super(REQUEST_CODE);
        this.result = result;
        this.state = state;
    }
    
    public SearchResult getSearchResult() {
        return result;
    }
    
    public List<PropertyResultEntry> getResult() {
        return result.getResult();
    }
}
