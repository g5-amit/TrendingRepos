package com.android.tech.trendingrepos.repofeature.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.android.tech.trendingrepos.app.view.BaseFragment;
import com.android.tech.trendingrepos.app.di.viewmodel.AppViewModelFactory;
import com.android.tech.trendingrepos.repofeature.viewmodel.TrendingRepoViewModel;

import javax.inject.Inject;

public class TrendingRepoFragment extends BaseFragment {

    private TrendingRepoViewModel mViewModel;

    @Inject
    public AppViewModelFactory mViewModelFactory;

    @Inject
    public TrendingRepoFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TrendingRepoViewModel.class);

    }
}
