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
                .map(this::parseTrendingRepoData);

    }

    private List<TrendingRepoEntity> parseTrendingRepoData(List<GitHubRepo> gitHubRepoList) {
        List<TrendingRepoEntity> trendingRepoEntityList = new ArrayList<>();

        for(GitHubRepo gitRepo: gitHubRepoList){
            TrendingRepoEntity trendingRepoEntity = new TrendingRepoEntity();
            trendingRepoEntity.setAuthor(gitRepo.getAuthor());
            trendingRepoEntity.setAvatar(gitRepo.getAvatar());
            trendingRepoEntity.setBuiltBy(gitRepo.getBuiltBy());
            trendingRepoEntity.setCurrentPeriodStars(gitRepo.getCurrentPeriodStars());
            trendingRepoEntity.setDescription(gitRepo.getDescription());
            trendingRepoEntity.setForks(gitRepo.getForks());
            trendingRepoEntity.setLanguage(gitRepo.getLanguage());
            trendingRepoEntity.setLanguageColor(gitRepo.getLanguageColor());
            trendingRepoEntity.setStars(gitRepo.getStars());
            trendingRepoEntity.setName(gitRepo.getName());
            trendingRepoEntity.setUrl(gitRepo.getUrl());
            trendingRepoEntity.setTimeStamp(System.currentTimeMillis());

            trendingRepoEntityList.add(trendingRepoEntity);
        }

        return trendingRepoEntityList;
    }


}
