package com.android.tech.trendingrepos.app.di.component;

import com.android.tech.trendingrepos.app.RepoApplication;
import com.android.tech.trendingrepos.app.di.module.AppModule;
import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.repofeature.di.module.ActivityModule;
import com.android.tech.trendingrepos.repofeature.di.module.ViewModelModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * This is the root Dagger component.
 * {@link AndroidSupportInjectionModule}
 * is the module from Dagger.Android that helps with the generation
 * and location of subcomponents, which will be in our case, activities
 */
@AppScoped
@Component(modules = {AppModule.class,
        ActivityModule.class,
        ViewModelModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<RepoApplication>{

    // we can now do DaggerAppComponent.builder().application(this).build().inject(this),
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(RepoApplication application);

        AppComponent build();
    }
}


