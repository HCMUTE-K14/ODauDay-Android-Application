package com.odauday.ui.user.profile;

import com.odauday.config.Constants.Task;
import com.odauday.data.HistoryRepository;
import com.odauday.data.UserRepository;
import com.odauday.data.remote.user.model.ChangePasswordRequest;
import com.odauday.model.User;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/28/18.
 */
public class ProfileUserViewModel extends BaseViewModel {
    
    private final HistoryRepository mHistoryRepository;
    private final UserRepository mUserRepository;
    
    @Inject
    public ProfileUserViewModel(HistoryRepository historyRepository,
        UserRepository userRepository) {
        mHistoryRepository = historyRepository;
        mUserRepository = userRepository;
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
        
        mCompositeDisposable.add(disposable);
    }
    
    public void updateProfile(User user) {
        Disposable disposable = mUserRepository
            .updateProfile(user)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(Task.TASK_UPDATE_PROFILE, success));
            }, throwable -> {
                response.setValue(Resource.error(Task.TASK_UPDATE_PROFILE, throwable));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
    public void reSendActiveAccount(String email) {
        Disposable disposable = mUserRepository
            .reSendActivation(email)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(Task.TASK_RESEND_ACTIVE_ACCOUNT, success));
            }, throwable -> {
                response.setValue(Resource.error(Task.TASK_RESEND_ACTIVE_ACCOUNT, throwable));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
    public void changePassword(ChangePasswordRequest request) {
        Disposable disposable = mUserRepository
            .changePassword(request)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(Task.TASK_CHANGE_PASSWORD, success));
            }, throwable -> {
                response.setValue(Resource.error(Task.TASK_CHANGE_PASSWORD, throwable));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
    public void getAmount() {
        Disposable disposable = mUserRepository
            .getAmount()
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(Task.TASK_GET_AMOUNT, success));
            }, throwable -> {
                response.setValue(Resource.error(Task.TASK_GET_AMOUNT, throwable));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
}
