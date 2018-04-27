package com.odauday.api;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odauday.config.AppConfig;
import com.odauday.exception.NetworkException;
import com.odauday.utils.NetworkUtils;
import com.odauday.utils.TextUtils;
import java.io.File;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by infamouSs on 2/27/18.
 */

public class APIHelper {
    
    public static final String TAG = APIHelper.class.getSimpleName();
    
    public static final String ACCESS_TOKEN = "X-Access-Token";
    
    public static final String USER_ID = "X-USER-ID";
    
    public static final String API_KEY = "X-API-KEY";
    
    private final Context mContext;
    
    public APIHelper(Context context) {
        this.mContext = context.getApplicationContext();
    }
    
    public Gson createGson(GsonBuilder gsonBuilder) {
        return gsonBuilder.create();
    }
    
    public Gson createDefaultGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return createGson(gsonBuilder);
    }
    
    public Interceptor createInterceptor(AbstractApiHeader applicationHeader) {
        return chain -> {
            try {
                Request modifiedRequest = chain.request().newBuilder()
                    .headers(applicationHeader.getHeader())
                    .build();
                return chain.proceed(modifiedRequest);
            } catch (SocketTimeoutException e) {
                throw new NetworkException("Cannot connect to Service");
            }
        };
    }
    
    public Interceptor createDefaultInterceptor() {
        return chain -> {
            try {
                Request modifiedRequest = chain.request().newBuilder()
                    .build();
                return chain.proceed(modifiedRequest);
            } catch (SocketTimeoutException e) {
                throw new NetworkException("Cannot connect to Service");
            }
        };
    }
    
    public Cache createCache(Context context) {
        return createDefaultCache(context);
    }
    
    public Cache createDefaultCache(Context context) {
        File cacheDir = new File(context.getCacheDir().getAbsolutePath(), "/cache/");
        if (cacheDir.mkdirs() || cacheDir.isDirectory()) {
            return new Cache(cacheDir, 1024 * 1024 * 10);
        }
        return null;
    }
    
    public OkHttpClient createClient(Cache cache, Interceptor interceptor) {
        
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
            message -> Timber.tag("Network").d(message))
            .setLevel(Level.BODY);
        
        return new OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .readTimeout(AppConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(AppConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build();
    }
    
    public Retrofit createRetrofit(String baseURL, @NonNull OkHttpClient client, Gson gson) {
        if (TextUtils.isEmpty(baseURL)) {
            baseURL = EndPoint.BASE_URL;
        }
        
        if (gson == null) {
            gson = createDefaultGson();
        }
        return new Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(SingleCallAdapter.create())
            .build();
    }
    
    public <T> T createService(final Class<T> clazz, String baseURL, OkHttpClient client,
        Gson gson) {
        if (!NetworkUtils.isNetworkAvailable(mContext)) {
            return null;
        }
        try {
            Retrofit retrofit = createRetrofit(baseURL, client, gson);
            return retrofit.create(clazz);
        } catch (Exception e) {
            throw new NetworkException("Cannot connect to Service");
        }
    }
}