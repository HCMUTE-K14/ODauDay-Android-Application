package com.odauday.ui.user.login;

import com.odauday.data.UserRepository;
import com.odauday.data.remote.model.users.AbstractAuthRequest;
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
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(""));
            }, error -> {
                response.setValue(Resource.error(error));
            });
        
        mCompositeDisposable.add(disposable);
    }
}
