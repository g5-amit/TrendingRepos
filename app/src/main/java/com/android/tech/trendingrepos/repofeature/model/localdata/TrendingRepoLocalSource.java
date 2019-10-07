package com.android.tech.trendingrepos.repofeature.model.localdata;

import androidx.annotation.NonNull;

import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.app.utils.schedulers.BaseSchedulerProvider;
import com.android.tech.trendingrepos.repofeature.model.localdata.dao.TrendingRepoDao;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

import static dagger.internal.Preconditions.checkNotNull;


/**
 * Concrete implementation of the Local Data Source
 */
@AppScoped
public class TrendingRepoLocalSource implements ILocalTrendingRepo {
    private final TrendingRepoDao mTrendingRepoDao;

    private BaseSchedulerProvider mSchedulerProvider;

    @Inject
    public TrendingRepoLocalSource(@NonNull TrendingRepoDao gitUserDao,
                                   @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        checkNotNull(gitUserDao, "gitUserDao cannot be null");

        mTrendingRepoDao = gitUserDao;
        mSchedulerProvider = schedulerProvider;
    }


    /**
     * Items are retrieved from disk
     */
    @NonNull
    @Override
    public Single<List<TrendingRepoEntity>> getTrendingRepoList() {
        return mTrendingRepoDao.getTrendingRepoEntityList();
    }

    @NonNull
    @Override
    public Single<TrendingRepoEntity> getTrendingRepo(@NonNull String url) {
        return mTrendingRepoDao.getTrendingRepoById(url);
    }

    @Override
    public void saveTrendingRepoList(@NonNull List<TrendingRepoEntity> trendingRepoEntityList) {
        checkNotNull(trendingRepoEntityList);
        for (TrendingRepoEntity user : trendingRepoEntityList)
            saveTrendingRepo(user);
    }

    @Override
    public void saveTrendingRepo(@NonNull TrendingRepoEntity trendingRepoEntity) {
        checkNotNull(trendingRepoEntity);
        Completable.fromRunnable(() -> mTrendingRepoDao.insertTrendingRepoEntity(trendingRepoEntity))
                .subscribeOn(mSchedulerProvider.io()).subscribe();
    }

    @Override
    public void deleteTrendingRepoList() {
        Completable.fromRunnable(mTrendingRepoDao::deleteAllTrendingRepo)
                .subscribeOn(mSchedulerProvider.io()).subscribe();
    }

    @Override
    public void deleteTrendingRepo(@NonNull String url) {
        Completable.fromRunnable(() -> mTrendingRepoDao.deleteTrendingRepoById(url))
                .subscribeOn(mSchedulerProvider.io()).subscribe();
    }

}
