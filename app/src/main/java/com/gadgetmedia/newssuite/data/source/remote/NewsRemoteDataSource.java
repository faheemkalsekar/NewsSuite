package com.gadgetmedia.newssuite.data.source.remote;

import android.support.annotation.NonNull;

import com.gadgetmedia.newssuite.api.NewsResponse;
import com.gadgetmedia.newssuite.api.NewsService;
import com.gadgetmedia.newssuite.api.Rows;
import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;
import com.gadgetmedia.newssuite.data.source.NewsDataSource;
import com.gadgetmedia.newssuite.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of the remote data source.
 */
@Singleton
public class NewsRemoteDataSource implements NewsDataSource {

    private final NewsService mNewsService;
    private final AppExecutors mAppExecutors;

    @Inject
    public NewsRemoteDataSource(@NonNull AppExecutors executors, @NonNull NewsService newsService) {
        mAppExecutors = executors;
        mNewsService = newsService;
    }

    @Override
    public void getNews(@NonNull final LoadNewsCallback callback) {

        mNewsService.getNews().enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                final NewsResponse body = response.body();

                if (body != null) {
                    final Rows[] rows = body.getRows();
                    final String newsTitle = body.getTitle();
                    final List<News> newsList = new ArrayList<>();

                    for (Rows row : rows) {
                        final News news = new News(row.getTitle(), row.getDescription(), row.getImageHref(), false);
                        newsList.add(news);
                    }

                    final Title title = new Title(newsTitle, newsList);
                    callback.onNewsLoaded(title);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void getNewsItem(@NonNull String newsId, @NonNull GetNewsCallback callback) {

    }

    @Override
    public void refreshNews() {
        // Not required because the {@link NewsRepository} handles the logic of refreshing the
        // news from all the available data sources.
    }

    @Override
    public void deleteAllNews() {

    }

    @Override
    public void saveNews(Title title) {

    }
}
