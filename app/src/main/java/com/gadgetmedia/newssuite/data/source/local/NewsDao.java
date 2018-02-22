package com.gadgetmedia.newssuite.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.gadgetmedia.newssuite.data.News;

import java.util.List;

/**
 * Data Access Object for the News table.
 */
@Dao
public interface NewsDao {
    /**
     * Select all news from the News table.
     *
     * @return all News items.
     */
    @Query("SELECT * FROM News")
    List<News> getNews();

    /**
     * Select a News by id.
     *
     * @param newsId the news id.
     * @return the News with newsId.
     */
    @Query("SELECT * FROM News WHERE id = :newsId")
    News getNewsById(String newsId);


}
