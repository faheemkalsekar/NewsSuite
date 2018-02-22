package com.gadgetmedia.newssuite.ui.newslist;

import com.gadgetmedia.newssuite.di.ActivityScoped;

/**
 * Listens to user actions from the UI ({@link NewsListFragment}), retrieves the data and updates the
 * UI as required.
 * <p/>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the {@link NewsListPresenter} (if it fails, it emits a compiler error).  It uses
 * {@link NewsListModule} to do so.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 **/
@ActivityScoped
final class NewsListPresenter implements NewsListContract.Presenter{

    @Override
    public void loadNews(boolean forceUpdate) {

    }

    @Override
    public void takeView(NewsListContract.View view) {

    }

    @Override
    public void dropView() {

    }
}
