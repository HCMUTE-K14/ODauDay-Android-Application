package com.odauday.api;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odauday.BuildConfig;
import com.odauday.R;
import com.odauday.config.AppConfig;
import com.odauday.data.local.cache.UserPreferenceHelper;
import com.odauday.exception.NetworkException;
import com.odauday.utils.NetworkUtils;
import com.odauday.utils.TextUtils;
import java.io.File;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.HttpUrl;
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
    
    @Inject
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
    
    public Interceptor createProtectInterceptor(UserPreferenceHelper userPreferenceHelper) {
        return chain -> {
            try {
                Headers.Builder builder = new Headers.Builder();
                
                builder.add(APIHelper.ACCESS_TOKEN, userPreferenceHelper.getAccessToken());
                builder.add(APIHelper.API_KEY, BuildConfig.API_KEY);
                builder.add(APIHelper.USER_ID, userPreferenceHelper.getUserId());
                
                Request modifiedRequest = chain.request().newBuilder()
                    .headers(builder.build())
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
                throw new NetworkException(
                    mContext.getString(R.string.message_cannot_connect_to_service));
            }
        };
    }
    
    public Interceptor createLanguageSupportInterceptor(String lang) {
        return chain -> {
            try {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                
                HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("lang", lang)
                    .build();
                
                Request.Builder requestBuilder = original.newBuilder()
                    .url(url);
                
                Request request = requestBuilder.build();
                return chain.proceed(request);
            } catch (Exception ex) {
                throw new NetworkException(
                    mContext.getString(R.string.message_cannot_connect_to_service));
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
    
    public OkHttpClient createClient(Cache cache, Interceptor interceptor,
        Interceptor languageInterceptor) {
        
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
            message -> Timber.tag("Network").d(message))
            .setLevel(Level.BODY);
        
        return new OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .addInterceptor(languageInterceptor)
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
        
        //        if (!NetworkUtils.isNetworkAvailable(mContext)) {
        //            Timber.tag("Network").d(mContext.getString(R.string.message_no_internet_access));
        //            return null;
        //        }
        return new Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(SingleCallAdapter.create())
            .build();
    }
    
    @Deprecated
    public <T> T createService(final Class<T> clazz, String baseURL, OkHttpClient client,
        Gson gson) {
        if (!NetworkUtils.isNetworkAvailable(mContext)) {
            return null;
        }
        try {
            Retrofit retrofit = createRetrofit(baseURL, client, gson);
            return retrofit.create(clazz);
        } catch (Exception e) {
            throw new NetworkException(
                mContext.getString(R.string.message_cannot_connect_to_service));
        }
    }
}