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
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.jpz.mynews.Controllers.Activities.WebViewActivity;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class NewsFragment extends Fragment implements AdapterAPI.Listener {

    protected RecyclerView recyclerView;

    // For data
    protected Disposable disposable;
    protected int page;

    // Declare list of results & Adapter
    protected AdapterAPI adapterAPI;
    protected List<GenericNews> genericNewsList;

    // Create keys for Intent
    public static final String KEY_URL = "item";

    // Fields (Boolean) to detect more scrolling
    protected LinearLayoutManager layoutManager;
    protected ProgressBar progressBar;
    protected boolean loadMore = false;
    protected int currentItems, totalItems, scrollOutItems;

    public NewsFragment() {
        // Required empty public constructor
    }

    protected abstract void executeRequest(int page);

    protected abstract void updateUI(List<GenericNews> newsList);

    protected abstract void fetchData();




        @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout of this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Get RecyclerView from layout and serialise it
        recyclerView = view.findViewById(R.id.fragment_main_recycler_view);

        // Get ProgressBar
        progressBar = view.findViewById(R.id.progressbar);

        // Call during UI creation
        configureRecyclerView();

        executeRequest(page);

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
        String url = adapterAPI.getPosition(position).url;

        // Spread the click with the url to WebViewActivity
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(KEY_URL, url);
        startActivity(intent);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Configure RecyclerViews, Adapters, LayoutManager & glue it together

    protected void configureRecyclerView(){
        // Reset list
        this.genericNewsList = new ArrayList<>();
        // Create adapter passing the list of articles
        this.adapterAPI = new AdapterAPI(genericNewsList, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        this.recyclerView.setAdapter(adapterAPI);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Add On Scroll Listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    loadMore = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                layoutManager = new LinearLayoutManager(getContext());

                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if (loadMore && currentItems + scrollOutItems == totalItems)
                    // Data fetch
                    loadMore = false;
                fetchData();
            }
        });
    }

    // Dispose subscription
    protected void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

}