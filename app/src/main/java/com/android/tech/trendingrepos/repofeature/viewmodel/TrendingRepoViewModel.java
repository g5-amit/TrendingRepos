package com.android.tech.trendingrepos.repofeature.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.android.tech.trendingrepos.repofeature.model.TrendingRepository;

import javax.inject.Inject;

public class TrendingRepoViewModel extends ViewModel {

    @NonNull
    private final TrendingRepository mRepository;


    @Inject
    public TrendingRepoViewModel(@NonNull TrendingRepository repository) {
        mRepository = repository;
    }
}
