package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.property.SearchService;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.data.remote.property.model.SearchResult;
import com.odauday.exception.SearchException;
import com.odauday.ui.search.common.event.OnCompleteDownloadProperty;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 4/6/18.
 */

public class SearchPropertyRepository {
    
    private final EventBus mBus;
    private final SearchService mSearchService;
    private final SchedulersExecutor mSchedulersExecutor;
    private SearchRequest mSearchRequest;
    
    @Inject
    public SearchPropertyRepository(EventBus eventBus,
              SearchService searchService,
              SchedulersExecutor schedulersExecutor) {
        this.mSearchService = searchService;
        this.mSchedulersExecutor = schedulersExecutor;
        this.mBus = eventBus;
    }
    
    private Single<SearchResult> callSearchAPI(SearchRequest searchRequest) {
        return mSearchService.search(searchRequest)
                  .delay(10, TimeUnit.MINUTES)
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
                      //Post EVENT RUNNING SEARCH
                  })
                  .subscribe(new DisposableSingleObserver<SearchResult>() {
                      @Override
                      public void onSuccess(SearchResult searchResult) {
                          mBus.post(new OnCompleteDownloadProperty(
                                    SearchPropertyState.COMPLETE_DOWNLOAD_DATA_FROM_SERVICE,
                                    testResult()));
                      }
                      
                      @Override
                      public void onError(Throwable e) {
                          //Post EVENT ERROR SEARCH
                          //SearchException exception = new SearchException(e.getMessage());
                          mBus.post(new OnCompleteDownloadProperty(
                                    SearchPropertyState.COMPLETE_DOWNLOAD_DATA_FROM_SERVICE,
                                    testResult()));
                      }
                  });
        
    }
    
    
    public SearchRequest getCurrentSearchRequest() {
        return mSearchRequest;
    }
    
    public void setCurrentSearchRequest(SearchRequest searchRequest) {
        this.mSearchRequest = searchRequest;
    }
    
    private List<PropertyResultEntry> testResult() {
        List<PropertyResultEntry> propertyResultEntries = new ArrayList<>();
        PropertyResultEntry entry = new PropertyResultEntry();
        entry.setAddress("Address 1");
        entry.setLocation(new GeoLocation(10.780142, 106.661388));
        entry.setFavorite(true);
        entry.setVisited(true);
        
        PropertyResultEntry entry1 = new PropertyResultEntry();
        entry1.setLocation(new GeoLocation(10.781607, 106.659660));
        entry1.setAddress("Address 2");
        
        entry1.setFavorite(false);
        entry1.setVisited(false);
        
        PropertyResultEntry entry2 = new PropertyResultEntry();
        entry2.setLocation(new GeoLocation(10.779077, 106.658770));
        entry2.setFavorite(true);
        entry2.setVisited(false);
        
        PropertyResultEntry entry3 = new PropertyResultEntry();
        entry3.setLocation(new GeoLocation(10.781849, 106.658727));
        entry3.setFavorite(false);
        entry3.setVisited(true);
        
        PropertyResultEntry entry4 = new PropertyResultEntry();
        entry4.setLocation(new GeoLocation(10.782187, 106.661259));
        
        propertyResultEntries.add(entry);
        propertyResultEntries.add(entry1);
        propertyResultEntries.add(entry2);
        propertyResultEntries.add(entry3);
        propertyResultEntries.add(entry4);
        
        for (float i = 0.5f; i < 1; i += 0.005) {
            PropertyResultEntry _entry = new PropertyResultEntry();
            _entry.setLocation(new GeoLocation(10.000000 + i, 106.000000 + i));
            _entry.setAddress("Address " + i);
            
            propertyResultEntries.add(_entry);
        }
        
        return propertyResultEntries;
    }
}
