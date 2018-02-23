package com.gadgetmedia.newssuite.ui.newslist;

import com.gadgetmedia.newssuite.di.ActivityScoped;
import com.gadgetmedia.newssuite.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link NewsListPresenter}.
 */
@Module
public abstract class NewsListPresenterModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract NewsListFragment newsListFragment();

    @ActivityScoped
    @Binds
    abstract NewsListContract.Presenter newsListPresenter(NewsListPresenter presenter);
}
