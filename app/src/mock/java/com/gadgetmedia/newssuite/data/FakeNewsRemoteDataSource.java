package com.gadgetmedia.newssuite.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.gadgetmedia.newssuite.data.source.NewsDataSource;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */

public class FakeNewsRemoteDataSource implements NewsDataSource {

    private static final Map<Long, News> NEWS_SERVICE_DATA = new LinkedHashMap<>();

    @Inject
    public FakeNewsRemoteDataSource() {
    }

    @Override
    public void getNews(@NonNull LoadNewsCallback callback) {
        callback.onNewsLoaded(Lists.newArrayList(NEWS_SERVICE_DATA.values()));
    }

    @Override
    public void getNewsItem(@NonNull String newsId, @NonNull GetNewsCallback callback) {
        News newsItem = NEWS_SERVICE_DATA.get(newsId);
        callback.onNewsLoaded(newsItem);
    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void deleteAllNews() {

    }

    @Override
    public void saveNews(@NonNull News news) {

    }

    @VisibleForTesting
    public void addTasks(News... news) {
        for (News newsItem : news) {
            NEWS_SERVICE_DATA.put(newsItem.getId(), newsItem);
        }
    }
}
