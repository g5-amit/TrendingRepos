package com.android.tech.trendingrepos.app.di.module;

import android.content.Context;
import android.net.ConnectivityManager;

import com.android.tech.trendingrepos.app.RepoApplication;
import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.app.utils.network.DefaultOnlineChecker;
import com.android.tech.trendingrepos.app.utils.network.OnlineChecker;
import com.android.tech.trendingrepos.app.utils.schedulers.BaseSchedulerProvider;
import com.android.tech.trendingrepos.app.utils.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule {

    @Provides
    @AppScoped
    BaseSchedulerProvider getSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }

    @Provides
    @AppScoped
    ConnectivityManager getConnectivityManager(RepoApplication context){
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @AppScoped
    OnlineChecker getOnlineChecker(ConnectivityManager cm){
        return new DefaultOnlineChecker(cm);
    }


}
