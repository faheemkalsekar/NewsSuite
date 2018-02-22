package com.gadgetmedia.newssuite.data.source.local;

import android.support.annotation.NonNull;

import com.gadgetmedia.newssuite.data.source.NewsDataSource;
import com.gadgetmedia.newssuite.util.AppExecutors;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Concrete implementation of a News source as a db.
 */
@Singleton
public class NewsLocalDataSource implements NewsDataSource {

    private final NewsDao mNewsDao;

    private final AppExecutors mAppExecutors;

    @Inject
    public NewsLocalDataSource(@NonNull AppExecutors executors, @NonNull NewsDao newsDao) {
        mNewsDao = newsDao;
        mAppExecutors = executors;
    }

    @Override
    public void getNews(@NonNull LoadNewsCallback callback) {

    }

    @Override
    public void getNewsItem(@NonNull String newsId, @NonNull GetNewsCallback callback) {

    }
}
