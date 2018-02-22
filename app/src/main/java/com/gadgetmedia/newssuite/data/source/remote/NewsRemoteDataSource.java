package com.gadgetmedia.newssuite.data.source.remote;

import android.support.annotation.NonNull;

import com.gadgetmedia.newssuite.data.source.NewsDataSource;

import javax.inject.Singleton;

/**
 * Implementation of the remote data source.
 */
@Singleton
public class NewsRemoteDataSource implements NewsDataSource {

    public NewsRemoteDataSource() {}

    @Override
    public void getNews(@NonNull LoadNewsCallback callback) {

    }

    @Override
    public void getNewsItem(@NonNull String newsId, @NonNull GetNewsCallback callback) {

    }
}
