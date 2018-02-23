package com.gadgetmedia.newssuite.data.source.local;

import android.support.annotation.NonNull;

import com.gadgetmedia.newssuite.api.NewsResponse;
import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;
import com.gadgetmedia.newssuite.data.source.NewsDataSource;
import com.gadgetmedia.newssuite.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation of a News source as a db.
 */
@Singleton
public class NewsLocalDataSource implements NewsDataSource {

    private final NewsWithTitleDao mNewsDao;

    private final AppExecutors mAppExecutors;

    @Inject
    public NewsLocalDataSource(@NonNull AppExecutors executors, @NonNull NewsWithTitleDao newsDao) {
        mNewsDao = newsDao;
        mAppExecutors = executors;
    }
    /**
     * Note: {@link LoadNewsCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getNews(@NonNull LoadNewsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<News> tasks = mNewsDao.getNews();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
//                            callback.onNewsLoaded(tasks);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getNewsItem(@NonNull String newsId, @NonNull GetNewsCallback callback) {

    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void deleteAllNews() {

    }

    @Override
    public void saveNews(final Title title) {
        checkNotNull(title);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mNewsDao.insertTitleWithNews(title);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);

    }
}
