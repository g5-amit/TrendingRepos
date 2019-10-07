package com.android.tech.trendingrepos.repofeature.view.activities;

import android.os.Bundle;

import com.android.tech.trendingrepos.R;
import com.android.tech.trendingrepos.app.view.BaseActivity;
import com.android.tech.trendingrepos.repofeature.view.fragments.TrendingRepoFragment;

import javax.inject.Inject;

public class TrendingRepoActivity extends BaseActivity {

    @Inject
    TrendingRepoFragment mInjectedFragment;

    @Override
    public int getLayoutRes() {
        return R.layout.trending_repo_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        // Set up fragment
        TrendingRepoFragment fragment = (TrendingRepoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = mInjectedFragment;
            replaceFragment(fragment);

        }
    }
}


