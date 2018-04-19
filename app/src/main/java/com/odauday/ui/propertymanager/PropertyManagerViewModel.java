package com.odauday.ui.propertymanager;

import com.odauday.data.PropertyRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/18/2018.
 */

public class PropertyManagerViewModel extends BaseViewModel {
    PropertyRepository mPropertyRepository;
    private PropertyManagerContract mPropertyManagerContract;
    @Inject
    public PropertyManagerViewModel(PropertyRepository propertyRepository) {
        mPropertyRepository = propertyRepository;
    }
    public void getPropertyOfUser(String user_id){
        Disposable disposable=mPropertyRepository.getPropertyOfUser(user_id)
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
    public void deleteProperty(String property_id){
        Disposable disposable=mPropertyRepository.deleteProperty(property_id)
            .doOnSubscribe(onSubscribe -> {
                mPropertyManagerContract.loading(true);
            })
            .subscribe(success -> {
                mPropertyManagerContract.onSuccessDeleteProperty(success);
                mPropertyManagerContract.loading(false);
            }, error -> {
                mPropertyManagerContract.onErrorDeleteProperty(error);
                mPropertyManagerContract.loading(false);
            });
    
        mCompositeDisposable.add(disposable);
    }
    public void setPropertyManagerContract(
        PropertyManagerContract propertyManagerContract) {
        mPropertyManagerContract = propertyManagerContract;
    }
}
