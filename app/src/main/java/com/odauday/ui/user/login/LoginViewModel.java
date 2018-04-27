package com.odauday.ui.user.login;

import static com.odauday.config.Constants.Task.TASK_LOGIN;

import com.odauday.data.UserRepository;
import com.odauday.data.remote.user.model.AbstractAuthRequest;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 3/23/18.
 */

public class LoginViewModel extends BaseViewModel {
    
    private final UserRepository mUserRepository;
    
    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }
    
    
    public void login(AbstractAuthRequest request) {
        
        Disposable disposable = mUserRepository.login(request)
                  .doOnSubscribe(onSubscribe -> response.setValue(Resource.loading(null)))
                  .subscribe(success -> response.setValue(Resource.success(TASK_LOGIN, "")),
                            error -> response.setValue(Resource.error(TASK_LOGIN, error)));
        
        mCompositeDisposable.add(disposable);
    }
}
