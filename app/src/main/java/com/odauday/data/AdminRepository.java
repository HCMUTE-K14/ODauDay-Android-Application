package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.AdminService;
import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.exception.AdminException;
import com.odauday.exception.FavoriteException;
import com.odauday.model.Property;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;

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
}
