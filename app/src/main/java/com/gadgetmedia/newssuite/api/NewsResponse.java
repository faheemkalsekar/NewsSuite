package com.gadgetmedia.newssuite.api;

import com.google.gson.annotations.SerializedName;

/**
 * POJO to hold News responses.
 */
public class NewsResponse {

    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private Rows[] rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Rows[] getRows() {
        return rows;
    }

    public void setRows(Rows[] rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "NewsResponse [title = " + title + ", rows = " + rows + "]";
    }
}
