package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.remote.TagService;
import com.odauday.data.remote.TagService.Protect;
import com.odauday.data.remote.TagService.Public;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.exception.TagException;
import com.odauday.model.Tag;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 3/30/2018.
 */

public class TagRepository implements Repository {
    
    private final TagService.Public mPublicTagService;
    private final TagService.Protect mProtectTagService;
    
    
    private final SchedulersExecutor mSchedulersExecutor;
    
    @Inject
    public TagRepository(Public publicTagService,
        Protect protectTagService, SchedulersExecutor schedulersExecutor) {
        mPublicTagService = publicTagService;
        mProtectTagService = protectTagService;
        mSchedulersExecutor = schedulersExecutor;
    }
    
    public Single<List<Tag>> getAllTag() {
        Single<JsonResponse<List<Tag>>> result = mPublicTagService.getAllTag();
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new TagException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof TagException) {
                        throw ex;
                    }
                    throw new TagException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> createTag(Tag tag) {
        Single<JsonResponse<MessageResponse>> result = mProtectTagService.createTag(tag);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new TagException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof TagException) {
                        throw ex;
                    }
                    throw new TagException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> updateTag(Tag tag) {
        Single<JsonResponse<MessageResponse>> result = mProtectTagService.updateTag(tag);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new TagException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof TagException) {
                        throw ex;
                    }
                    throw new TagException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Single<MessageResponse> deleteTag(String id) {
        Single<JsonResponse<MessageResponse>> result = mProtectTagService.deleteTag(id);
        return result
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    }
                    throw new TagException(response.getErrors());
                    
                } catch (Exception ex) {
                    if (ex instanceof TagException) {
                        throw ex;
                    }
                    throw new TagException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
    
    public Public getPublicTagService() {
        return mPublicTagService;
    }
    
    public Protect getProtectTagService() {
        return mProtectTagService;
    }
    
    public SchedulersExecutor getSchedulersExecutor() {
        return mSchedulersExecutor;
    }
    
}
