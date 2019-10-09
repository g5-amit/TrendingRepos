package com.android.tech.trendingrepos.datasource;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.android.tech.trendingrepos.app.utils.schedulers.BaseSchedulerProvider;
import com.android.tech.trendingrepos.app.utils.schedulers.ImmediateSchedulerProvider;
import com.android.tech.trendingrepos.repofeature.model.localdata.AppRoomDb;
import com.android.tech.trendingrepos.repofeature.model.localdata.TrendingRepoLocalSource;
import com.android.tech.trendingrepos.repofeature.model.localdata.dao.TrendingRepoDao;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class TrendingRepoLocalSourceTest {

    private TrendingRepoLocalSource mLocalDataSource;

    private AppRoomDb mDatabase;

    private BaseSchedulerProvider mSchedulerProvider;

    private static final TrendingRepoEntity REPO_ENTITY = new TrendingRepoEntity();

    @Before
    public void setup() {

        REPO_ENTITY.setUrl("https://google.com");
        REPO_ENTITY.setName("Google");
        REPO_ENTITY.setAuthor("google");
        REPO_ENTITY.setTimeStamp(System.currentTimeMillis());

        // using an in-memory database for testing, since it doesn't survive killing the process
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                AppRoomDb.class)
                .build();
        TrendingRepoDao trendingReposDao = mDatabase.repoDao();

        mSchedulerProvider = new ImmediateSchedulerProvider();

        // Make sure that we're not keeping a reference to the wrong instance.
        mLocalDataSource = new TrendingRepoLocalSource(trendingReposDao, mSchedulerProvider);
    }

    @After
    public void cleanUp() {
        mDatabase.repoDao().deleteAllTrendingRepo();
        mDatabase.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    /**
     * Test scenario states:
     * Local Source should get the correct result upon saving a repo
     */
    @Test
    public void saveRepo_retrievesRepo() {
        // When saved into the repos repository
        mLocalDataSource.saveTrendingRepo(REPO_ENTITY);

        // Then the repo can be retrieved from the persistent repository
        TestSubscriber<TrendingRepoEntity> testSubscriber = new TestSubscriber<>();

        mLocalDataSource.getTrendingRepo(REPO_ENTITY.getUrl()).toFlowable().subscribe(testSubscriber);

        testSubscriber.assertValue(REPO_ENTITY);
    }

    /**
     * Test scenario states:
     * Local Source should get the correct result upon saving repos
     */
    @Test
    public void getRepos_retrieveSavedRepos() {
        // Given 2 new repos in the persistent repository

        final TrendingRepoEntity REPO_ENTITY1 = new TrendingRepoEntity();
        REPO_ENTITY1.setUrl("https://google.com");
        REPO_ENTITY1.setName("Google");
        REPO_ENTITY1.setAuthor("google");
        REPO_ENTITY1.setTimeStamp(System.currentTimeMillis());


        final TrendingRepoEntity REPO_ENTITY2 = new TrendingRepoEntity();
        REPO_ENTITY2.setUrl("https://google2.com");
        REPO_ENTITY2.setName("Google2");
        REPO_ENTITY2.setAuthor("google");
        REPO_ENTITY2.setTimeStamp(System.currentTimeMillis());

        mLocalDataSource.saveTrendingRepo(REPO_ENTITY1);
        mLocalDataSource.saveTrendingRepo(REPO_ENTITY2);

        // Then the repos can be retrieved from the persistent repository
        TestSubscriber<List<TrendingRepoEntity>> testSubscriber = new TestSubscriber<>();

        mLocalDataSource.getTrendingRepoList(false).toFlowable().subscribe(testSubscriber);

        List<TrendingRepoEntity> result = testSubscriber.values().get(0);

        assertThat(result, hasItems(REPO_ENTITY1, REPO_ENTITY2));
    }

    /**
     * Test scenario states:
     * Local Source should get the correct result upon having no data
     */
    @Test
    public void getRepo_whenRepoNotSaved() {
        //Given that no Repo has been saved
        //When querying for a repo, no values are returned.
        TestSubscriber<TrendingRepoEntity> testSubscriber = new TestSubscriber<>();

        mLocalDataSource.getTrendingRepo("some_id").toFlowable().subscribe(testSubscriber);

        testSubscriber.assertNoValues();
    }

    /**
     * Test scenario states:
     * Local Source should get the correct result upon emptying source
     */
    @Test
    public void deleteAllRepos_emptyListOfRetrievedrepos() {
        // Given a new repo in the persistent repository
        mLocalDataSource.saveTrendingRepo(REPO_ENTITY);

        // When all repos are deleted
        mLocalDataSource.deleteTrendingRepoList();

        // Then the retrieved repos is an empty list
        TestSubscriber<List<TrendingRepoEntity>> testSubscriber = new TestSubscriber<>();

        mLocalDataSource.getTrendingRepoList(false).toFlowable().subscribe(testSubscriber);

        List<TrendingRepoEntity> result = testSubscriber.values().get(0);

        assertThat(result.size(), is(0));
    }
}
