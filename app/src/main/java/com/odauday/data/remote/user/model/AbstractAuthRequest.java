package com.odauday.data.remote.user.model;

import com.odauday.data.remote.BaseRequest;

/**
 * Created by infamouSs on 3/23/18.
 */

public abstract class AbstractAuthRequest implements BaseRequest {
    
    private final LoginType type;
    
    AbstractAuthRequest(LoginType type) {
        this.type = type;
    }
    
    public LoginType getType() {
        return type;
    }
}
