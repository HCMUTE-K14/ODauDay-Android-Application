package com.odauday.ui.user.tag;

import android.arch.lifecycle.ViewModelProvider;
import com.odauday.data.TagRepository;
import com.odauday.data.UserRepository;
import com.odauday.ui.user.register.RegisterViewModel;
import com.odauday.viewmodel.ViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 4/1/2018.
 */
@Module
public class TagModule {
    @Provides
    ViewModelProvider.Factory mainViewModelProvider(TagViewModel mainViewModel) {
        return new ViewModelFactory<>(mainViewModel);
    }
    @Provides
    TagViewModel provideTagViewModel(TagRepository tagRepository) {
        return new TagViewModel(tagRepository);
    }
}
