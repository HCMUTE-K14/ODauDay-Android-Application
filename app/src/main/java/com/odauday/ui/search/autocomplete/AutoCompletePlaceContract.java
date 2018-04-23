package com.odauday.ui.search.autocomplete;

import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.ui.base.BaseContract;
import java.util.List;

/**
 * Created by infamouSs on 4/23/18.
 */
public interface AutoCompletePlaceContract extends BaseContract {
    
    void onSuccessGetRecentSearchFromLocal(List<AutoCompletePlace> autoCompletePlaces);
    
    void onSuccessDeleteRecentSearchFromLocal(AutoCompletePlace autoCompletePlace);
    
    void hideClearButton();
    
    void showClearButton();
}
