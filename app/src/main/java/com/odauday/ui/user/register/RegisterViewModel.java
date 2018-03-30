package com.odauday.ui.user.register;

import com.odauday.data.UserRepository;
import com.odauday.data.remote.model.users.RegisterRequest;
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
                  .doOnSubscribe(onSubscribe -> {
                      response.setValue(Resource.loading(null));
                  })
                  .subscribe(success -> {
                      response.setValue(Resource.success(success));
                  }, error -> {
                      response.setValue(Resource.error(error));
                  });
        
        mCompositeDisposable.add(disposable);
    }
}
