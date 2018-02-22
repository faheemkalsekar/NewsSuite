package com.gadgetmedia.newssuite.ui.newslist;

import android.os.Bundle;

import com.gadgetmedia.newssuite.R;

import dagger.android.support.DaggerAppCompatActivity;



public class NewsListActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
