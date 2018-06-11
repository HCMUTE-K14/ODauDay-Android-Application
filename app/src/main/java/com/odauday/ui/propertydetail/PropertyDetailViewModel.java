package com.odauday.ui.propertydetail;

import static com.odauday.config.Constants.Task.TASK_CREATE_FAVORITE;
import static com.odauday.config.Constants.Task.TASK_GET_DETAIL_PROPERTY;

import com.odauday.data.FavoriteRepository;
import com.odauday.data.PropertyRepository;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.model.Favorite;
import com.odauday.model.PropertyDetail;
import com.odauday.utils.TextUtils;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 5/16/18.
 */
public class PropertyDetailViewModel extends BaseViewModel {
    
    private final PropertyRepository mPropertyRepository;
    private final PreferencesHelper mPreferencesHelper;
    private final FavoriteRepository mFavoriteRepository;
    
    @Inject
    public PropertyDetailViewModel(PropertyRepository propertyRepository,
        FavoriteRepository favoriteRepository,
        PreferencesHelper preferencesHelper) {
        this.mPropertyRepository = propertyRepository;
        this.mPreferencesHelper = preferencesHelper;
        this.mFavoriteRepository = favoriteRepository;
    }
    
    public void getFullDetail(PropertyDetail detail) {
        String id = detail.getId();
        Disposable disposable = mPropertyRepository.getFullDetail(id)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                Timber.d("Success get detail");
                response.setValue(Resource.success(TASK_GET_DETAIL_PROPERTY, success));
            }, throwable -> {
                Timber.d("fail get detail " + throwable.getMessage());
                response.setValue(Resource.error(TASK_GET_DETAIL_PROPERTY, throwable));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
    public void createFavorite(String propertyId) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        Favorite favorite = new Favorite(userId, propertyId);
        
        Disposable disposable = mFavoriteRepository
            .checkFavorite(favorite)
            .subscribe(success -> {
                response.setValue(Resource.success(TASK_CREATE_FAVORITE, success));
            }, throwable -> {
                response.setValue(Resource.error(TASK_CREATE_FAVORITE, throwable));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
    public void removeFavorite(String propertyId) {
    
    }
}
