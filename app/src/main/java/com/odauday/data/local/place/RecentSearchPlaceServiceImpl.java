package com.odauday.data.local.place;

import com.odauday.data.local.place.RecentSearchPlaceDao.Properties;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/23/18.
 */
public class RecentSearchPlaceServiceImpl implements RecentSearchPlaceService {
    
    private final RecentSearchPlaceDao mPlaceLocalDao;
    
    public RecentSearchPlaceServiceImpl(RecentSearchPlaceDao dao) {
        this.mPlaceLocalDao = dao;
    }
    
    @Override
    public Single<Long> create(RecentSearchPlace recentSearchPlace) {
        return Single.fromCallable(() -> mPlaceLocalDao.insert(recentSearchPlace));
    }
    
    @Override
    public Single<Long> delete(RecentSearchPlace recentSearchPlace) {
        return Single.fromCallable(() -> {
            mPlaceLocalDao.delete(recentSearchPlace);
            return 1L;
        });
    }
    
    @Override
    public Single<List<AutoCompletePlace>> findByUserId(String userId) {
        return Single.fromCallable(() -> {
            
            List<RecentSearchPlace> recentSearches = mPlaceLocalDao
                      .queryBuilder()
                      .where(Properties.UserId.eq(userId))
                      .list();
            return convert(recentSearches);
        });
    }
    
    @Override
    public Single<List<AutoCompletePlace>> search(String keyword, String userId) {
        return Single.fromCallable(() -> {
            List<RecentSearchPlace> recentSearches = mPlaceLocalDao
                      .queryBuilder()
                      .where(Properties.UserId.eq(userId))
                      .where(Properties.Name.like(keyword))
                      .list();
            
            return convert(recentSearches);
        });
    }
    
    @Override
    public List<AutoCompletePlace> convert(List<RecentSearchPlace> placeLocalList) {
        List<AutoCompletePlace> completePlaceList = new ArrayList<>();
        for (RecentSearchPlace placeLocal : placeLocalList) {
            completePlaceList.add(placeLocal.convert());
        }
        return completePlaceList;
    }
}
