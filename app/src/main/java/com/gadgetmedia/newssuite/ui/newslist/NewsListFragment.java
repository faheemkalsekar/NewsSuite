package com.gadgetmedia.newssuite.ui.newslist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gadgetmedia.newssuite.R;
import com.gadgetmedia.newssuite.data.db.News;
import com.gadgetmedia.newssuite.di.ActivityScoped;
import com.gadgetmedia.newssuite.util.EmptyStateRecyclerView;
import com.gadgetmedia.newssuite.util.ScrollChildSwipeRefreshLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Main UI for the News List Screen.
 */
@ActivityScoped
public class NewsListFragment extends DaggerFragment implements NewsListContract.View {

    @Inject
    NewsListContract.Presenter mPresenter;

    OnHeadlineChangedListener mCallback;

    private OnListFragmentInteractionListener mListener;
    private NewsItemRecyclerViewAdapter mAdapter;

    @Inject
    public NewsListFragment() {
        // The required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newslist_frag, container, false);

        // Set the adapter
        Context context = view.getContext();
        final EmptyStateRecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setEmptyView(view.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);
        mAdapter = new NewsItemRecyclerViewAdapter(Picasso.with(context),new ArrayList<News>(), mListener);
        recyclerView.setAdapter(mAdapter);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadNews(true);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        final Activity activity = (Activity) context;
        try {
            mCallback = (OnHeadlineChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        //prevent leaking activity in case presenter is orchestrating a long running task
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showNewsList(final List<News> newsList) {
        mAdapter.replaceData(newsList);
    }

    @Override
    public void showNoNews() {
    // Handled by EmptyStateRecycler view
    }

    @Override
    public void showNewsLabel(final String label) {
        if (getView() == null) {
            return;
        }
        mCallback.onShowNewsLabel(label);

    }

    @Override
    public void showLoadingNewsError() {
        showMessage(getString(R.string.loading_news_error));
    }
    private void showMessage(final String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
    // News Activity must implement this interface
    public interface OnHeadlineChangedListener {
        public void onShowNewsLabel(final String label);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(final News news);
    }

}
