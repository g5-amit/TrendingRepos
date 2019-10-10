package com.android.tech.trendingrepos.repofeature.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.android.tech.trendingrepos.app.utils.SortUtils;
import com.android.tech.trendingrepos.repofeature.model.TrendingRepository;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.android.tech.trendingrepos.repofeature.view.GitUiModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class TrendingRepoViewModel extends ViewModel {

    @NonNull
    private final TrendingRepository mRepository;


    @Inject
    public TrendingRepoViewModel(@NonNull TrendingRepository repository) {
        mRepository = repository;
    }

    public Single<GitUiModel> getTrendingRepo(boolean isForcedCall) {
        return mRepository.getTrendingRepoList(isForcedCall)
                .map(this::constructUiData);
    }

    private GitUiModel constructUiData(List<TrendingRepoEntity> trendingRepoEntityList){
        SortUtils.sortByStars(trendingRepoEntityList);
        GitUiModel uiModel = new GitUiModel(trendingRepoEntityList);
        return uiModel;
    }

}
