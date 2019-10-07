package com.android.tech.trendingrepos.repofeature.di.module;

import com.android.tech.trendingrepos.BuildConfig;
import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.repofeature.model.remotedata.RemoteApiService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RepoRemoteDataModule {

    @Provides
    @AppScoped
    RemoteApiService getRemoteApiService(Retrofit retrofit){
        return retrofit.create(RemoteApiService.class);

    }

    @Provides
    @AppScoped
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.API_BASE_URL)
                .build();
    }

    @Provides
    @AppScoped
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }
}
