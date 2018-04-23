package com.odauday.data.local.place;

import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/23/18.
 */
public interface RecentSearchPlaceService {
    
    Single<Long> create(RecentSearchPlace recentSearchPlace);
    
    Single<Long> delete(RecentSearchPlace recentSearchPlace);
    
    Single<List<AutoCompletePlace>> findByUserId(String userId);
    
    Single<List<AutoCompletePlace>> search(String keyword, String userId);
    
    List<AutoCompletePlace> convert(List<RecentSearchPlace> placeLocalList);
    
}
