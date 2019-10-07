package com.android.tech.trendingrepos.repofeature.di.module;

import androidx.room.Room;

import com.android.tech.trendingrepos.app.RepoApplication;
import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.app.utils.Constants;
import com.android.tech.trendingrepos.app.utils.schedulers.BaseSchedulerProvider;
import com.android.tech.trendingrepos.repofeature.model.localdata.AppRoomDb;
import com.android.tech.trendingrepos.repofeature.model.localdata.dao.TrendingRepoDao;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoLocalDataModule {

    @Provides
    @AppScoped
    AppRoomDb getAppRoomDb(RepoApplication context){
        return Room.databaseBuilder(context.getApplicationContext(),
                AppRoomDb.class, Constants.TRENDING_REPO_DB)
                .build();

    }

    @Provides
    @AppScoped
    TrendingRepoDao getTrendingRepoDao(AppRoomDb db){
        return db.repoDao();
    }

}
