package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.local.tag.RecentTag;
import com.odauday.data.local.tag.RecentTagService;
import com.odauday.exception.TagException;
import com.odauday.model.Tag;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/9/18.
 */

public class RecentTagRepository implements Repository {
    
    private final RecentTagService mRecentTagService;

    private final PreferencesHelper mPreferencesHelper;

    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public RecentTagRepository(RecentTagService recentTagService,
              PreferencesHelper preferencesHelper,
              SchedulersExecutor schedulersExecutor) {
        this.mRecentTagService = recentTagService;
        this.mPreferencesHelper = preferencesHelper;
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    public Single<Long> create(Tag tag) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        
        RecentTag recentTag = new RecentTag(tag, userId);
        return mRecentTagService
                  .create(recentTag)
                  .onErrorResumeNext(throwable -> {
                      throw new TagException(throwable.getMessage());
                  })
                  .subscribeOn(mSchedulersExecutor.computation())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<List<Tag>> findAllRecentTagByCurrentUserId() {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        
        return mRecentTagService
                  .findAllRecentTagByUserId(userId)
                  .onErrorResumeNext(throwable -> {
                      throw new TagException(throwable.getMessage());
                  })
                  .subscribeOn(mSchedulersExecutor.computation())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Long> delete(Tag tag) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        
        return mRecentTagService
                  .delete(new RecentTag(tag, userId))
                  .onErrorResumeNext(throwable -> {
                      throw new TagException(throwable.getMessage());
                  })
                  .subscribeOn(mSchedulersExecutor.computation())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<Long> save(List<Tag> tags) {
        String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
        List<RecentTag> recentTags = makeRecentTagsFromTags(tags, userId);
        return mRecentTagService
                  .save(recentTags)
                  .onErrorResumeNext(throwable -> {
                      throw new TagException(throwable.getMessage());
                  })
                  .subscribeOn(mSchedulersExecutor.computation())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    private List<RecentTag> makeRecentTagsFromTags(List<Tag> tags, String userId) {
        List<RecentTag> recentTags = new ArrayList<>();
        for (Tag tag : tags) {
            recentTags.add(new RecentTag(tag, userId));
        }
        
        return recentTags;
    }
}
