package com.android.tech.trendingrepos.logic;

import com.android.tech.trendingrepos.repofeature.model.TrendingRepository;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.android.tech.trendingrepos.repofeature.view.GitUiModel;
import com.android.tech.trendingrepos.repofeature.viewmodel.TrendingRepoViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrendingRepoViewModelTest {

    private static List<TrendingRepoEntity> REPOS;

    private TrendingRepoViewModel mViewModel;

    private TestObserver<GitUiModel> mReposUISubscriber;

    @Mock
    private TrendingRepository mRepository;

    @Before
    public void setupReposViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mViewModel = new TrendingRepoViewModel(mRepository);

        TrendingRepoEntity tempRepo1 = new TrendingRepoEntity();
        tempRepo1.setUrl("https://google.com");
        tempRepo1.setName("Google");
        tempRepo1.setAuthor("google");
        tempRepo1.setStars(12000);
        tempRepo1.setTimeStamp(System.currentTimeMillis());

        TrendingRepoEntity tempRepo2 = new TrendingRepoEntity();
        tempRepo2.setUrl("https://fb.com");
        tempRepo2.setName("fb");
        tempRepo2.setAuthor("facebook");
        tempRepo2.setStars(2000);
        tempRepo2.setTimeStamp(System.currentTimeMillis());

        REPOS = new ArrayList<>();
        REPOS.add(tempRepo1);
        REPOS.add(tempRepo2);

        mReposUISubscriber = new TestObserver<>();
    }

    @Test
    public void getGitUiModel_emits_whenRepos() {
        // Given that we are subscribed to the emissions of the UI model
        withReposInRepositoryAndSubscribed(REPOS);

        // The Repos model containing the list of Repos is emitted
        mReposUISubscriber.assertValueCount(1);
        GitUiModel model = mReposUISubscriber.values().get(0);
        assertGitUiModelWithReposVisible(model);
    }

    @Test
    public void forceUpdateRepos_updatesReposRepository() {
        // Given that the repo repository never emits
        when(mRepository.getTrendingRepoList(true)).thenReturn(Single.just(REPOS));

        // When calling force update
        mViewModel.getTrendingRepo(true).subscribe(mReposUISubscriber);
        mReposUISubscriber.assertValueCount(1);
        GitUiModel model = mReposUISubscriber.values().get(0);
        assertGitUiModelWithReposVisible(model);

    }

    private void assertGitUiModelWithReposVisible(GitUiModel model) {
        assertRepoItems(model.getmRepoList());
    }

    private void withReposInRepositoryAndSubscribed(List<TrendingRepoEntity> repos) {
        // Given that the repo repository returns repos
        when(mRepository.getTrendingRepoList(false)).thenReturn(Single.just(repos));

        // Given that we are subscribed to the repos
        mViewModel.getTrendingRepo(false).subscribe(mReposUISubscriber);
    }

    private void assertRepoItems(List<TrendingRepoEntity> items) {
        // check if the RepoItems are the expected ones
        assertEquals(items.size(), REPOS.size());

        assertRepo(items.get(0), REPOS.get(0));
        assertRepo(items.get(1), REPOS.get(1));
    }

    private void assertRepo(TrendingRepoEntity targetItem, TrendingRepoEntity sourceItem) {
        assertEquals(targetItem, sourceItem);
    }


}
