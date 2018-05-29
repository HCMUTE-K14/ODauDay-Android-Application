package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.local.history.HistoryProperty;
import com.odauday.data.local.history.HistoryService;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 5/5/18.
 */
public class HistoryLocalRepository implements Repository {
    
    private final HistoryService mHistoryService;
    private final PreferencesHelper mPreferencesHelper;
    private final SchedulersExecutor mSchedulersExecutor;
    
    
    public HistoryLocalRepository(HistoryService historyService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        mHistoryService = historyService;
        mSchedulersExecutor = schedulersExecutor;
        mPreferencesHelper = preferencesHelper;
    }
    
    
    public Single<Long> create(HistoryProperty historyProperty) {
        return mHistoryService.create(historyProperty)
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Long> create(String propertyId) {
        return create(createHistoryProeprtyFromPropertyId(propertyId));
    }
    
    public Single<Long> delete(HistoryProperty historyProperty) {
        return mHistoryService.delete(historyProperty)
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Long> save(List<HistoryProperty> historyProperties) {
        return mHistoryService.save(historyProperties)
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Boolean> isSavedProperty(HistoryProperty historyProperty) {
        return mHistoryService.isSavedLocalProperty(historyProperty)
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Boolean> isSavedProperty(String propertyId) {
        
        return isSavedProperty(createHistoryProeprtyFromPropertyId(propertyId));
    }
    
    private HistoryProperty createHistoryProeprtyFromPropertyId(String propertyId) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        
        HistoryProperty historyProperty = new HistoryProperty();
        
        historyProperty.setPropertyId(propertyId);
        historyProperty.setUserId(userId);
        
        return historyProperty;
    }
}
