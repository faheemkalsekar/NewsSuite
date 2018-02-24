package com.gadgetmedia.newssuite.data.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

/**
 * Immutable model class for a News item.
 */
@Entity(tableName = "news")
public final class News {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @Nullable
    @ColumnInfo(name = "title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private String mDescription;

    @Nullable
    @ColumnInfo(name = "imageHref")
    private String mImageHref;

    @ColumnInfo(name = "completed")
    private boolean mCompleted;

    @ColumnInfo(name = "category")
    private String mCategory;

    public News() {
    }

    /**
     * Constructor to specify a News Item
     *
     * @param title       title of the News
     * @param description description of the News
     * @param imageHref   image to be downloaded
     * @param completed   true if the News is read, false if it's not
     */

    public News(@Nullable String title, @Nullable String description,
                @Nullable String imageHref, boolean completed) {

        mTitle = title;
        mDescription = description;
        mImageHref = imageHref;
        mCompleted = completed;
    }

    public long getId() {
        return mId;
    }

    public void setId(final long mId) {
        this.mId = mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@Nullable final String mTitle) {
        this.mTitle = mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@Nullable String mDescription) {
        this.mDescription = mDescription;
    }

    @Nullable
    public String getImageHref() {
        return mImageHref;
    }

    public void setImageHref(@Nullable String mImageHref) {
        this.mImageHref = mImageHref;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(final boolean mCompleted) {
        this.mCompleted = mCompleted;
    }


    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}
