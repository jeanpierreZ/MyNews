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
import com.jpz.mynews.Views.AdapterAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class NewsFragment extends Fragment implements AdapterAPI.Listener {

    private RecyclerView recyclerView;

    // For data
    private Disposable disposable;

    // Declare list of results & Adapter
    private List<GenericNews> genericNewsList = new ArrayList<>();
    private AdapterAPI adapterAPI;

    // Create keys for Intent
    public static final String KEY_URL = "item";

    // Force developer implement those methods
    protected abstract NewsFragment newInstance();
    //protected abstract int getFragmentLayout();
    protected abstract void configureDesign();
    protected abstract void updateRecyclerView();
    protected abstract void destroyView();
    protected abstract void updateOnClickItem();
    protected abstract void updateNewsUI();

    protected abstract List<NewsFragment> fetchNews(int page);



    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Get RecyclerView from layout and serialise it
        recyclerView = view.findViewById(R.id.fragment_main_recycler_view);

        // Call during UI creation
        configureRecyclerView();

        fetchNews(0);

        // Configure Design (call this method instead of override onCreateView())
        configureDesign();
        return(view);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Dispose subscription when activity is destroyed
        this.disposeWhenDestroy();

        destroyView();
    }

    // Override the method to load url
    @Override
    public void onClickItem(int position) {
        // Save the url of the item in the RecyclerView
        String url = adapterAPI.getPosition(position).getUrl();

        // Spread the click with the url to WebViewActivity
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(KEY_URL, url);
        startActivity(intent);

        updateOnClickItem();
    }


    protected void configureRecyclerView(){
        // Reset list
        List<GenericNews> genericNewsList = new ArrayList<>();
        // Create adapter passing the list of articles
        adapterAPI = new AdapterAPI(genericNewsList, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(adapterAPI);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateRecyclerView();
    }


    //  Update UI for Top Stories
    protected void updateUI(List<GenericNews> list){
        genericNewsList.addAll(list);
        adapterAPI.notifyDataSetChanged();
        updateNewsUI();
    }


    // Dispose subscription
    protected void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

}
