package com.odauday.ui.test;

import com.odauday.data.UserRepository;
import com.odauday.exception.BaseException;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import javax.inject.Inject;

/**
 * Created by infamouSs on 2/28/18.
 */

public class GithubUserViewModel extends BaseViewModel {
    
    
    private final UserRepository mUserRepository;
    
    public int currentIndex = 1;
    
    @Inject
    public GithubUserViewModel(UserRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
    }
    
    public void getData() {
        mCompositeDisposable.add(mUserRepository.test(currentIndex)
                  .doOnSubscribe(disposable -> {
                      setIsLoading(true);
                      response.setValue(Resource.loading(null));
                  })
                  .subscribe(users -> {
                                setIsLoading(false);
                                if(users == null || users.isEmpty()){
                                    response.setValue(Resource.error(new BaseException("Empty Data")));
                                    return;
                                }
                                response.setValue(Resource.success(users));
                            },
                            throwable -> {
                                setIsLoading(false);
                                response.setValue(Resource.error(throwable));
                            }));
    }
    
    public void loadNextPage() {
        currentIndex++;
    
        getData();
    }
}
