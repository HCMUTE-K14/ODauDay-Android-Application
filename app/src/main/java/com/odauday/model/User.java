package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 2/27/18.
 */

public class User {
    
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    
    public User(String login, Integer id, String avatarUrl) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        User user = (User) o;
        
        if (id != user.id) {
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        return avatarUrl != null ? avatarUrl.equals(user.avatarUrl) : user.avatarUrl == null;
    }
    
    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "User{" +
               "login='" + login + '\'' +
               ", id=" + id +
               ", avatarUrl='" + avatarUrl + '\'' +
               '}';
    }
}
