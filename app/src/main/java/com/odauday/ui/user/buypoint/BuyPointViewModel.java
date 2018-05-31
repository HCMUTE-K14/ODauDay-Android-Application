package com.odauday.ui.user.buypoint;

import com.odauday.config.Constants.Task;
import com.odauday.data.PremiumRepository;
import com.odauday.data.remote.premium.model.SubscribeRequest;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/31/18.
 */
public class BuyPointViewModel extends BaseViewModel {
    
    private final PremiumRepository mPremiumRepository;
    
    @Inject
    public BuyPointViewModel(PremiumRepository premiumRepository) {
        this.mPremiumRepository = premiumRepository;
    }
    
    public void getPremium() {
        Disposable disposable = mPremiumRepository
            .get()
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(Task.TASK_GET_PREMIUM, success));
            }, throwable -> {
                response.setValue(Resource.error(Task.TASK_GET_PREMIUM, throwable));
            });
        
        mCompositeDisposable.add(disposable);
    }
    
    public void subscribe(SubscribeRequest request){
        Disposable disposable = mPremiumRepository
            .subscribe(request)
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                response.setValue(Resource.success(Task.TASK_SUBSCRIBE, success));
            }, throwable -> {
                response.setValue(Resource.error(Task.TASK_SUBSCRIBE, throwable));
            });
    
        mCompositeDisposable.add(disposable);
    }
}
