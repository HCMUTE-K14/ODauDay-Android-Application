package com.odauday.data.local.history;

import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/14/18.
 */
public interface HistoryService {
    
    Single<Long> create(HistoryProperty historyProperty);
    
    Single<Long> delete(HistoryProperty historyProperty);
    
    Single<List<HistoryProperty>> findHistoryByUserId(String userId);
    
    Single<Long> save(List<HistoryProperty> historyProperties);
}
