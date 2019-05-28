package com.jpz.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jpz.mynews.Controllers.Activities.WebViewActivity;
import com.jpz.mynews.Controllers.Utils.APIClient;
import com.jpz.mynews.Models.API;
import com.jpz.mynews.Models.GenericNews;

import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements AdapterAPI.Listener {

    private RecyclerView recyclerView;

    // For data
    private Disposable disposable;

    // Declare list of results & Adapter
    private AdapterAPI adapterAPI;
    private List<GenericNews> genericNewsList;

    // Create keys for Bundle & Intent
    private static final String KEY_POSITION = "position";
    public static final String KEY_URL = "item";

    public MainFragment() {
        // Required empty public constructor
    }

    // Method that will create a new instance of MainFragment, and add data to its bundle.
    public static MainFragment newInstance(API api) {

        // Create new fragment
        MainFragment fragment = new MainFragment();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putSerializable(KEY_POSITION, api);
        fragment.setArguments(args);

        return(fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout of this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Get RecyclerView from layout and serialise it
        recyclerView = view.findViewById(R.id.fragment_main_recycler_view);

        // Call during UI creation
        configureRecyclerView();

        // Get data from Bundle (created in method newInstance)
        if (getArguments() == null) {
            return view;
        }

        // Get data from Bundle
        API api = (API) getArguments().getSerializable(KEY_POSITION);
        if (api != null)
        switch (api) {
            // Load articles of NY Times when launching the app
            // Execute streams after UI creation
            case TopStories:
                executeTopStoriesRequest();
                break;
            case MostPopular:
                executeMostPopularRequest();
                break;
            case Foreign:
                executeArticleSearchRequest();
                break;
            case Financial:
                //executeArticleSearchRequest(Service.API_FILTER_FINANCIAL);
                break;
            case Technology:
                //executeArticleSearchRequest(Service.API_FILTER_TECHNOLOGY);
                break;
        }
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

    private void configureRecyclerView(){
        // Reset list
        this.genericNewsList = new ArrayList<>();
        // Create adapter passing the list of articles
        this.adapterAPI = new AdapterAPI(genericNewsList, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        this.recyclerView.setAdapter(adapterAPI);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // Execute TopStories stream
    private void executeTopStoriesRequest(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = APIClient.fetchStoriesToGeneric()
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG","On Next TopStories");
                        // Update UI with list of TopStories
                        updateUI(genericNewsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error TopStories" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG","On Complete TopStories");
                    }
                });
    }

    // Execute MostPopular stream
    private void executeMostPopularRequest(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = APIClient.fetchPopularToGeneric()
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG","On Next MostPopular");
                        // Update UI with lis of MostPopular
                        updateUI(genericNewsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error MostPopular" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG","On Complete MostPopular");
                    }
                });
    }

        // Execute ArticleSearch stream
        private void executeArticleSearchRequest(){
            // Execute the stream subscribing to Observable defined inside Stream
            this.disposable = APIClient.fetchSearchToGeneric()
                    .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                        @Override
                        public void onNext(List<GenericNews> genericNewsList) {
                            Log.i("TAG","On Next ArticleSearch");
                            // Update UI with a list of ArticleSearch
                            updateUI(genericNewsList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG","On Error ArticleSearch" + Log.getStackTraceString(e));
                        }

                        @Override
                        public void onComplete() {
                            Log.i("TAG","On Complete ArticleSearch");
                        }
                    });
        }

    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //  Update UI for Top Stories
    private void updateUI(List<GenericNews> newsList){
        genericNewsList.addAll(newsList);
        adapterAPI.notifyDataSetChanged();
    }

}
