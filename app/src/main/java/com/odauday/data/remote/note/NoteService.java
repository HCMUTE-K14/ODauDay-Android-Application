package com.odauday.data.remote.note;

import static com.odauday.api.EndPoint.NOTE;

import com.odauday.data.remote.model.JsonResponse;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.model.Note;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by infamouSs on 5/17/18.
 */
public interface NoteService {
    
    
    @POST(NOTE)
    Single<JsonResponse<MessageResponse>> create(@Body Note note);
    
    @PUT(NOTE)
    Single<JsonResponse<MessageResponse>> update(@Body Note note);
    
    @DELETE(NOTE)
    Single<JsonResponse<MessageResponse>> delete(@Body Note note);
}
