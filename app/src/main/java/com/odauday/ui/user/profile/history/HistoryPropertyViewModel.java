package com.odauday.ui.user.profile.history;

import com.odauday.config.Constants.Task;
import com.odauday.data.HistoryRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by infamouSs on 5/29/18.
 */
public class HistoryPropertyViewModel extends BaseViewModel {
    
    public static final int LIMIT_PER_REQUEST = 10;
    
    private final HistoryRepository mHistoryRepository;
    
    private int mCurrentPage = 1;
    
    private long mCount = 0;
    
    private long mTotalPage = 1;
    
    public HistoryPropertyViewModel(HistoryRepository historyRepository) {
        this.mHistoryRepository = historyRepository;
        
    }
    
    public void getHistory() {
        if (mCurrentPage > mTotalPage) {
            return;
        }
        Disposable disposable = mHistoryRepository
            .getHistoryDetails(mCurrentPage, LIMIT_PER_REQUEST)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                mCount = success.getCount();
                mTotalPage = success.getPages();
                response.setValue(Resource.success(Task.TASK_GET_HISTORY, success));
            }, throwable -> {
                response.setValue(Resource.error(Task.TASK_GET_HISTORY, throwable));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
    public void resetStage() {
        mCurrentPage = 1;
        mCount = 0;
        mTotalPage = 1;
    }
    
    public void loadNextPage() {
        mCurrentPage++;
        
        getHistory();
    }
}
