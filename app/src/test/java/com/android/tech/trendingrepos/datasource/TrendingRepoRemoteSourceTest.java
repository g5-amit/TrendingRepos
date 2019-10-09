package com.android.tech.trendingrepos.datasource;

import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.android.tech.trendingrepos.repofeature.model.pojo.GitHubRepo;
import com.android.tech.trendingrepos.repofeature.model.remotedata.IRemoteTrendingRepo;
import com.android.tech.trendingrepos.repofeature.model.remotedata.RemoteApiService;
import com.android.tech.trendingrepos.repofeature.model.remotedata.TrendingRepoRemoteSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class TrendingRepoRemoteSourceTest {

    @Mock
    RemoteApiService mRemoteApiService;

    private IRemoteTrendingRepo mRemoteDataSource;

    @Before
    public void setup() throws Exception {
        // init mocks
        MockitoAnnotations.initMocks(this);

        // get reference to the class in test
        mRemoteDataSource = new TrendingRepoRemoteSource(mRemoteApiService);

    }


    @Test
    public void testPreConditions() {
        assertNotNull(mRemoteDataSource);
    }

    /**
     * Test scenario states:
     * Remote Source should get the correct results in success scenario
     */
    @Test
    public void testRemoteApiResponse_success() throws Exception {
        TestSubscriber<List<TrendingRepoEntity>> testSubscriber = new TestSubscriber<>();

        List<GitHubRepo> listRepos = new ArrayList<>();

        // set up mock response
        GitHubRepo tempRepo = new GitHubRepo("android", "google", "https://google.com");
        listRepos.add(tempRepo);

        // prepare fake response
        when(mRemoteApiService.getRemoteTrendingRepos())
                .thenReturn(Single.just(listRepos));

        // trigger response
        mRemoteDataSource.getTrendingRepoList(false).toFlowable().subscribe(testSubscriber);


        testSubscriber.assertValue(parseTrendingRepoData(listRepos));
    }

    /**
     * Test scenario states:
     * Remote Source should get the correct results in failure scenario
     */
    @Test
    public void testRemoteApiResponse_failure() throws Exception {
        TestSubscriber<List<TrendingRepoEntity>> testSubscriber = new TestSubscriber<>();

        // prepare fake exception
        Throwable exception = new IOException();

        // prepare fake response
        when(mRemoteApiService.getRemoteTrendingRepos()).
                thenReturn(Single.<List<GitHubRepo>>error(exception));

        // assume the repository calls the remote DataSource
        mRemoteDataSource.getTrendingRepoList(false).toFlowable().subscribe(testSubscriber);

        testSubscriber.assertError(IOException.class);
    }

    private List<TrendingRepoEntity> parseTrendingRepoData(List<GitHubRepo> gitHubRepoList) {
        List<TrendingRepoEntity> trendingRepoEntityList = new ArrayList<>();

        for(GitHubRepo gitRepo: gitHubRepoList){
            TrendingRepoEntity trendingRepoEntity = new TrendingRepoEntity();
            trendingRepoEntity.setAuthor(gitRepo.getAuthor());
            trendingRepoEntity.setAvatar(gitRepo.getAvatar());
            trendingRepoEntity.setBuiltBy(gitRepo.getBuiltBy());
            trendingRepoEntity.setCurrentPeriodStars(gitRepo.getCurrentPeriodStars());
            trendingRepoEntity.setDescription(gitRepo.getDescription());
            trendingRepoEntity.setForks(gitRepo.getForks());
            trendingRepoEntity.setLanguage(gitRepo.getLanguage());
            trendingRepoEntity.setLanguageColor(gitRepo.getLanguageColor());
            trendingRepoEntity.setStars(gitRepo.getStars());
            trendingRepoEntity.setName(gitRepo.getName());
            trendingRepoEntity.setUrl(gitRepo.getUrl());
            trendingRepoEntity.setTimeStamp(System.currentTimeMillis());

            trendingRepoEntityList.add(trendingRepoEntity);
        }
        return trendingRepoEntityList;
    }
}
