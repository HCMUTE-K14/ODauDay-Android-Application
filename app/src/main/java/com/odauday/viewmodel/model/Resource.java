package com.odauday.viewmodel.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by infamouSs on 2/28/18.
 */

public class Resource<T> {
    
    @NonNull
    public final String task;
    
    @NonNull
    public final Status status;
    
    @Nullable
    public final String message;
    
    @Nullable
    public final T data;
    
    public Resource(@NonNull Status status, @NonNull String task, @Nullable T data,
              @Nullable String message) {
        this.status = status;
        this.data = data;
        this.task = task;
        this.message = message;
    }
    
    public static <T> Resource<T> success(String task, @Nullable T data) {
        return new Resource<>(Status.SUCCESS, task, data, null);
    }
    
    public static <T> Resource<T> error(String task, String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, task, data, msg);
    }
    
    public static Resource error(String task, Throwable ex) {
        return new Resource<>(Status.ERROR, task, ex, ex.getMessage());
    }
    
    public static Resource error(String task, Exception ex) {
        return new Resource<>(Status.ERROR, task, ex, ex.getMessage());
    }
    
    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, "loading", data, null);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Resource<?> resource = (Resource<?>) o;
        
        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }
    
    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Resource{" +
               "status=" + status +
               ", message='" + message + '\'' +
               ", data=" + data +
               '}';
    }
}