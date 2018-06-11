package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.property.SearchService;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.data.remote.property.model.SearchResult;
import com.odauday.exception.SearchException;
import com.odauday.ui.search.common.event.OnCompleteDownloadPropertyEvent;
import com.odauday.ui.search.common.event.OnErrorDownloadPropertyEvent;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 4/6/18.
 */

public class SearchPropertyRepository {

    private final SearchService mSearchService;
    private final EventBus mBus;
    private final SchedulersExecutor mSchedulersExecutor;
    //private final HistoryLocalRepository mHistoryLocalRepository;
    private SearchRequest mSearchRequest;

    @Inject
    public SearchPropertyRepository(EventBus eventBus,
              SearchService searchService,
            //  HistoryLocalRepository historyLocalRepository,
              SchedulersExecutor schedulersExecutor) {
        this.mSearchService = searchService;
        this.mSchedulersExecutor = schedulersExecutor;
        this.mBus = eventBus;
      //  this.mHistoryLocalRepository = historyLocalRepository;
    }
    
    private Single<SearchResult> callSearchAPI(SearchRequest searchRequest) {
        return mSearchService.search(searchRequest)
                  .subscribeOn(mSchedulersExecutor.io())
                  .observeOn(mSchedulersExecutor.ui())
                  .map(response -> {
                      try {
                          if (response.isSuccess()) {
                              return response.getData();
                          } else {
                              throw new SearchException(response.getErrors());
                          }
                      } catch (Exception ex) {
                          if (ex instanceof SearchException) {
                              throw ex;
                          }
                          throw new SearchException(ex.getMessage());
                      }
                  });
    }
    
    public void search(SearchRequest searchRequest) {
        callSearchAPI(searchRequest)
                  .doOnSubscribe(running -> {
                      mBus.post(SearchPropertyState.RUNNING);
                  })
                  .subscribe(new DisposableSingleObserver<SearchResult>() {
                      @Override
                      public void onSuccess(SearchResult searchResult) {
                          mBus.post(new OnCompleteDownloadPropertyEvent(
                                    SearchPropertyState.COMPLETE_DOWNLOAD_DATA_FROM_SERVICE,
                                    searchResult));
                      }
                      
                      @Override
                      public void onError(Throwable e) {
                          SearchException exception = new SearchException(e.getMessage());
                          mBus.post(new OnErrorDownloadPropertyEvent(SearchPropertyState.ERROR,
                                    exception));
                      }
                  });
        
    }
    
    public SearchRequest getCurrentSearchRequest() {
        return mSearchRequest;
    }
    
    public void setCurrentSearchRequest(SearchRequest searchRequest) {
        this.mSearchRequest = searchRequest;
    }
}
