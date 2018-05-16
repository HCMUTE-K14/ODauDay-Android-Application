package com.odauday.ui.admin.usermanager;

import com.odauday.data.AdminRepository;
import com.odauday.data.PropertyRepository;
import com.odauday.data.UserRepository;
import com.odauday.ui.admin.propertymanager.ConfirmPropertyContract;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by kunsubin on 5/10/2018.
 */

public class UserManagerModel extends BaseViewModel {
    
    AdminRepository mAdminRepository;
    private UserManagerContract mUserManagerContract;
    @Inject
    public UserManagerModel(AdminRepository adminRepository) {
        mAdminRepository = adminRepository;
    }
    
    public void setUserManagerContract(
        UserManagerContract userManagerContract) {
        mUserManagerContract = userManagerContract;
    }
    
    public void getUserByAdmin(String page,String limit,String status,String likeName) {
        Disposable disposable = mAdminRepository.getUserByAdmin(page, limit, status,likeName)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success("",success));
            }, error -> {
                response.setValue(Resource.error("",error));
            });
        mCompositeDisposable.add(disposable);
    }
    public void changeStatusUser(String id,String status) {
        Disposable disposable = mAdminRepository.changeStatusUser(id, status)
            .doOnSubscribe(onSubscribe -> {
                mUserManagerContract.loading(true);
            })
            .subscribe(success -> {
                mUserManagerContract.onChangeStatusSuccess(success);
                mUserManagerContract.loading(false);
            }, error -> {
                mUserManagerContract.onChangeStatusFailure(error);
                mUserManagerContract.loading(false);
            });
        mCompositeDisposable.add(disposable);
    }
    
}
