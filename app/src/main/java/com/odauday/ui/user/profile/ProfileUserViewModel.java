package com.odauday.ui.user.profile;

import com.odauday.config.Constants.Task;
import com.odauday.data.HistoryRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/28/18.
 */
public class ProfileUserViewModel extends BaseViewModel {
    
    private final HistoryRepository mHistoryRepository;
    
    @Inject
    public ProfileUserViewModel(HistoryRepository historyRepository) {
        mHistoryRepository = historyRepository;
    }
    
    public void clearHistory() {
        Disposable disposable = mHistoryRepository
            .clearHistory()
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(Task.TASK_CLEAR_HISTORY, success));
            }, throwable -> {
                response.setValue(Resource.error(Task.TASK_CLEAR_HISTORY, throwable));
            });
    }
    
}
