package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.AdminService;
import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.exception.AdminException;
import com.odauday.exception.BaseException;
import com.odauday.exception.FavoriteException;
import com.odauday.model.Property;
import com.odauday.model.User;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 5/4/2018.
 */

public class AdminRepository implements Repository {
    private final AdminService mAdminService;
    private final SchedulersExecutor mSchedulersExecutor;
    @Inject
    public AdminRepository(AdminService adminService, SchedulersExecutor schedulersExecutor) {
        mAdminService = adminService;
        mSchedulersExecutor = schedulersExecutor;
    }
    public Single<List<Property>> getPropertyByAdmin(String page,String limit,String status,String likeName) {
        Single<JsonResponse<List<Property>>> result = mAdminService.getPropertyByAdmin(page,limit,status,likeName);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new AdminException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof AdminException) {
                        throw ex;
                    }
                    throw new AdminException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<List<User>> getUserByAdmin(String page,String limit,String status,String likeName) {
        Single<JsonResponse<List<User>>> result = mAdminService.getUserByAdmin(page,limit,status,likeName);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new AdminException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof AdminException) {
                        throw ex;
                    }
                    throw new AdminException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<MessageResponse> changeStatusUser(String id,String status){
        Single<JsonResponse<MessageResponse>> result = mAdminService
            .changeStatusUser(id, status);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new BaseException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof BaseException) {
                        throw ex;
                    }
                    throw new BaseException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    public Single<MessageResponse> changeStatusProperty(String id,String status){
        Single<JsonResponse<MessageResponse>> result = mAdminService
            .changeStatusProperty(id, status);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new BaseException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof BaseException) {
                        throw ex;
                    }
                    throw new BaseException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
