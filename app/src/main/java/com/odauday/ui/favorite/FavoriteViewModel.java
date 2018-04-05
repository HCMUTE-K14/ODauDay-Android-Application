package com.odauday.ui.favorite;

import android.util.Log;
import com.odauday.data.FavoriteRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/4/2018.
 */

public class FavoriteViewModel extends BaseViewModel{
    
    FavoriteRepository mFavoriteRepository;
    
    @Inject
    public FavoriteViewModel(FavoriteRepository favoriteRepository) {
        this.mFavoriteRepository = favoriteRepository;
    }
    public void getFavoritePropertyByUser(String userId){
        Disposable disposable=mFavoriteRepository.getFavoritePropertyByUser(userId)
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
