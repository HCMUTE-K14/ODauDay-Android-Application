package com.odauday.ui.favorite;


import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.FavoriteRepository;
import com.odauday.data.TagRepository;
import com.odauday.ui.user.tag.TagViewModel;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;


/**
 * Created by infamouSs on 3/31/18.
 */
@Module
public class FavoriteTabModule {
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(FavoriteViewModel favoriteViewModel) {
        return new ViewModelFactory<>(favoriteViewModel);
    }
    @Provides
    FavoriteViewModel provideFavoriteViewModel(FavoriteRepository favoriteRepository) {
        return new FavoriteViewModel(favoriteRepository);
    }
}
