package com.odauday.data;

import com.odauday.SchedulersExecutor;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.note.NoteService;
import com.odauday.exception.NoteException;
import com.odauday.model.Note;
import com.odauday.utils.TextUtils;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/17/18.
 */
public class NoteRepository implements Repository {
    
    private final NoteService mNoteService;
    private final SchedulersExecutor mSchedulersExecutor;
    private final PreferencesHelper mPreferencesHelper;
    
    @Inject
    public NoteRepository(NoteService noteService,
        PreferencesHelper preferencesHelper,
        SchedulersExecutor schedulersExecutor) {
        this.mNoteService = noteService;
        this.mPreferencesHelper = preferencesHelper;
        this.mSchedulersExecutor = schedulersExecutor;
    }
    
    
    public Single<MessageResponse> create(Note note) {
        return callApi(mNoteService.create(checkUserId(note)));
    }
    
    public Single<MessageResponse> update(Note note) {
        return callApi(mNoteService.update(checkUserId(note)));
    }
    
    public Single<MessageResponse> delete(Note note) {
        return callApi(mNoteService.delete(checkUserId(note)));
    }
    
    private Note checkUserId(Note note) {
        if (TextUtils.isEmpty(note.getUserId())) {
            note.setUserId(mPreferencesHelper.get(PrefKey.USER_ID, ""));
            return note;
        }
        
        return note;
    }
    
    private Single<MessageResponse> callApi(Single<JsonResponse<MessageResponse>> data) {
        return data
            .map(response -> {
                try {
                    if (response.isSuccess()) {
                        return response.getData();
                    } else {
                        throw new NoteException(response.getErrors());
                    }
                } catch (Exception ex) {
                    if (ex instanceof NoteException) {
                        throw ex;
                    }
                    throw new NoteException(ex.getMessage());
                }
            })
            .subscribeOn(mSchedulersExecutor.io())
            .observeOn(mSchedulersExecutor.ui());
    }
}
