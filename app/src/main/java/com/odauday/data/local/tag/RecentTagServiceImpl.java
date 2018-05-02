package com.odauday.data.local.tag;

import com.odauday.data.local.tag.RecentTagDao.Properties;
import com.odauday.model.Tag;
import com.odauday.utils.TextUtils;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/9/18.
 */

public class RecentTagServiceImpl implements RecentTagService {
    
    
    private final RecentTagDao mRecentTagDao;
    
    public RecentTagServiceImpl(RecentTagDao recentTagDao) {
        this.mRecentTagDao = recentTagDao;
    }
    
    @Override
    public Single<Long> create(RecentTag tag) {
        return Single.fromCallable(() -> mRecentTagDao.insert(tag));
    }
    
    @Override
    public Single<List<Tag>> findAllRecentTagByUserId(String userId) {
        return Single.fromCallable(() -> {
            List<RecentTag> recentTags = mRecentTagDao
                      .queryBuilder()
                      .where(Properties.UserId.eq(userId))
                      .list();
            return convert(recentTags);
        });
    }
    
    @Override
    public Single<Long> delete(RecentTag tag) {
        return Single.fromCallable(() -> {
            mRecentTagDao.delete(tag);
            return 1L;
        });
    }
    
    @Override
    public List<Tag> convert(List<RecentTag> recentTags) {
        List<Tag> tags = new ArrayList<>();
        for (RecentTag recentTag : recentTags) {
            tags.add(RecentTag.convertToTag(recentTag));
        }
        return tags;
    }
    
    @Override
    public Single<Long> save(List<RecentTag> recentTags) {
        return Single.fromCallable(() -> {
            for (RecentTag recentTag : recentTags) {
                if (TextUtils.isEmpty(recentTag.getId())) {
                    continue;
                }
                boolean isExists = mRecentTagDao
                                             .queryBuilder()
                                             .where(Properties.Id.eq(recentTag.getId()))
                                             .count() > 0;
                if (!isExists) {
                    mRecentTagDao.insert(recentTag);
                }
            }
            return 1L;
        });
    }
}
