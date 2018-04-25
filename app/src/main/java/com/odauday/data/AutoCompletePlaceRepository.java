package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.local.place.RecentSearchPlace;
import com.odauday.data.local.place.RecentSearchPlaceService;
import com.odauday.data.remote.autocompleteplace.AutoCompletePlaceService;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.exception.AutCompletePlaceException;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceCollection;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/22/18.
 */
public class AutoCompletePlaceRepository implements Repository {
    
    private final AutoCompletePlaceService mAutoCompletePlaceService;
    
    private final RecentSearchPlaceService mRecentSearchPlaceService;
    
    private final SchedulersExecutor mSchedulersExecutor;
    
    private final PreferencesHelper mPreferencesHelper;
    
    @Inject
    public AutoCompletePlaceRepository(
              AutoCompletePlaceService autoCompletePlaceService,
              RecentSearchPlaceService recentSearchPlaceService,
              PreferencesHelper preferencesHelper,
              SchedulersExecutor schedulersExecutor) {
        this.mAutoCompletePlaceService = autoCompletePlaceService;
        this.mSchedulersExecutor = schedulersExecutor;
        this.mRecentSearchPlaceService = recentSearchPlaceService;
        this.mPreferencesHelper = preferencesHelper;
    }
    
    
    public Single<List<AutoCompletePlace>> searchRemote(String keyword) {
        return callApi(keyword);
    }
    
    public Single<List<AutoCompletePlace>> searchLocal(String keyword) {
        return callLocal(keyword);
    }
    
    public Single<List<AutoCompletePlace>> getRecentSearchLocal() {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        
        return mRecentSearchPlaceService
                  .findByUserId(userId)
                  .subscribeOn(mSchedulersExecutor.io())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Long> createRecentSearch(AutoCompletePlace autoCompletePlace) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        return mRecentSearchPlaceService
                  .create(new RecentSearchPlace(autoCompletePlace, userId))
                  .subscribeOn(mSchedulersExecutor.io())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Long> deleteRecentSearch(AutoCompletePlace autoCompletePlace) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        return mRecentSearchPlaceService
                  .delete(new RecentSearchPlace(autoCompletePlace, userId))
                  .subscribeOn(mSchedulersExecutor.io())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<AutoCompletePlaceCollection> search(String keyword) {
        return Single.zip(
                  searchLocal(keyword)
                            .onErrorResumeNext(
                                      throwable -> Single.just(new ArrayList<>())),
                  searchRemote(keyword),
                  AutoCompletePlaceCollection::new
        ).subscribeOn(mSchedulersExecutor.io())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    private Single<List<AutoCompletePlace>> callLocal(String keyword) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        return mRecentSearchPlaceService.search(keyword, userId)
                  .subscribeOn(mSchedulersExecutor.io());
    }
    
    private Single<List<AutoCompletePlace>> callApi(String keyword) {
        return mAutoCompletePlaceService.search(keyword)
                  .subscribeOn(mSchedulersExecutor.io())
                  .map(response -> {
                      try {
                          if (response.isSuccess()) {
                              return response.getData();
                          } else {
                              throw new AutCompletePlaceException(response.getErrors());
                          }
                      } catch (Exception ex) {
                          if (ex instanceof AutCompletePlaceException) {
                              throw ex;
                          }
                          throw new AutCompletePlaceException(ex.getMessage());
                      }
                  });
    }
    
    public SchedulersExecutor getSchedulersExecutor() {
        return mSchedulersExecutor;
    }
}
