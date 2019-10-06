package com.android.tech.trendingrepos.repofeature.di.module;

import com.android.tech.trendingrepos.app.di.scopes.ActivityScoped;
import com.android.tech.trendingrepos.repofeature.view.activities.TrendingRepoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {FragmentModule.class})
    abstract TrendingRepoActivity getTrendingRepoActivity();
}
