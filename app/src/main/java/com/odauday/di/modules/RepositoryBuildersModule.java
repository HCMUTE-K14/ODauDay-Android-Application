package com.odauday.di.modules;

import com.odauday.SchedulersExecutor;
import com.odauday.data.AutoCompletePlaceRepository;
import com.odauday.data.FavoriteRepository;
import com.odauday.data.GeoInfoRepository;
import com.odauday.data.HistoryRepository;
import com.odauday.data.PropertyRepository;
import com.odauday.data.RecentTagRepository;
import com.odauday.data.SavedSearchRepository;
import com.odauday.data.SearchPropertyRepository;
import com.odauday.data.TagRepository;
import com.odauday.data.UserRepository;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.local.place.RecentSearchPlaceService;
import com.odauday.data.local.tag.RecentTagService;
import com.odauday.data.remote.FavoriteService;
import com.odauday.data.remote.HistoryService;
import com.odauday.data.remote.SavedSearchService;
import com.odauday.data.remote.TagService;
import com.odauday.data.remote.autocompleteplace.AutoCompletePlaceService;
import com.odauday.data.remote.geoinfo.GeoInfoService;
import com.odauday.data.remote.image.ImageService;
import com.odauday.data.remote.property.PropertyService;
import com.odauday.data.remote.property.SearchService;
import com.odauday.data.remote.user.UserService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 2/28/18.
 */
@Module
public class RepositoryBuildersModule {
    
    @Provides
    @Singleton
    UserRepository provideUserRepository(
        UserService.Public publicUserService,
        UserService.Protect protectUserService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        return new UserRepository(
            publicUserService,
            protectUserService,
            preferencesHelper,
            schedulersExecutor);
    }
    
    @Provides
    @Singleton
    TagRepository provideTagRepository(TagService.Public publicTagService,
        TagService.Protect protectTagService, SchedulersExecutor schedulersExecutor) {
        return new TagRepository(publicTagService, protectTagService, schedulersExecutor);
    }
    
    @Provides
    @Singleton
    PropertyRepository providePropertyRepository(PropertyService protectPropertyService,
        ImageService imageService,
        SchedulersExecutor schedulersExecutor) {
        return new PropertyRepository(protectPropertyService,
            imageService,
            schedulersExecutor);
    }
    
    @Provides
    @Singleton
    FavoriteRepository provideFavoriteRepository(FavoriteService favoriteService,
        SchedulersExecutor schedulersExecutor) {
        return new FavoriteRepository(favoriteService, schedulersExecutor);
    }
    
    @Provides
    @Singleton
    SavedSearchRepository provideSearchRepository(SavedSearchService savedSearchService,
        SchedulersExecutor schedulersExecutor) {
        return new SavedSearchRepository(savedSearchService, schedulersExecutor);
    }
    
    @Provides
    @Singleton
    HistoryRepository provideHistoryRepository(HistoryService historyService,
        SchedulersExecutor schedulersExecutor) {
        return new HistoryRepository(historyService, schedulersExecutor);
    }
    
    @Provides
    @Singleton
    SearchPropertyRepository provideSearchPropertyRepository(
        EventBus eventBus,
        SearchService searchService,
        SchedulersExecutor schedulersExecutor) {
        return new SearchPropertyRepository(
            eventBus,
            searchService,
            schedulersExecutor);
    }
    
    @Provides
    @Singleton
    AutoCompletePlaceRepository provideAutoCompletePlaceRepository(
        AutoCompletePlaceService autoCompletePlaceService,
        RecentSearchPlaceService recentSearchPlaceService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        return new AutoCompletePlaceRepository(
            autoCompletePlaceService,
            recentSearchPlaceService,
            preferencesHelper,
            schedulersExecutor);
    }
    
    @Provides
    @Singleton
    GeoInfoRepository provideGeoInfoRepository(GeoInfoService geoInfoService,
        SchedulersExecutor schedulersExecutor) {
        return new GeoInfoRepository(geoInfoService, schedulersExecutor);
    }
    
    //--------------------------LOCAL---------------------------//
    
    @Provides
    @Singleton
    RecentTagRepository provideRecentTagRepository(
        RecentTagService recentTagService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        return new RecentTagRepository(
            recentTagService,
            preferencesHelper,
            schedulersExecutor);
    }
}
