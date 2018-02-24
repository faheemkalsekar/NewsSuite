package com.gadgetmedia.newssuite;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.gadgetmedia.newssuite.data.source.NewsRepository;
import com.gadgetmedia.newssuite.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
public class NewsSuiteApp extends DaggerApplication {
    @Inject
    NewsRepository newsRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    /**
     * Our Espresso tests need to be able to get an instance of the {@link NewsRepository}
     * so that we can delete all news before running each test
     */
    @VisibleForTesting
    public NewsRepository getNewsRepository() {
        return newsRepository;
    }
}
