package com.odauday.ui.search.common.event;

import com.odauday.data.SearchPropertyState;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.ui.base.BaseEvent;
import java.util.List;

/**
 * Created by infamouSs on 4/15/18.
 */
public class OnCompleteDownloadProperty extends BaseEvent {
    
    public static final int REQUEST_CODE = 90;
    
    private List<PropertyResultEntry> result;
    private SearchPropertyState state;
    
    public OnCompleteDownloadProperty(SearchPropertyState state, List<PropertyResultEntry> result) {
        super(REQUEST_CODE);
        this.result = result;
        this.state = state;
    }
    
    public List<PropertyResultEntry> getResult() {
        return result;
    }
}
