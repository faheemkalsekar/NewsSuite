package com.gadgetmedia.newssuite.ui.newslist;

import com.gadgetmedia.newssuite.BasePresenter;
import com.gadgetmedia.newssuite.BaseView;
import com.gadgetmedia.newssuite.data.db.News;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */

public interface NewsListContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(final boolean active);

        boolean isActive();

        void showNewsList(final List<News> newsList);

        void showNoNews();

        void showNewsLabel(String label);

        void showLoadingNewsError();
    }

    interface Presenter extends BasePresenter<View> {

        void loadNews(final boolean forceUpdate);

        void takeView(final NewsListContract.View view);

        void dropView();

    }
}
