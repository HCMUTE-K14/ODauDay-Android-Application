package com.odauday.ui.favorite;

import com.odauday.data.FavoriteRepository;
import com.odauday.model.Property;
import com.odauday.model.PropertyID;
import com.odauday.model.ShareFavorite;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/4/2018.
 */

public class FavoriteViewModel extends BaseViewModel{
    
    FavoriteRepository mFavoriteRepository;
    private FavoriteContract mFavoriteContract;
    
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
    public void unCheckFavorites(List<PropertyID> list){
        Disposable disposable=mFavoriteRepository.unCheckFavorites(list)
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
    public void shareFavorite(ShareFavorite shareFavorite){
        Disposable disposable=mFavoriteRepository.shareFavorite(shareFavorite)
            .doOnSubscribe(onSubscribe -> {
                mFavoriteContract.loading(true);
            })
            .subscribe(success -> {
                mFavoriteContract.shareFavoriteSuccess(success);
                mFavoriteContract.loading(false);
            }, error -> {
                mFavoriteContract.shareFavoriteError(error);
                mFavoriteContract.loading(false);
            });
    
        mCompositeDisposable.add(disposable);
    }
    
    public void setFavoriteContract(FavoriteContract favoriteContract) {
        
        mFavoriteContract = favoriteContract;
    }
}
