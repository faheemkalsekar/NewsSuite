package com.gadgetmedia.newssuite.data.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.gadgetmedia.newssuite.data.FakeNewsRemoteDataSource;
import com.gadgetmedia.newssuite.data.source.local.NewsDatabase;
import com.gadgetmedia.newssuite.data.source.local.NewsLocalDataSource;
import com.gadgetmedia.newssuite.data.source.local.NewsWithTitleDao;
import com.gadgetmedia.newssuite.util.AppExecutors;
import com.gadgetmedia.newssuite.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * This is used by Dagger to inject the required arguments into the {@link NewsRepository}.
 */
@Module
abstract public class NewsRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    static NewsDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "News.db")
                .build();
    }

    @Singleton
    @Provides
    static NewsWithTitleDao provideTasksDao(NewsDatabase db) {
        return db.newsWithTitleDao();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }

    @Singleton
    @Binds
    @Local
    abstract NewsDataSource provideTasksLocalDataSource(NewsLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract NewsDataSource provideTasksRemoteDataSource(FakeNewsRemoteDataSource dataSource);
}
