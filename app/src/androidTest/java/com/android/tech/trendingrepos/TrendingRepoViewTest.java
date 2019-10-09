package com.android.tech.trendingrepos;

import androidx.test.rule.ActivityTestRule;

import com.android.tech.trendingrepos.repofeature.view.activities.TrendingRepoActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TrendingRepoViewTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<TrendingRepoActivity> mNewsActivityTestRule =
            new ActivityTestRule<>(TrendingRepoActivity.class);

    // More complex tests should be added as app's complexity rises
    @Test
    public void displayItemsInList(){
        // check if the ListView is visible
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }
}
