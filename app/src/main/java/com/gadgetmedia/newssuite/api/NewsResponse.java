package com.gadgetmedia.newssuite.api;

import com.gadgetmedia.newssuite.data.News;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO to hold News responses.
 */
public class NewsResponse {
    @SerializedName("title")
    private String mTitle;
    @SerializedName("rows")
    private List<News> mNewsItems;

    public String getTitle() {
        return mTitle;
    }

    public List<News> getNewsItems() {
        return mNewsItems;
    }

    public void setNewsItems(List<News> mNewsItems) {
        this.mNewsItems = mNewsItems;
    }
}
