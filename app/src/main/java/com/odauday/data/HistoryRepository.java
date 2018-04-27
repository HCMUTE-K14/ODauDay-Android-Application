package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.HistoryService;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.exception.HistoryException;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/27/2018.
 */

public class HistoryRepository {
    
    private final HistoryService mHistoryService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public HistoryRepository(HistoryService historyService,
        SchedulersExecutor schedulersExecutor) {
        mHistoryService = historyService;
        mSchedulersExecutor = schedulersExecutor;
    }
    
    public Single<MessageResponse> clearHistory(String userId) {
        Single<JsonResponse<MessageResponse>> result = mHistoryService.clearHistory(userId);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new HistoryException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof HistoryException) {
                        throw ex;
                    }
                    throw new HistoryException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
