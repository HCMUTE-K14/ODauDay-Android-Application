package com.odauday.data.local.tag;

import com.odauday.model.Tag;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/9/18.
 */

public interface RecentTagService {
    
    Single<Long> create(RecentTag tag);
    
    Single<List<Tag>> findAllRecentTagByUserId(String userId);
    
    Single<Long> delete(RecentTag tag);
    
    List<Tag> convert(List<RecentTag> recentTags);
    
    Single<Long> save(List<RecentTag> recentTags);
}
