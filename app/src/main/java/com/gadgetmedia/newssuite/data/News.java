package com.gadgetmedia.newssuite.data;

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
    private final long mId;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @Nullable
    @ColumnInfo(name = "imageHref")
    private final String mImageHref;

    @ColumnInfo(name = "completed")
    private final boolean mCompleted;


    /**
     * Constructor to specify a News Item
     *
     * @param id          id of the task
     * @param title       title of the News
     * @param description description of the News
     * @param imageHref   image to be downloaded
     * @param completed   true if the News is read, false if it's not
     */

    public News(long id, @Nullable String title, @Nullable String description,
                @Nullable String imageHref, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mImageHref = imageHref;
        mCompleted = completed;
    }

    public long getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public String getImageHref() {
        return mImageHref;
    }


    public boolean isCompleted() {
        return mCompleted;
    }
}
