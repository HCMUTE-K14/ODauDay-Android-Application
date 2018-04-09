package com.odauday.data.remote.search;

import com.odauday.ui.search.common.SearchCriteria;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/6/18.
 */

public class SearchService {
    
    @Inject
    public SearchService() {
    
    }
    
    
    public SearchCriteria getCurrentSearchCriteria() {
        return new SearchCriteria();
    }
}
