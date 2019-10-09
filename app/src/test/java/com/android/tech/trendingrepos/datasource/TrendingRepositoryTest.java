package com.android.tech.trendingrepos.datasource;

import com.android.tech.trendingrepos.app.utils.network.OnlineChecker;
import com.android.tech.trendingrepos.repofeature.model.ITrendingRepo;
import com.android.tech.trendingrepos.repofeature.model.TrendingRepository;
import com.android.tech.trendingrepos.repofeature.model.localdata.ILocalTrendingRepo;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.android.tech.trendingrepos.repofeature.model.remotedata.IRemoteTrendingRepo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrendingRepositoryTest {

    private long testTime = System.currentTimeMillis();
    private long staleTime = 2 * 60 * 60 * 1000;//2 hrs

    private List<TrendingRepoEntity> REPOS_RECENT = new ArrayList<>();
    private List<TrendingRepoEntity> REPOS_STALE = new ArrayList<>();

    private TrendingRepository mTrendingRepository;

    private TestSubscriber<List<TrendingRepoEntity>> mRepoTestSubscriber;

    private TestSubscriber<TrendingRepoEntity> mRepoEntityTestSubscriber;


    @Mock
    private IRemoteTrendingRepo mRemoteDataSource;

    @Mock
    private ILocalTrendingRepo mLocalDataSource;

    @Mock
    private OnlineChecker mOnlineChecker;


    @Before
    public void setupReposRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        TrendingRepoEntity tempRepo1 = new TrendingRepoEntity();
        tempRepo1.setUrl("https://google.com");
        tempRepo1.setName("Google");
        tempRepo1.setAuthor("google");
        tempRepo1.setTimeStamp(testTime);

        TrendingRepoEntity tempRepo2 = new TrendingRepoEntity();
        tempRepo2.setUrl("https://fb.com");
        tempRepo2.setName("fb");
        tempRepo2.setAuthor("facebook");
        tempRepo2.setTimeStamp(testTime);

        TrendingRepoEntity tempRepo3 = new TrendingRepoEntity();
        tempRepo3.setUrl("https://googlestale.com");
        tempRepo3.setName("Google");
        tempRepo3.setAuthor("google");
        tempRepo3.setTimeStamp(testTime-staleTime);

        TrendingRepoEntity tempRepo4 = new TrendingRepoEntity();
        tempRepo4.setUrl("https://fbstale.com");
        tempRepo4.setName("fb");
        tempRepo4.setAuthor("facebook");
        tempRepo4.setTimeStamp(testTime-staleTime);

        REPOS_RECENT.add(tempRepo1);
        REPOS_RECENT.add(tempRepo2);

        REPOS_STALE.add(tempRepo3);
        REPOS_STALE.add(tempRepo4);

        // Get a reference to the class under test
        mTrendingRepository = new TrendingRepository
                (mRemoteDataSource, mLocalDataSource, mOnlineChecker);

        mRepoTestSubscriber = new TestSubscriber<>();
    }

    /**
     * Offline Test scenario states:
     * As the disk has up-to-date items, upon querying the repository with no internet connection,
     * the Local Data Source should be accessed and correct items should be retrieved
     */
    @Test
    public void getReposOffline_requestsReposFromLocalDataSource() {

        // the local data source has up-to-date data available
        new ArrangeBuilder()
                .withReposAvailable(mLocalDataSource, REPOS_RECENT);

        // establish a fake internet connection status
        when(mOnlineChecker.isOnline()).thenReturn(false);

        // When repos are requested from the repos repository
        mTrendingRepository.getTrendingRepoList(false).toFlowable().subscribe(mRepoTestSubscriber);

        // Then repos are loaded from the local data source
        verify(mLocalDataSource).getTrendingRepoList(false);
        mRepoTestSubscriber.assertValue(REPOS_RECENT);
    }

    /**
     * Online Test scenario states:
     * As the disk has up-to-date items, upon querying the repository with active internet connection,
     * the Local Data Source should be accessed and correct items should be retrieved
     */
    @Test
    public void getReposOnline_requestsReposFromLocalDataSource_upToDateLocal() {

        // the local data source has up-to-date data available
        new ArrangeBuilder()
                .withReposAvailable(mLocalDataSource, REPOS_RECENT);

        // establish a fake internet connection status
        when(mOnlineChecker.isOnline()).thenReturn(true);

        // When repos are requested from the repos repository
        mTrendingRepository.getTrendingRepoList(false).toFlowable().subscribe(mRepoTestSubscriber);

        // Then repos are loaded from the local data source
        verify(mLocalDataSource).getTrendingRepoList(false);
        mRepoTestSubscriber.assertValue(REPOS_RECENT);
    }

     /* Online Test scenario states:
            * As the disk has stale items, upon querying the repository with active internet connection,
            * the Remote Data Source should be accessed and correct items should be retrieved
     */
    @Test
    public void getReposOnline_requestsReposFromRemoteDataSource_staleLocal() {

        // the remote data source has fresh data available
        new ArrangeBuilder()
                .withReposAvailable(mRemoteDataSource, REPOS_RECENT);

        // the local data source has stale data available
        new ArrangeBuilder()
                .withReposAvailable(mLocalDataSource, REPOS_STALE);

        // establish a fake internet connection status
        when(mOnlineChecker.isOnline()).thenReturn(true);

        // When repos are requested from the repos repository
        mTrendingRepository.getTrendingRepoList(false).toFlowable().subscribe(mRepoTestSubscriber);

        // Both sources should be queried, yet the local source has stale items
        // which triggers the call to the remote source
        verify(mLocalDataSource).getTrendingRepoList(false);
        verify(mRemoteDataSource).getTrendingRepoList(false);

        mRepoTestSubscriber.assertValue(REPOS_RECENT);
    }

    /**
     * Online Test scenario states:
     * As the disk has no items, upon querying the repository with active internet connection,
     * the Remote Data Source should be accessed and correct items should be retrieved
     */
    @Test
    public void getReposOnline_requestsReposFromRemoteDataSource_emptyLocal() {

        // the remote data source has fresh data available
        new ArrangeBuilder()
                .withReposAvailable(mRemoteDataSource, REPOS_RECENT);

        // the local data source has stale data available
        new ArrangeBuilder()
                .withReposNotAvailable(mLocalDataSource);

        // establish a fake internet connection status
        when(mOnlineChecker.isOnline()).thenReturn(true);

        // When repos are requested from the repos repository
        mTrendingRepository.getTrendingRepoList(false).toFlowable().subscribe(mRepoTestSubscriber);

        // Both sources should be queried, yet the local source has no items
        // which triggers the call to the remote source
        verify(mLocalDataSource).getTrendingRepoList(false);
        verify(mRemoteDataSource).getTrendingRepoList(false);

        mRepoTestSubscriber.assertValue(REPOS_RECENT);
    }


    /**
     * Test scenario states:
     * Upon get command , Local Data Source should retrieve the item locally
     */
    @Test
    public void getTrendingRepoFromLocal(){
        TrendingRepoEntity tempRepo = new TrendingRepoEntity();
        tempRepo.setUrl("https://google.com");
        tempRepo.setName("Google");
        tempRepo.setAuthor("google");
        tempRepo.setTimeStamp(testTime);

        new TrendingRepositoryTest.ArrangeBuilder().withRepoById(mLocalDataSource, tempRepo);

        mTrendingRepository.getTrendingRepo(tempRepo.getUrl()).toFlowable().subscribe(mRepoEntityTestSubscriber);

        // upon get command, check if only local data source is being called
        verify(mLocalDataSource).getTrendingRepo(tempRepo.getUrl());
        mRepoEntityTestSubscriber.assertValue(tempRepo);
    }


    /**
     * Test scenario states:
     * Upon save command ,  Local Data Source should save
     *  the corresponding items taken as parameter
     */
    @Test
    public void saveTrendingRepoList(){
        mTrendingRepository.saveTrendingRepoList(REPOS_RECENT);

        // upon save command, check if Local data sources are being called
        verify(mLocalDataSource).saveTrendingRepoList(REPOS_RECENT);
    }

    /**
     * Test scenario states:
     * Upon save command , Local Data Source should save
     *  the corresponding item taken as parameter
     */
    @Test
    public void saveTrendingRepo(){
        mTrendingRepository.saveTrendingRepo(REPOS_RECENT.get(0));

        // upon save command, check if Local data sources are being called
        verify(mLocalDataSource).saveTrendingRepo(REPOS_RECENT.get(0));
    }

    /**
     * Test scenario states:
     * Upon delete command , Local Data Source should delete
     *  all items
     */
    @Test
    public void deleteTrendingRepoList(){
        mTrendingRepository.deleteTrendingRepoList();

        // upon save command, check if Local data sources are being called
        verify(mLocalDataSource).deleteTrendingRepoList();
    }

    /**
     * Test scenario states:
     * Upon delete command ,  Local Data Source should delete
     *  the corresponding item
     */
    @Test
    public void deleteTrendingRepo(){
        mTrendingRepository.deleteTrendingRepo("https://google.com");

        // upon save command, check if Local data sources are being called
        verify(mLocalDataSource).deleteTrendingRepo("https://google.com");
    }



    class ArrangeBuilder {

        ArrangeBuilder withReposNotAvailable(ITrendingRepo dataSource) {
            when(dataSource.getTrendingRepoList(false)).thenReturn(Single.just(Collections.emptyList()));
            return this;
        }

        ArrangeBuilder withReposAvailable(ITrendingRepo dataSource, List<TrendingRepoEntity> repos) {
            // don't allow the data sources to complete.
            when(dataSource.getTrendingRepoList(false)).thenReturn(Single.just(repos));
            return this;
        }

        ArrangeBuilder withRepoById(ILocalTrendingRepo dataSource,TrendingRepoEntity repo) {
            // don't allow the data sources to complete.
            when(dataSource.getTrendingRepo(repo.getUrl())).thenReturn(Single.just(repo));
            return this;
        }
    }
}
