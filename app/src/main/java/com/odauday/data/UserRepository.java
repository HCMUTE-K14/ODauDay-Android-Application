package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.UserService;
import com.odauday.model.User;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 2/27/18.
 */

public class UserRepository implements Repository {
    
    private final UserService.Public mPublicUserService;
    private final UserService.Protect mProtectUserService;
    
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public UserRepository(UserService.Public publicUserService,
              UserService.Protect protectUserService, SchedulersExecutor schedulersExecutor) {
        this.mPublicUserService = publicUserService;
        this.mProtectUserService = protectUserService;
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    public Single<List<User>> test(int index) {
        return mPublicUserService.test(index)
                  .subscribeOn(mSchedulersExecutor.io())
                  .observeOn(mSchedulersExecutor.ui());
    }
    
    public SchedulersExecutor scheduler() {
        return mSchedulersExecutor;
    }
}
