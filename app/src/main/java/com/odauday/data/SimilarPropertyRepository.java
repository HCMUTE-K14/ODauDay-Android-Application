package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.similar.SimilarPropertyService;
import com.odauday.exception.PropertyException;
import com.odauday.exception.SearchException;
import com.odauday.ui.propertydetail.common.SimilarProperty;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 6/1/18.
 */
public class SimilarPropertyRepository implements Repository {
    
    private final SimilarPropertyService mSimilarPropertyService;
    private final SchedulersExecutor mSchedulersExecutor;
    
    public SimilarPropertyRepository(SimilarPropertyService similarPropertyService,
        SchedulersExecutor schedulersExecutor) {
        this.mSimilarPropertyService = similarPropertyService;
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    
    public Single<List<SimilarProperty>> get(String propertyId) {
        return mSimilarPropertyService
            .get(propertyId)
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui())
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    } else {
                        throw new PropertyException(response.getErrors());
                    }
                } catch (Exception ex) {
                    if (ex instanceof SearchException) {
                        throw ex;
                    }
                    throw new PropertyException(ex.getMessage());
                }
            });
    }
}
