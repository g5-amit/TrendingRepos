package com.android.tech.trendingrepos.repofeature.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.app.di.viewmodel.AppViewModelFactory;
import com.android.tech.trendingrepos.app.di.viewmodel.ViewModelKey;
import com.android.tech.trendingrepos.repofeature.viewmodel.TrendingRepoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrendingRepoViewModel.class)
    abstract ViewModel bindQuakesViewModel(TrendingRepoViewModel ratesViewModel);

    @Binds
    @AppScoped
    abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}
