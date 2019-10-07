package com.android.tech.trendingrepos.repofeature.model;

import androidx.annotation.NonNull;

import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;

import java.util.List;

import io.reactivex.Single;

public interface ITrendingRepo {

    @NonNull
    Single<List<TrendingRepoEntity>> getTrendingRepoList();
}
