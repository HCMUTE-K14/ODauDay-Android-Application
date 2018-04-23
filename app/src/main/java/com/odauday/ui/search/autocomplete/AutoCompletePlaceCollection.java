package com.odauday.ui.search.autocomplete;

import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import java.util.List;

/**
 * Created by infamouSs on 4/23/18.
 */
public class AutoCompletePlaceCollection {
    
    private List<AutoCompletePlace> mRecentSearch;
    private List<AutoCompletePlace> mSuggestionSearch;
    
    public AutoCompletePlaceCollection(
              List<AutoCompletePlace> recentSearch,
              List<AutoCompletePlace> suggestionSearch) {
        mRecentSearch = recentSearch;
        mSuggestionSearch = suggestionSearch;
    }
    
    public List<AutoCompletePlace> getRecentSearch() {
        return mRecentSearch;
    }
    
    public void setRecentSearch(
              List<AutoCompletePlace> recentSearch) {
        mRecentSearch = recentSearch;
    }
    
    public List<AutoCompletePlace> getSuggestionSearch() {
        return mSuggestionSearch;
    }
    
    public void setSuggestionSearch(
              List<AutoCompletePlace> suggestionSearch) {
        mSuggestionSearch = suggestionSearch;
    }
}
