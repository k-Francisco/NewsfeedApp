package com.example.johncarter.newsfeedapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {

    private static final String REQUEST_URL = "https://newsapi.org/v1/articles?source=techradar&sortBy=latest&apiKey=802d23f119f34202b58e33a8728eb045";
    private static final int NEWS_LOADER_ID = 1;
    private RecyclerView recyclerView;
    private NewsRecycler newsRecycler;

    private static final String mCollapsedTitle = "Techradar";
    private static final String mExpandedTitle = "";
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        final AppCompatTextView title = (AppCompatTextView) findViewById(R.id.toolbarTitle);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private String state = "";
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(verticalOffset == 0){
                    if(state != "Expanded"){
                        title.setText("");
                        state = "Expanded";
                    }
                }else if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    if(state != "Collapsed"){
                        title.setText("Techradar");
                        state = "Collapsed";
                    }

                }


            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.news_reccler);
        recyclerView.setHasFixedSize(true);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {

            View loadingIndicator = findViewById(R.id.progressBar);
            loadingIndicator.setVisibility(View.GONE);

        }


    }


    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(REQUEST_URL);
        return new NewsLoader(this,baseUri.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> data) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            newsRecycler = new NewsRecycler(this,data);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(newsRecycler);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {

    }
}
