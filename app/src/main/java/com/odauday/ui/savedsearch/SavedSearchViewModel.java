package com.odauday.ui.savedsearch;

import com.odauday.data.SavedSearchRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/9/2018.
 */

public class SavedSearchViewModel extends BaseViewModel {
    
    private SavedSearchRepository mSavedSearchRepository;
    
    @Inject
    public SavedSearchViewModel(SavedSearchRepository savedSearchRepository) {
        mSavedSearchRepository = savedSearchRepository;
    }
    
    public void getSearchByUser(String userId) {
        Disposable disposable = mSavedSearchRepository.getSearchByUser(userId)
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
    
    public void removeSearch(String search_id) {
        Disposable disposable = mSavedSearchRepository.removeSearch(search_id)
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
