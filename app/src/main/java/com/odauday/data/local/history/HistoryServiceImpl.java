package com.odauday.data.local.history;

import com.odauday.data.local.history.HistoryPropertyDao.Properties;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/14/18.
 */
public class HistoryServiceImpl implements HistoryService {
    
    private final HistoryPropertyDao mHistoryPropertyDao;
    
    public HistoryServiceImpl(HistoryPropertyDao historyPropertyDao) {
        this.mHistoryPropertyDao = historyPropertyDao;
    }
    
    @Override
    public Single<Long> create(HistoryProperty historyProperty) {
        return Single.fromCallable(() -> mHistoryPropertyDao.insert(historyProperty));
    }
    
    @Override
    public Single<Long> delete(HistoryProperty historyProperty) {
        return Single.fromCallable(() -> {
            mHistoryPropertyDao.delete(historyProperty);
            return 1L;
        });
    }
    
    @Override
    public Single<List<HistoryProperty>> findHistoryByUserId(String userId) {
        return Single.fromCallable(() -> mHistoryPropertyDao
                  .queryBuilder()
                  .where(Properties.UserId.eq(userId))
                  .list());
    }
    
    @Override
    public Single<Long> save(List<HistoryProperty> historyProperties) {
        return Single.fromCallable(() -> {
            for (HistoryProperty historyProperty : historyProperties) {
                boolean isExists = mHistoryPropertyDao
                                             .queryBuilder()
                                             .where(Properties.PropertyId
                                                                 .eq(historyProperty.getId()),
                                                       Properties.UserId
                                                                 .eq(historyProperty.getId()))
                                             .count() > 0;
                
                if (!isExists) {
                    mHistoryPropertyDao.insert(historyProperty);
                }
            }
            return 1L;
        });
    }
}
