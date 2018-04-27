package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.SavedSearchService;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.SavedSearchResponse;
import com.odauday.exception.SearchException;
import com.odauday.model.Search;
import io.reactivex.Single;
import javax.inject.Inject;


/**
 * Created by kunsubin on 4/9/2018.
 */

public class SavedSearchRepository implements Repository {
    
    private final SavedSearchService mSavedSearchService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public SavedSearchRepository(SavedSearchService savedSearchService,
        SchedulersExecutor schedulersExecutor) {
        mSavedSearchService = savedSearchService;
        mSchedulersExecutor = schedulersExecutor;
    }
    
    public Single<SavedSearchResponse> getSearchByUser(String user_id) {
        Single<JsonResponse<SavedSearchResponse>> result = mSavedSearchService
            .getSearchByUser(user_id);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new SearchException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof SearchException) {
                        throw ex;
                    }
                    throw new SearchException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> saveSearch(Search search) {
        Single<JsonResponse<MessageResponse>> result = mSavedSearchService.saveSearch(search);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new SearchException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof SearchException) {
                        throw ex;
                    }
                    throw new SearchException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> removeSearch(String search_id) {
        Single<JsonResponse<MessageResponse>> result = mSavedSearchService.removeSearch(search_id);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new SearchException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof SearchException) {
                        throw ex;
                    }
                    throw new SearchException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
