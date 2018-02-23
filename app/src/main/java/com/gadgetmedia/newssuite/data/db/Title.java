package com.gadgetmedia.newssuite.data.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Immutable model class for a News Title.
 */
@Entity(tableName = "title")
public class Title {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Expose
    private long mId;

    @ColumnInfo(name = "title")
    @Expose
    private String mTitle;

    @Ignore
    @Expose
    public List<News> news;

    public Title() {
    }

    public Title(final String mTitle, final List<News> news) {
        this.mTitle = mTitle;
        this.news = news;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
