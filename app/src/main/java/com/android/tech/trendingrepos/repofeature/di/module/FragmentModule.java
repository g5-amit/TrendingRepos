package com.android.tech.trendingrepos.repofeature.di.module;

import com.android.tech.trendingrepos.app.di.scopes.FragmentScoped;
import com.android.tech.trendingrepos.repofeature.view.fragments.TrendingRepoFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {FragmentModule.FragmentAbstractModule.class})
public class FragmentModule {

    @Module
    public interface FragmentAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        TrendingRepoFragment getTrendingRepoFragment();
    }
}
