package com.gadgetmedia.newssuite.ui.newslist;

import com.gadgetmedia.newssuite.BasePresenter;
import com.gadgetmedia.newssuite.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */

public interface NewsListContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter<View> {

        void loadNews(boolean forceUpdate);

        void takeView(NewsListContract.View view);

        void dropView();

    }
}
