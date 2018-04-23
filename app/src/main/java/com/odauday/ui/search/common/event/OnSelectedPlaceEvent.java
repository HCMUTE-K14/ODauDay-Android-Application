package com.odauday.ui.search.common.event;

import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/23/18.
 */
public class OnSelectedPlaceEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 105;
    
    private final AutoCompletePlace data;
    
    public OnSelectedPlaceEvent(AutoCompletePlace autoCompletePlace) {
        super(REQUEST_CODE);
        this.data = autoCompletePlace;
    }
    
    public AutoCompletePlace getData() {
        return data;
    }
    
}
