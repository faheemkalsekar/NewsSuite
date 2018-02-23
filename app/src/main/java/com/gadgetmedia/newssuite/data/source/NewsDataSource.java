package com.gadgetmedia.newssuite.data.source;

import android.support.annotation.NonNull;

import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;

/**
 * Main entry point for accessing News data.
 * <p>
 * getNews() and getNewsItem() have callbacks to inform the user of
 * network/database errors or successful operations.
 * <p/>
 */
public interface NewsDataSource {

    void getNews(@NonNull LoadNewsCallback callback);

    void getNewsItem(@NonNull String newsId, @NonNull GetNewsCallback callback);

    void refreshNews();

    void deleteAllNews();

    void saveNews(Title title);

    interface LoadNewsCallback {

        void onNewsLoaded(Title title);

        void onDataNotAvailable();
    }

    interface GetNewsCallback {

        void onNewsLoaded(News news);

        void onDataNotAvailable();
    }


}
