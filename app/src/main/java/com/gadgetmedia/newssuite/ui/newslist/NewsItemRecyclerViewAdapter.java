package com.gadgetmedia.newssuite.ui.newslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gadgetmedia.newssuite.R;
import com.gadgetmedia.newssuite.data.db.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.gadgetmedia.newssuite.ui.newslist.NewsListFragment.OnListFragmentInteractionListener;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link RecyclerView.Adapter} that can display a {@link News} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 *
 */
public class NewsItemRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private final Picasso mPicasso;
    private List<News> mValues;

    public NewsItemRecyclerViewAdapter(Picasso with, final List<News> items,
                                       final OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mPicasso = with;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newslist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(holder.mItem.getTitle());
        holder.mDescView.setText(holder.mItem.getDescription());

        // Resize to the width specified maintaining aspect ratio
        mPicasso.load(holder.mItem.getImageHref())
                .resize(240,0).
                into(holder.mImageView);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void replaceData(final List<News> newsList) {
        setList(newsList);
        notifyDataSetChanged();
    }

    private void setList(final List<News> newsList) {
        mValues = checkNotNull(newsList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescView;
        public News mItem;
        public final ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.title);
            mDescView = view.findViewById(R.id.desc);
            mImageView = view.findViewById(R.id.imageView);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
