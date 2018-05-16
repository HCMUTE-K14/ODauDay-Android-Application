package com.odauday.ui.search;

import com.odauday.data.remote.model.MessageResponse;

/**
 * Created by infamouSs on 5/16/18.
 */
public interface SearchTabContract {
    
    void onSuccessSavedSearch(MessageResponse response);
    
    void onFailSavedSearch(Throwable throwable);
}
