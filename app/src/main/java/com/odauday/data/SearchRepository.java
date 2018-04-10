package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.SearchService;
import com.odauday.data.remote.SearchService.Protect;
import com.odauday.data.remote.SearchService.Public;
import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.model.SearchResponse;
import com.odauday.exception.FavoriteException;
import com.odauday.exception.SearchException;
import com.odauday.model.Search;
import io.reactivex.Single;
import javax.inject.Inject;


/**
 * Created by kunsubin on 4/9/2018.
 */

public class SearchRepository implements Repository{
    private final SearchService.Public mPublicSearchService;
    private final SearchService.Protect mProtectSearchService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public SearchRepository(Public publicSearchService,
        Protect protectSearchService,
        SchedulersExecutor schedulersExecutor) {
        mPublicSearchService = publicSearchService;
        mProtectSearchService = protectSearchService;
        mSchedulersExecutor = schedulersExecutor;
    }
    public Single<SearchResponse> getSearchByUser(String user_id) {
        Single<JsonResponse<SearchResponse>> result = mProtectSearchService.getSearchByUser(user_id);
        return result
            .map(response -> {
                try {
                    if(response.isSuccess()){
                        return response.getData();
                    }
                    throw new SearchException(response.getErrors());
                    
                } catch (Exception ex) {
                    if(ex instanceof SearchException){
                        throw ex;
                    }
                    throw new SearchException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<MessageResponse> saveSearch(Search search) {
        Single<JsonResponse<MessageResponse>> result = mProtectSearchService.saveSearch(search);
        return result
            .map(response -> {
                try {
                    if(response.isSuccess()){
                        return response.getData();
                    }
                    throw new SearchException(response.getErrors());
                    
                } catch (Exception ex) {
                    if(ex instanceof SearchException){
                        throw ex;
                    }
                    throw new SearchException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<MessageResponse> removeSearch(String search_id) {
        Single<JsonResponse<MessageResponse>> result = mProtectSearchService.removeSearch(search_id);
        return result
            .map(response -> {
                try {
                    if(response.isSuccess()){
                        return response.getData();
                    }
                    throw new SearchException(response.getErrors());
                    
                } catch (Exception ex) {
                    if(ex instanceof SearchException){
                        throw ex;
                    }
                    throw new SearchException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
