package com.android.tech.trendingrepos.repofeature.model.remotedata;

import androidx.annotation.NonNull;

import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.android.tech.trendingrepos.repofeature.model.pojo.GitHubRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Remote Data Source implementation
 */
@AppScoped
public class TrendingRepoRemoteSource implements IRemoteTrendingRepo {
    @NonNull
    private RemoteApiService mApiService;

    @Inject
    public TrendingRepoRemoteSource(@NonNull RemoteApiService apiService) {
        mApiService = apiService;
    }

    /**
     * Fresh items are retrieved from Remote API
     */
    @NonNull
    @Override
    public Single<List<TrendingRepoEntity>> getTrendingRepoList() {
        return mApiService.getRemoteTrendingRepos()
                .map(this::parseWeatherData);

    }

    private List<TrendingRepoEntity> parseWeatherData(GitHubRepo gitHubRepo) {
        return null;
    }


}
