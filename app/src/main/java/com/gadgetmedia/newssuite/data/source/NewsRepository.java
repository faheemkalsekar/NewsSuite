/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gadgetmedia.newssuite.data.source;

import android.support.annotation.NonNull;

import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;
import com.gadgetmedia.newssuite.di.AppComponent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load News from the data sources into a cache.
 * By marking the constructor with {@code @Inject} and the class with {@code @Singleton}, Dagger
 * injects the dependencies required to create an instance of the NewsRepository (if it fails, it
 * emits a compiler error). It uses {@link NewsRepositoryModule} to do so, and the constructed
 * instance is available in {@link AppComponent}.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */
@Singleton
public class NewsRepository implements NewsDataSource {

    private final NewsDataSource mNewsRemoteDataSource;

    private final NewsDataSource mNewsLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, List<News>> mCachedNews;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    /**
     * By marking the constructor with {@code @Inject}, Dagger will try to inject the dependencies
     * required to create an instance of the NewsRepository. Because {@link NewsDataSource} is an
     * interface, we must provide to Dagger a way to build those arguments, this is done in
     * {@link NewsRepositoryModule}.
     * <p>
     * When two arguments or more have the same type, we must provide to Dagger a way to
     * differentiate them. This is done using a qualifier.
     * <p>
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    NewsRepository(@Remote NewsDataSource newsRemoteDataSource,
                   @Local NewsDataSource newsLocalDataSource) {
        mNewsRemoteDataSource = newsRemoteDataSource;
        mNewsLocalDataSource = newsLocalDataSource;
    }

    /**
     * Gets News from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadNewsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getNews(@NonNull final LoadNewsCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedNews != null && !mCacheIsDirty) {
            callback.onNewsLoaded(convertCache(mCachedNews));
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getNewsFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mNewsLocalDataSource.getNews(new LoadNewsCallback() {
                @Override
                public void onNewsLoaded(final Title title) {
                    refreshCache(title);
                    callback.onNewsLoaded(convertCache(mCachedNews));
                }

                @Override
                public void onDataNotAvailable() {
                    getNewsFromRemoteDataSource(callback);
                }
            });
        }
    }

    private void refreshCache(final Title title) {
        if (mCachedNews == null) {
            mCachedNews = new LinkedHashMap<String, List<News>>();
        }
        mCachedNews.clear();
        mCachedNews.put(title.getTitle(), title.getNews());
        mCacheIsDirty = false;
    }

    private void getNewsFromRemoteDataSource(@NonNull final LoadNewsCallback callback) {
        mNewsRemoteDataSource.getNews(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(final Title title) {
                refreshCache(title);
                refreshLocalDataSource(title);
                callback.onNewsLoaded(convertCache(mCachedNews));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(final Title title) {
        mNewsLocalDataSource.deleteAllNews();
        mNewsLocalDataSource.saveNews(title);
    }

    @Override
    public void getNewsItem(@NonNull String newsId, @NonNull GetNewsCallback callback) {

    }

    @Override
    public void refreshNews() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllNews() {
        mNewsRemoteDataSource.deleteAllNews();
        mNewsLocalDataSource.deleteAllNews();

        if (mCachedNews == null) {
            mCachedNews = new LinkedHashMap<>();
        }
        mCachedNews.clear();
    }

    @Override
    public void saveNews(final Title title) {

    }

    private Title convertCache(final Map<String, List<News>> mCachedNews) {
        final Title title = new Title();
        for (Map.Entry<String, List<News>> entry : mCachedNews.entrySet()) {
            String key = entry.getKey();
            List<News> value = entry.getValue();
            title.setNews(value);
            title.setTitle(key);
        }
        return title;
    }
}
