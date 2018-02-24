package com.gadgetmedia.newssuite.ui.newslist;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gadgetmedia.newssuite.R;
import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.util.ActivityUtils;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

import static com.gadgetmedia.newssuite.ui.newslist.NewsListFragment.OnHeadlineChangedListener;
import static com.gadgetmedia.newssuite.ui.newslist.NewsListFragment.OnListFragmentInteractionListener;


/**
 * Displays News List Screen.
 */
public class NewsListActivity extends DaggerAppCompatActivity implements OnHeadlineChangedListener, OnListFragmentInteractionListener {

    @Inject
    Lazy<NewsListFragment> newsFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newslist_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NewsListFragment newsListFragment = (NewsListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (newsListFragment == null) {
            newsListFragment = newsFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    newsListFragment, R.id.contentFrame);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onShowNewsLabel(final String label) {
        setTitle(label);
    }

    @Override
    public void onListFragmentInteraction(final News news) {

    }
}
