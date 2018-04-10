package com.odauday.ui.savedsearch;

import com.odauday.data.SearchRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/9/2018.
 */

public class SavedSearchViewModel extends BaseViewModel {
    private SearchRepository mSearchRepository;
    
    @Inject
    public SavedSearchViewModel(SearchRepository searchRepository) {
        mSearchRepository = searchRepository;
    }
    
    public void getSearchByUser(String userId){
        Disposable disposable=mSearchRepository.getSearchByUser(userId)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(success));
            }, error -> {
                response.setValue(Resource.error(error));
            });
        
        mCompositeDisposable.add(disposable);
    }
    public void removeSearch(String search_id){
        Disposable disposable=mSearchRepository.removeSearch(search_id)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(success));
            }, error -> {
                response.setValue(Resource.error(error));
            });
        
        mCompositeDisposable.add(disposable);
    }
}
