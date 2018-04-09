package com.odauday.data.remote.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 2/27/18.
 */

public class FacebookAuthRequest extends AbstractAuthRequest {
    
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    
    
    @SerializedName("email")
    @Expose
    private String email;
    
    @SerializedName("display_name")
    @Expose
    private String displayName;
    
    @SerializedName("facebook_access_token")
    @Expose
    private String accessToken;
    
    public FacebookAuthRequest() {
        super(LoginType.FACEBOOK);
    }
    
    public FacebookAuthRequest(String facebookId, String email,
              String displayName, String accessToken) {
        super(LoginType.FACEBOOK);
        this.facebookId = facebookId;
        this.email = email;
        this.displayName = displayName;
        this.accessToken = accessToken;
    }
    
    public String getFacebookId() {
        return facebookId;
    }
    
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        FacebookAuthRequest that = (FacebookAuthRequest) o;
        
        if (facebookId != null ? !facebookId.equals(that.facebookId) : that.facebookId != null) {
            return false;
        }
        return email != null ? email.equals(that.email) : that.email == null;
    }
    
    @Override
    public int hashCode() {
        int result = facebookId != null ? facebookId.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "FacebookAuthRequest{" +
               "facebookId='" + facebookId + '\'' +
               ", email='" + email + '\'' +
               ", displayName='" + displayName + '\'' +
               ", accessToken='" + accessToken + '\'' +
               '}';
    }
}
