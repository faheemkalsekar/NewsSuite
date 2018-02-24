package com.gadgetmedia.newssuite.ui.newslist;


import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.gadgetmedia.newssuite.NewsSuiteApp;
import com.gadgetmedia.newssuite.R;
import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;
import com.gadgetmedia.newssuite.data.source.NewsDataSource;
import com.gadgetmedia.newssuite.di.AppComponent;
import com.gadgetmedia.newssuite.di.DaggerAppComponent;
import com.gadgetmedia.newssuite.util.EspressoIdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NewsListActivityTest {

    private AppComponent mAppComponent;

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p>
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     * <p>
     * <p>
     * Sometimes an {@link Activity} requires a custom start {@link Intent} to receive data
     * from the source Activity. ActivityTestRule has a feature which let's you lazily start the
     * Activity under test, so you can control the Intent that is used to start the target Activity.
     */
    @Rule
    public ActivityTestRule<NewsListActivity> mNewsListActivity =
            new ActivityTestRule<NewsListActivity>(NewsListActivity.class, true /* Initial touch mode  */,
                    false /* Lazily launch activity */) {
                /**
                 * To avoid a long list of news and the need to scroll through the list to find a
                 * news, we call {@link NewsDataSource#deleteAllNews()} ()} before each test.
                 */

                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    NewsSuiteApp application = (NewsSuiteApp) InstrumentationRegistry.getTargetContext().getApplicationContext();
                    mAppComponent = DaggerAppComponent.builder().application(application).build();
                    mAppComponent.inject(application);
                    mAppComponent.getNewsRepository().deleteAllNews();
                }
            };


    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    private void loadNewsList() {
        startActivityWithStubbedNews();
    }

    /***
     * <p>
     * Note that this test runs hermetically and is fully isolated using a fake implementation of
     * the service API. This is a great way to make your tests more reliable and faster at the same
     * time, since they are isolated from any outside dependencies.
     */
    private void startActivityWithStubbedNews() {
        // Add a news stub to the fake service api layer.
        List<News> newsList = new ArrayList<>();
        News news1 = new News("Beavers", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png", false);
        News news2 = new News("Avril", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png", false);

        newsList.add(news1);
        newsList.add(news2);
        Title title = new Title("About Canada", newsList);
        mAppComponent.getNewsRepository().saveNews(title);

        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        mNewsListActivity.launchActivity(startIntent);
    }

    @Test
    public void newsDetails_DisplayedInUi() throws Exception {
        loadNewsList();

        // Check that the news title and description are displayed
        onView(withItemText("Beavers")).check(matches(isDisplayed()));
        onView(withItemText("Warmer than you might think.")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyNewsDetails_DisplayedInUi() throws Exception {
        loadEmptyNewsList();

        onView(withText(R.string.empty_list)).check(matches(isDisplayed()));
    }

    private void loadEmptyNewsList() {
        startActivityWithStubbedEmptyNews();
    }

    private void startActivityWithStubbedEmptyNews() {
        // Add a news stub to the fake service api layer.
        List<News> newsList = new ArrayList<>();
        News news1 = new News();
        News news2 = new News();
        newsList.add(news1);
        newsList.add(news2);
        Title title = new Title("About Canada", newsList);
        mAppComponent.getNewsRepository().saveNews(title);

        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        mNewsListActivity.launchActivity(startIntent);
    }

    /**
     * `
     * A custom {@link Matcher} which matches an item in a {@link ListView} by its text.
     * <p>
     * View constraints:
     * <ul>
     * <li>View must be a child of a {@link ListView}
     * <ul>
     *
     * @param itemText the text to match
     * @return Matcher that matches text in the given view
     */
    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA LV with text " + itemText);
            }
        };
    }

}
