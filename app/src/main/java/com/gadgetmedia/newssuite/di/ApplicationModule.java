package com.gadgetmedia.newssuite.di;

import android.app.Application;
import android.content.Context;

import com.gadgetmedia.newssuite.BuildConfig;
import com.gadgetmedia.newssuite.api.NewsService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This is a Dagger module. We use this to bind our Application class as a Context in the AppComponent
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 * One of the advantages of Dagger.Android is that your
 * Application & Activities are provided into your graph for you.
 * {@link
 * AppComponent}.
 */
@Module
public abstract class ApplicationModule {
    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(final Application application) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        File cacheDir = new File(application.getCacheDir(), UUID.randomUUID().toString());
        // 10 MiB cache
        Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);

        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    static NewsService provideNewsService(final Gson gson, final OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(NewsService.class);
    }


    @Provides
    @Singleton
    static Picasso picasso(final Application application) {

        return new Picasso.Builder(application)
                .build();
    }


    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(final Application application);
}

