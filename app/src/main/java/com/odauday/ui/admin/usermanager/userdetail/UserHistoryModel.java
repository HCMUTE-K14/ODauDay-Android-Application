package com.odauday.ui.admin.usermanager.userdetail;

import com.odauday.data.AdminRepository;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by kunsubin on 6/11/2018.
 */

public class UserHistoryModel extends BaseViewModel {
    AdminRepository mAdminRepository;
    public static final int LIMIT_PER_REQUEST = 10;
    private int mCurrentPage = 1;
    private long mCount = 0;
    private long mTotalPage = 1;
    
    @Inject
    public UserHistoryModel(AdminRepository adminRepository) {
        mAdminRepository = adminRepository;
    }
    public void getHistoryByUser(String user_id) {
        if (mCurrentPage > mTotalPage) {
            return;
        }
        Disposable disposable = mAdminRepository.getHistoryByUser(user_id, String.valueOf(mCurrentPage), String.valueOf(LIMIT_PER_REQUEST))
            .doOnSubscribe(onSubscribe -> {
                response.setValue(Resource.loading(null));
            })
            .subscribe(success -> {
                mCount = success.getCount();
                mTotalPage = success.getPages();
                response.setValue(Resource.success("", success));
            }, throwable -> {
                response.setValue(Resource.error("", throwable));
            });
        mCompositeDisposable.add(disposable);
    }
    public void resetStage() {
        mCurrentPage = 1;
        mCount = 0;
        mTotalPage = 1;
    }
    
    public void loadNextPage(String user_id) {
        mCurrentPage++;
    
        getHistoryByUser(user_id);
    }
}
