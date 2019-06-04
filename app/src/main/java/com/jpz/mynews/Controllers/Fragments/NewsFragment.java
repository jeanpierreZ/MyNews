package com.jpz.mynews.Controllers.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jpz.mynews.Controllers.Activities.WebViewActivity;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterNews;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class NewsFragment extends Fragment implements AdapterNews.Listener {

    // For data
    protected RecyclerView recyclerView;
    protected Disposable disposable;

    // Declare list of news & Adapter
    protected AdapterNews adapterNews;
    protected List<GenericNews> genericNewsList;

    // Create key for Intent
    public static final String KEY_URL = "item";

    public NewsFragment() {
        // Required empty public constructor
    }

    // Overloading methods for child fragments
    protected abstract void fetchData();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout of this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Get RecyclerView from layout and serialise it
        recyclerView = view.findViewById(R.id.fragment_news_recycler_view);

        // Call during UI creation
        configureRecyclerView();

        // Load articles of NY Times when launching the app
        // Execute streams after UI creation
        fetchData();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Dispose subscription when activity is destroyed
        this.disposeWhenDestroy();
    }

    // Override the method to load url
    @Override
    public void onClickItem(int position) {
        // Save the url of the item in the RecyclerView
        String url = adapterNews.getPosition(position).url;

        // Spread the click with the url to WebViewActivity
        Intent webViewActivity = new Intent(getActivity(), WebViewActivity.class);
        webViewActivity.putExtra(KEY_URL, url);
        startActivity(webViewActivity);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Configure RecyclerViews, Adapters, LayoutManager & glue it together

    protected void configureRecyclerView(){
        // Reset list
        this.genericNewsList = new ArrayList<>();
        // Create adapter passing the list of articles
        this.adapterNews = new AdapterNews(genericNewsList, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        this.recyclerView.setAdapter(adapterNews);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void updateUI(List<GenericNews> newsList) {
        genericNewsList.addAll(newsList);
        adapterNews.notifyDataSetChanged();
    }

    // Dispose subscription
    protected void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

}