package com.odauday.ui.user.register;

import static com.odauday.config.Constants.Task.TASK_REGISTER;

import com.odauday.data.UserRepository;
import com.odauday.data.remote.user.model.RegisterRequest;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 3/27/18.
 */

public class RegisterViewModel extends BaseViewModel {
    
    private final UserRepository mUserRepository;
    
    @Inject
    public RegisterViewModel(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }
    
    
    public void register(RegisterRequest request) {
        
        Disposable disposable = mUserRepository.register(request)
                  .doOnSubscribe(onSubscribe -> response.setValue(Resource.loading(null)))
                  .subscribe(success -> response.setValue(Resource.success(TASK_REGISTER, success)),
                            error -> response.setValue(Resource.error(TASK_REGISTER, error)));
        mCompositeDisposable.add(disposable);
    }
}
