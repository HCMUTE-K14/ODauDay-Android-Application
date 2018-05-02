package com.odauday.ui.search.common.event;

import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.ui.base.BaseEvent;

/**
 * Created by infamouSs on 4/23/18.
 */
public class OnSelectedPlaceEvent extends BaseEvent {
    
    public static final int REQUEST_CODE = 105;
    
    private final AutoCompletePlace data;
    
    private final boolean isNeedMoveMapInMapFragment;
    
    public OnSelectedPlaceEvent(AutoCompletePlace autoCompletePlace,
        boolean isNeedMoveMapInMapFragment) {
        super(REQUEST_CODE);
        this.data = autoCompletePlace;
        this.isNeedMoveMapInMapFragment = isNeedMoveMapInMapFragment;
    }
    
    public AutoCompletePlace getData() {
        return data;
    }
    
    public boolean isNeedMoveMapInMapFragment() {
        return isNeedMoveMapInMapFragment;
    }
}
