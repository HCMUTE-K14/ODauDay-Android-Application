package com.odauday.api;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.odauday.data.remote.model.JsonResponse;
import com.odauday.exception.RetrofitException;
import io.reactivex.Single;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.CallAdapter.Factory;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/25/18.
 */

@SuppressWarnings("unchecked")
public class SingleCallAdapter extends Factory {

    private final RxJava2CallAdapterFactory original;

    private SingleCallAdapter() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new SingleCallAdapter();
    }

    @Override
    public CallAdapter<?, ?> get(
              @NonNull Type returnType,
              @NonNull Annotation[] annotations,
              @NonNull Retrofit retrofit) {
        return new RxCallAdapterWrapper(
                  retrofit,
                  original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Object, Single<?>> {

        private final Retrofit retrofit;
        private final CallAdapter<Object, Single> wrapped;

        RxCallAdapterWrapper(Retrofit retrofit, CallAdapter wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Single<?> adapt(@NonNull Call<Object> call) {
            return wrapped.adapt(call)
                      .flatMap(o -> {
                          try {
                              return Single.just(o);
                          } catch (Exception ex) {
                              return Single.error(ex);
                          }
                      })
                      .onErrorResumeNext(throwable -> {
                          if (throwable instanceof HttpException) {
                              HttpException httpException = (HttpException) throwable;
                              Response response = httpException.response();
                              JsonResponse json = new Gson()
                                        .fromJson(response.errorBody().string(),
                                                  JsonResponse.class);
                              return Single.just(json);
                          }
                          if (throwable instanceof IOException) {
                              Timber.tag("ERROR").e(((IOException) throwable).getMessage());
                              return Single.error(RetrofitException
                                        .networkError((IOException) throwable));
                          }
                          Timber.tag("ERROR").e(((Throwable) throwable).getMessage());
                          return Single.error(RetrofitException
                                    .unexpectedError((Exception) throwable));
                      });
        }


    }
}
