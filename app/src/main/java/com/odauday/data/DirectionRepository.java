package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.direction.DirectionService;
import com.odauday.data.remote.direction.model.DirectionRequest;
import com.odauday.data.remote.direction.model.DirectionResponse;
import com.odauday.exception.DirectionException;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/12/18.
 */
public class DirectionRepository implements Repository {
    
    private final DirectionService mDirectionService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public DirectionRepository(DirectionService directionService,
        SchedulersExecutor schedulersExecutor) {
        this.mDirectionService = directionService;
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    
    public Single<DirectionResponse> get(DirectionRequest directionRequest) {
        return mDirectionService
            .get(directionRequest.formatFromLocation(), directionRequest.formatToLocation(),
                directionRequest.getMode())
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    } else {
                        throw new DirectionException(response.getErrors());
                    }
                } catch (Exception ex) {
                    if (ex instanceof DirectionException) {
                        throw ex;
                    }
                    throw new DirectionException(ex.getMessage());
                }
                
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
