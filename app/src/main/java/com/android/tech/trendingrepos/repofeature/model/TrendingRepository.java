package com.android.tech.trendingrepos.repofeature.model;

import androidx.annotation.NonNull;

import com.android.tech.trendingrepos.app.di.scopes.AppScoped;
import com.android.tech.trendingrepos.app.di.scopes.Local;
import com.android.tech.trendingrepos.app.di.scopes.Remote;
import com.android.tech.trendingrepos.app.utils.network.OnlineChecker;
import com.android.tech.trendingrepos.repofeature.model.localdata.ILocalTrendingRepo;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.android.tech.trendingrepos.repofeature.model.remotedata.IRemoteTrendingRepo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

import static dagger.internal.Preconditions.checkNotNull;

@AppScoped
public class TrendingRepository implements ILocalTrendingRepo {

    private final IRemoteTrendingRepo mRemoteDataSource;

    private final ILocalTrendingRepo mLocalDataSource;

    private final OnlineChecker mOnlineChecker;

    /**
     * Dagger allows us to have a single instance of the repository throughout the app
     *
     * @param remoteDataSource the backend data source (Remote Source)
     * @param localDataSource  the device storage data source (Local Source)
     */
    @Inject
    public TrendingRepository(@Remote IRemoteTrendingRepo remoteDataSource,
                              @Local ILocalTrendingRepo localDataSource,
                              OnlineChecker onlineChecker) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
        mOnlineChecker = onlineChecker;
    }

    /**
     * The retrieval logic sets the Local Source as the primary source
     * In case of an active internet connection and the absence of Local database
     * or if it contains stale data, the Remote Source is queried and the Local one is refreshed
     */

    @NonNull
    @Override
    public Single<List<TrendingRepoEntity>> getTrendingRepoList(boolean isForcedCall) {
        return mLocalDataSource.getTrendingRepoList(isForcedCall)
                .flatMap(data -> {
                    if (data.isEmpty() || isStale(data) || isForcedCall) {
                        if (mOnlineChecker.isOnline()) {
                            return getFreshTrendingRepoList(isForcedCall);
                        } else {
                            if (data.isEmpty()) {
                                return null;
                            }
                        }
                    }
                    return Single.just(data);
                });
    }

    @NonNull
    @Override
    public Single<TrendingRepoEntity> getTrendingRepo(@NonNull String url) {
        checkNotNull(url);
        return mLocalDataSource.getTrendingRepo(url);
    }

    @Override
    public void saveTrendingRepoList(@NonNull List<TrendingRepoEntity> trendingRepoEntityList) {
        checkNotNull(trendingRepoEntityList);
        mLocalDataSource.saveTrendingRepoList(trendingRepoEntityList);
    }

    @Override
    public void saveTrendingRepo(@NonNull TrendingRepoEntity trendingRepoEntity) {
        checkNotNull(trendingRepoEntity);
        mLocalDataSource.saveTrendingRepo(trendingRepoEntity);
    }

    @Override
    public void deleteTrendingRepoList() {
        mLocalDataSource.deleteTrendingRepoList();
    }

    @Override
    public void deleteTrendingRepo(@NonNull String url) {
        mLocalDataSource.deleteTrendingRepo(url);
    }

    /**
     * Helper methods, should be encapsulated
     */

    private boolean isStale(List<TrendingRepoEntity> data) {
        // it is enough for 1 item to be stale
        return !data.get(0).isUpToDate();
    }

    /**
     * Contains data refreshing logic
     * Both sources are emptied, then new items are retrieved from querying the Remote Source
     * and finally, sources are replenished
     */
    public Single<List<TrendingRepoEntity>> getFreshTrendingRepoList(boolean isForcedCall) {
        return mRemoteDataSource.getTrendingRepoList(isForcedCall).
                doOnSuccess(this::saveFreshTrendingRepoList);
    }

    private void saveFreshTrendingRepoList(@NonNull List<TrendingRepoEntity> trendingRepoEntityList) {
        deleteTrendingRepoList();
        saveTrendingRepoList(trendingRepoEntityList);
    }

}

