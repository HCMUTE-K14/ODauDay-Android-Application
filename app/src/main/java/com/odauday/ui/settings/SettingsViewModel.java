package com.odauday.ui.settings;

import com.odauday.data.HistoryRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/27/2018.
 */

public class SettingsViewModel extends BaseViewModel {
    
    private HistoryRepository mHistoryRepository;
    
    @Inject
    public SettingsViewModel(HistoryRepository historyRepository) {
        mHistoryRepository = historyRepository;
    }
    
    public void clearHistory(String user_id) {
        Disposable disposable = mHistoryRepository.clearHistory(user_id)
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
}
