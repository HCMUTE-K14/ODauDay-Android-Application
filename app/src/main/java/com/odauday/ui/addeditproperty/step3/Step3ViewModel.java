package com.odauday.ui.addeditproperty.step3;

import static com.odauday.config.Constants.Task.TASK_CREATE_TAGS;
import static com.odauday.config.Constants.Task.TASK_GET_RECENT_TAG;

import com.odauday.data.RecentTagRepository;
import com.odauday.model.Tag;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import java.util.List;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/27/18.
 */
public class Step3ViewModel extends BaseViewModel {
    
    private final RecentTagRepository mRecentTagRepository;
    
    public Step3ViewModel(RecentTagRepository recentTagRepository) {
        this.mRecentTagRepository = recentTagRepository;
    }
    
    public void insertCurrentTags(List<Tag> tags) {
        Disposable disposable = mRecentTagRepository
                  .save(tags)
                  .subscribe(success -> response
                                      .setValue(Resource.success(TASK_CREATE_TAGS, success)),
                            error -> response.setValue(Resource.error(TASK_CREATE_TAGS, error)));
        
        mCompositeDisposable.add(disposable);
    }
    
    public void getRecentTags() {
        Disposable disposable = mRecentTagRepository
                  .findAllRecentTagByCurrentUserId()
                  .subscribe(success -> {
                      Timber.d(success.toString());
                      response.setValue(Resource.success(TASK_GET_RECENT_TAG, success));
                  }, error -> {
                      Timber.d(error.getMessage());
                      response.setValue(Resource.error(TASK_GET_RECENT_TAG, error));
                  });
        
        mCompositeDisposable.add(disposable);
    }
}
