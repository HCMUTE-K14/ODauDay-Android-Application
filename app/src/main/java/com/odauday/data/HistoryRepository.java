package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.HistoryService;
import com.odauday.data.remote.history.HistoryDetailResultEntry;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.exception.HistoryException;
import com.odauday.model.History;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/27/2018.
 */

public class HistoryRepository {
    
    private final HistoryService mHistoryService;
    private final SchedulersExecutor mSchedulersExecutor;
    private final PreferencesHelper mPreferencesHelper;
    
    @Inject
    public HistoryRepository(HistoryService historyService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        mHistoryService = historyService;
        mSchedulersExecutor = schedulersExecutor;
        mPreferencesHelper = preferencesHelper;
    }
    
    public Single<MessageResponse> create(String propertyId) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        History history = new History(userId, propertyId);
        
        return create(history);
    }
    
    public Single<MessageResponse> create(History history) {
        return mHistoryService.create(history)
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new HistoryException(response.getErrors());
                } catch (Exception ex) {
                    if (ex instanceof HistoryException) {
                        throw ex;
                    }
                    throw new HistoryException(ex.getMessage());
                }
                
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<HistoryDetailResultEntry> getHistoryDetails(int currentPage, int limit) {
        return mHistoryService.getDetails(currentPage, limit)
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new HistoryException(response.getErrors());
                } catch (Exception ex) {
                    if (ex instanceof HistoryException) {
                        throw ex;
                    }
                    throw new HistoryException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> delete(String propertyId) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        History history = new History(userId, propertyId);
        
        return delete(history);
    }
    
    public Single<MessageResponse> delete(History history) {
        return mHistoryService.delete(history)
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new HistoryException(response.getErrors());
                } catch (Exception ex) {
                    if (ex instanceof HistoryException) {
                        throw ex;
                    }
                    throw new HistoryException(ex.getMessage());
                }
                
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> clearHistory(String userId) {
        Single<JsonResponse<MessageResponse>> result = mHistoryService.clearHistory(userId);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new HistoryException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof HistoryException) {
                        throw ex;
                    }
                    throw new HistoryException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> clearHistory() {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        
        return clearHistory(userId);
    }
}
