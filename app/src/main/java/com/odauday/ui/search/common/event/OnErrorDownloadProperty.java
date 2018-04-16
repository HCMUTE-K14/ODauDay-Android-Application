package com.odauday.ui.search.common.event;

import com.odauday.data.SearchPropertyState;
import com.odauday.exception.BaseException;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/15/18.
 */
public class OnErrorDownloadProperty extends BaseEvent {
    
    public static final int REQUEST_CODE = 91;
    
    private BaseException exception;
    private SearchPropertyState state;
    
    public OnErrorDownloadProperty(SearchPropertyState state, BaseException ex) {
        super(REQUEST_CODE);
        this.exception = ex;
        this.state = state;
    }
    
    public BaseException getException() {
        return exception;
    }
    
    public SearchPropertyState getState() {
        return state;
    }
}
