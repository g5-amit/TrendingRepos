package com.android.tech.trendingrepos.repofeature.model.localdata;

import androidx.annotation.NonNull;

import com.android.tech.trendingrepos.repofeature.model.ITrendingRepo;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.android.tech.trendingrepos.repofeature.model.remotedata.IRemoteTrendingRepo;

import java.util.List;

import io.reactivex.Single;


public interface ILocalTrendingRepo extends ITrendingRepo {

    @NonNull
    Single<TrendingRepoEntity> getTrendingRepo(@NonNull String repoUrl);

    void saveTrendingRepoList(@NonNull List<TrendingRepoEntity> trendingRepoEntityList);

    void saveTrendingRepo(@NonNull TrendingRepoEntity trendingRepoEntity);

    void deleteTrendingRepoList();

    void deleteTrendingRepo(@NonNull String repoUrl);
}
