package com.android.tech.trendingrepos.repofeature.di.module;

import com.android.tech.trendingrepos.BuildConfig;
import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.repofeature.model.remotedata.RemoteApiService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RepoRemoteDataModule {

    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 120;
    private static final int CONNECTION_TIMEOUT = 30;

    @Provides
    @AppScoped
    RemoteApiService getRemoteApiService(Retrofit retrofit){
        return retrofit.create(RemoteApiService.class);

    }

    @Provides
    @AppScoped
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.API_BASE_URL)
                .build();
    }


    @Provides
    @AppScoped
    OkHttpClient getOkHttpClient(HttpLoggingInterceptor logging) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .followRedirects(true)
                    .followSslRedirects(true)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(logging);
        return clientBuilder.build();
    }

    @Provides
    @AppScoped
    HttpLoggingInterceptor getLogging(){
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @Provides
    @AppScoped
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }
}
