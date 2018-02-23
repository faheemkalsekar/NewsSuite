package com.gadgetmedia.newssuite.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gadgetmedia.newssuite.api.NewsResponse;
import com.gadgetmedia.newssuite.api.Rows;
import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.data.db.Title;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data Access Object for the News table.
 */
@Dao
public abstract class NewsWithTitleDao {
    /**
     * Select all news from the News table.
     *
     * @return all News items.
     */
    @Query("SELECT * FROM NEWS")
    public abstract List<News> getNews();

    /**
     * Select a News by id.
     *
     * @param newsId the news id.
     * @return the News with newsId.
     */
    @Query("SELECT * FROM NEWS WHERE id = :newsId")
    public abstract News getNewsById(String newsId);

    @Insert
    public abstract void _insertNewsTitle(final Title newsTitle);

    @Insert
    public abstract void _insertNewsList(final List<News> pets);

    @Query("SELECT * FROM TITLE")
    public abstract Title getNewsTitle();

    @Query("SELECT * FROM NEWS WHERE category =:category")
    public abstract List<News> getNewsList(final String category);

    public void insertTitleWithNews(final Title title) {
        final List<News> newsList = title.getNews();
        for (int i = 0; i < newsList.size(); i++) {
            newsList.get(i).setCategory(title.getTitle());
        }
        _insertNewsList(newsList);
        _insertNewsTitle(title);
    }

    public Title getTitleWithNews() {
        Title title = getNewsTitle();
        List<News> newsList = getNewsList(title.getTitle());
        title.setNews(newsList);
        return title;
    }

}
