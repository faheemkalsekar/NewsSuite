package com.gadgetmedia.newssuite.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;
import com.gadgetmedia.newssuite.data.source.NewsDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */

public class FakeNewsRemoteDataSource implements NewsDataSource {

    @Inject
    public FakeNewsRemoteDataSource() {
    }

    @Override
    public void getNews(@NonNull LoadNewsCallback callback) {
        List<News> newsList = new ArrayList<>();
        News news1 = new News("Beavers", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png", false);
        News news2 = new News("Avril", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png", false);

        newsList.add(news1);
        newsList.add(news2);
        Title title = new Title("About Canada", newsList);
        callback.onNewsLoaded(title);
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
    public void saveNews(Title title) {

    }

    @VisibleForTesting
    public void addTasks(News... news) {

    }
}
