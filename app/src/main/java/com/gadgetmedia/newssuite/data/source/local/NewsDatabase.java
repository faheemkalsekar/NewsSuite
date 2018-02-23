package com.gadgetmedia.newssuite.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;

/**
 * The Room Database that contains the News table.
 */
@Database(entities = {News.class, Title.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract NewsWithTitleDao newsWithTitleDao();

}
