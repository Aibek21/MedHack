package kz.kbtu.medhack.utils.dagger.modules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.kbtu.medhack.api.Api;
import kz.kbtu.medhack.api.RequestHandler;
import kz.kbtu.medhack.login.LoginActivity;
import kz.kbtu.medhack.models.User;
import kz.kbtu.medhack.utils.factories.ResponseConverterFactory;
import kz.kbtu.medhack.utils.helps.Helper;
import kz.kbtu.medhack.utils.helps.Keys;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aibekkuralbaev on 05.10.16.
 */

@Module
public class NetModule {


    public NetModule() {
    }


//    @Provides
//    @Singleton
//    Cache provideOkHttpCache(Application application) {
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        Cache cache = new Cache(application.getCacheDir(), cacheSize);
//        return cache;
//    }

    @Provides
    String provideBaseUrl() {
        return Keys.BASE_URL;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        gsonBuilder.registerTypeAdapterFactory(AutoValueTypeAdapterFactory.create());
        return gsonBuilder.create();
    }


    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    Interceptor provideInterceptor(Gson gson, @Nullable User user, Context context, SharedPreferences sharedPreferences) {
        return chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder;
            Request request;
            Response response;

            Log.d("NetModule", "provideInterceptor: " + user);
            if (user != null) {
                requestBuilder = original.newBuilder()
                        .header("Authorization", "Token token=" + user.getAuthenticationToken())
                        .method(original.method(), original.body());

                request = requestBuilder.build();
                response = chain.proceed(request);

            } else
                response = chain.proceed(original);

            boolean unauthorized = (response.code() == 401);
            if (unauthorized) {
                new Helper().setUser(sharedPreferences, null);
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//                ErrorResponse body = gson.fromJson(response.body().string(), ErrorResponse.class);
//                throw new AuthorizeException(body.error());
            }
            return response;
        };
    }

    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Interceptor interceptor) {
        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().
                readTimeout(60, TimeUnit.SECONDS).
                connectTimeout(60, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(interceptor);
        okHttpClient.addInterceptor(loggingInterceptor);
        return okHttpClient.build();
    }




    @Provides
    Retrofit provideRetrofit(String baseUrl, Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(ResponseConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }


    @Provides
    RequestHandler provideRequestHandler(Retrofit retrofit) {
        return new RequestHandler(retrofit.create(Api.class));
    }
}
