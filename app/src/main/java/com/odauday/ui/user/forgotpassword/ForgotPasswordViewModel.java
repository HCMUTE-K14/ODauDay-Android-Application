package com.odauday.ui.user.forgotpassword;

import static com.odauday.config.Constants.Task.TASK_FORGOTPASSWORD;

import com.odauday.data.UserRepository;
import com.odauday.data.remote.user.model.ForgotPasswordRequest;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 3/29/18.
 */

public class ForgotPasswordViewModel extends BaseViewModel {
    
    private final UserRepository mUserRepository;
    
    @Inject
    public ForgotPasswordViewModel(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }
    
    
    public void forgotPassword(ForgotPasswordRequest request) {
        
        Disposable disposable = mUserRepository.forgotPassword(request)
                  .doOnSubscribe(onSubscribe -> response.setValue(Resource.loading(null)))
                  .subscribe(
                            success -> response
                                      .setValue(Resource.success(TASK_FORGOTPASSWORD, success)),
                            error -> response.setValue(Resource.error(TASK_FORGOTPASSWORD, error)));
        
        mCompositeDisposable.add(disposable);
    }
}
