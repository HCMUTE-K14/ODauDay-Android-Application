package com.odauday.ui.admin.propertymanager;

import com.odauday.data.AdminRepository;
import com.odauday.data.PropertyRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 5/4/2018.
 */

public class ConfirmPropertyModel extends BaseViewModel{
    
    AdminRepository mAdminRepository;
    PropertyRepository mPropertyRepository;
    private ConfirmPropertyContract mConfirmPropertyContract;
    
    @Inject
    public ConfirmPropertyModel(AdminRepository adminRepository,
        PropertyRepository propertyRepository) {
        mAdminRepository = adminRepository;
    }
    
    public void setConfirmPropertyContract(
        ConfirmPropertyContract confirmPropertyContract) {
        mConfirmPropertyContract = confirmPropertyContract;
    }
    
    public void getPropertyByAdmin(String page,String limit,String status,String likeName) {
        Disposable disposable = mAdminRepository.getPropertyByAdmin(page, limit, status,likeName)
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
    public void deleteProperty(String property_id) {
        Disposable disposable = mPropertyRepository.deleteProperty(property_id)
            .doOnSubscribe(onSubscribe -> {
                mConfirmPropertyContract.onLoadingAction(true);
            })
            .subscribe(success -> {
                mConfirmPropertyContract.onSuccessAction(success);
                mConfirmPropertyContract.onLoadingAction(false);
            }, error -> {
                mConfirmPropertyContract.onErrorAction(error);
                mConfirmPropertyContract.onLoadingAction(false);
            });
        
        mCompositeDisposable.add(disposable);
    }
    public void changeStatusProperty(String property_id,String status) {
        Disposable disposable = mAdminRepository.changeStatusProperty(property_id, status)
            .doOnSubscribe(onSubscribe -> {
                mConfirmPropertyContract.onLoadingAction(true);
            })
            .subscribe(success -> {
                mConfirmPropertyContract.onSuccessAction(success);
                mConfirmPropertyContract.onLoadingAction(false);
            }, error -> {
                mConfirmPropertyContract.onErrorAction(error);
                mConfirmPropertyContract.onLoadingAction(false);
            });
        
        mCompositeDisposable.add(disposable);
    }
}
