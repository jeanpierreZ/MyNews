package com.jpz.mynews.controllers.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jpz.mynews.models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.controllers.adapters.AdapterNews;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class NewsFragment extends Fragment implements AdapterNews.Listener {

    // Declare Containers, View, Disposable, Adapter & a list of news
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView recyclerView;
    protected Disposable disposable;
    protected AdapterNews adapterNews;
    protected List<GenericNews> genericNewsList;

    // For data
    protected int page;
    protected String query;
    protected String beginDate;
    protected String endDate;

    // Declare callback
    private OnWebClickedListener callbackUrl;

    public NewsFragment() {
        // Required empty public constructor
    }

    // Overloading method for child fragments
    protected abstract void fetchData();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout of this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Get SwipeRefreshLayout from layout and serialise it
        swipeRefreshLayout = view.findViewById(R.id.fragment_news_swipe_container);

        // Get RecyclerView from layout and serialise it
        recyclerView = view.findViewById(R.id.fragment_news_recycler_view);

        // Call during UI creation
        configureSwipeRefreshLayout();
        configureRecyclerView();

        // Load articles of NY Times when launching the app (execute streams after UI creation)
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

        // Spread the click to the parent activity
        callbackUrl.OnWebClicked(position, url);
    }

    // ----------------------------------------------------------------------------
    // Configure SwipeRefreshLayout, RecyclerViews, Adapters, LayoutManager & glue it together

    protected void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Clear the list and fetch data from the first page
                        genericNewsList.clear();
                        page = 0;
                        fetchData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    protected void configureRecyclerView(){
        // Reset list
        this.genericNewsList = new ArrayList<>();
        // Create the adapter by passing the list of articles
        this.adapterNews = new AdapterNews(genericNewsList, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        this.recyclerView.setAdapter(adapterNews);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void updateUI(List<GenericNews> newsList) {
        // Add the list from the request and notify the adapter
        genericNewsList.addAll(newsList);
        adapterNews.notifyDataSetChanged();
    }

    // To dispose subscription
    protected void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // ----------------------------------------------------------------------------
    // Interface for callback to parent activity and associated methods

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    // Declare our interface that will be implemented by any container activity
    public interface OnWebClickedListener {
        void OnWebClicked(int position, String url);
    }

    // Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            // Parent activity will automatically subscribe to callback
            callbackUrl = (OnWebClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnWebClickedListener");
        }
    }

}