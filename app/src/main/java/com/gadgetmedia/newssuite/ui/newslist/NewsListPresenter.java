package com.gadgetmedia.newssuite.ui.newslist;

import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;
import com.gadgetmedia.newssuite.data.source.NewsDataSource;
import com.gadgetmedia.newssuite.data.source.NewsRepository;
import com.gadgetmedia.newssuite.di.ActivityScoped;
import com.gadgetmedia.newssuite.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Listens to user actions from the UI ({@link NewsListFragment}), retrieves the data and updates the
 * UI as required.
 * <p/>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the {@link NewsListPresenter} (if it fails, it emits a compiler error).  It uses
 * {@link NewsListPresenterModule} to do so.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 **/
@ActivityScoped
final class NewsListPresenter implements NewsListContract.Presenter {

    private final NewsRepository mNewsRepository;
    @Nullable
    private NewsListContract.View mNewsListView;

    private boolean mFirstLoad = true;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    NewsListPresenter(final NewsRepository newsRepository) {
        mNewsRepository = newsRepository;
    }

    @Override
    public void loadNews(boolean forceUpdate) {
        // A network reload will be forced on first load.
        loadNews(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link NewsDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadNews(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            if (mNewsListView != null) {
                mNewsListView.setLoadingIndicator(true);
            }
        }
        if (forceUpdate) {
            mNewsRepository.refreshNews();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        // App is busy until further notice
        EspressoIdlingResource.increment();

        mNewsRepository.getNews(new NewsDataSource.LoadNewsCallback() {

            @Override
            public void onNewsLoaded(final Title title) {
                List<News> newsToShow = new ArrayList<>();

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                newsToShow.addAll(title.getNews());

                if (mNewsListView == null || !mNewsListView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mNewsListView.setLoadingIndicator(false);
                }

                processNews(newsToShow, title.getTitle());
            }


            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (mNewsListView == null || !mNewsListView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mNewsListView.setLoadingIndicator(false);
                }
                mNewsListView.showLoadingNewsError();
            }
        });
    }

    private void processNews(final List<News> newsList, final String label) {
        if (newsList.isEmpty()) {
            // Show a message indicating there are no news for now.
            processEmptyNews();
        } else {
            // Show the list of tasks
            if (mNewsListView != null) {
                mNewsListView.showNewsList(newsList);
            }
            // Set the Title in the Action Bar.
            showNewsLabel(label);
        }
    }

    private void showNewsLabel(final String label) {

        if (mNewsListView != null) {
            mNewsListView.showNewsLabel(label);
        }

    }


    private void processEmptyNews() {
        if (mNewsListView == null) return;
        mNewsListView.showNoNews();
    }

    @Override
    public void takeView(final NewsListContract.View view) {
        mNewsListView = view;
        loadNews(false);
    }

    @Override
    public void dropView() {
        mNewsListView = null;
    }
}
