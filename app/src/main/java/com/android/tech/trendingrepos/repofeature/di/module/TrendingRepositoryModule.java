package com.android.tech.trendingrepos.repofeature.di.module;

import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.app.di.scopes.Local;
import com.android.tech.trendingrepos.app.di.scopes.Remote;
import com.android.tech.trendingrepos.app.utils.schedulers.BaseSchedulerProvider;
import com.android.tech.trendingrepos.repofeature.model.localdata.ILocalTrendingRepo;
import com.android.tech.trendingrepos.repofeature.model.localdata.TrendingRepoLocalSource;
import com.android.tech.trendingrepos.repofeature.model.localdata.dao.TrendingRepoDao;
import com.android.tech.trendingrepos.repofeature.model.remotedata.IRemoteTrendingRepo;
import com.android.tech.trendingrepos.repofeature.model.remotedata.RemoteApiService;
import com.android.tech.trendingrepos.repofeature.model.remotedata.TrendingRepoRemoteSource;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RepoLocalDataModule.class, RepoRemoteDataModule.class})
public class TrendingRepositoryModule {

    @Provides
    @Local
    @AppScoped
    ILocalTrendingRepo provideTrendingRepoLocalSource(TrendingRepoDao trendingRepoDao,
                                                  BaseSchedulerProvider schedulerProvider) {
        return new TrendingRepoLocalSource(trendingRepoDao, schedulerProvider);
    }

    @Provides
    @Remote
    @AppScoped
    IRemoteTrendingRepo provideTrendingRepoRemoteSource(RemoteApiService apiService) {
        return new TrendingRepoRemoteSource(apiService);
    }
}
