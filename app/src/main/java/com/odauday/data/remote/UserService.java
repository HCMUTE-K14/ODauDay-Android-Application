package com.odauday.data.remote;

import com.odauday.model.User;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by infamouSs on 2/27/18.
 */

public interface UserService {
    
    public interface Public {
        //users?access_token=294239c172eb94a8f141bac7ba6cdb7ced298aa0
        @GET("/")
        Single<List<User>> test(@Query("since") int since);
    }
    
    public interface Protect {
        
        @GET("/")
        Single<List<User>> testProtect();
    }
}
