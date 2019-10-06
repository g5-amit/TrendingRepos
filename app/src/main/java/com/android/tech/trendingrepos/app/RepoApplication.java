package com.android.tech.trendingrepos.app;

import com.android.tech.trendingrepos.app.di.component.AppComponent;
import com.android.tech.trendingrepos.app.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;


/**
 * We create a custom Application class that extends  {@link dagger.android.DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @AppScoped Component
 * We never have to call `component.inject(this)` as {@link dagger.android.DaggerApplication} will do that for us.
 */
public class RepoApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

}

