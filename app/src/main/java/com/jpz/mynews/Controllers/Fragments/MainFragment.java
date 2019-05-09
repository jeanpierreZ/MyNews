package com.jpz.mynews.Controllers.Fragments;

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
import com.jpz.mynews.Controllers.Utils.NYTService;
import com.jpz.mynews.Controllers.Utils.NYTStreams;
import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.NYTArticleSearch;
import com.jpz.mynews.Models.NYTMostPopular;
import com.jpz.mynews.Models.NYTResult;
import com.jpz.mynews.Models.NYTResultMP;
import com.jpz.mynews.Models.NYTTopStories;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.MostPopularAdapter;
import com.jpz.mynews.Views.TopStoriesAdapter;

import java.util.ArrayList;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView recyclerView;

    // For data
    private Disposable disposable;

    // Declare list of results (NYTResult) & Adapter
    private List<NYTResult> results;
    private TopStoriesAdapter topStoriesAdapter;

    // Declare list of results (NYTResultMP) & Adapter
    private List<NYTResultMP> resultMPList;
    private MostPopularAdapter mostPopularAdapter;

    // Create keys for our Bundle
    private static final String KEY_POSITION = "position";

    public MainFragment() {
        // Required empty public constructor
    }

    // Method that will create a new instance of MainFragment, and add data to its bundle.
    public static MainFragment newInstance(int position) {

        // Create new fragment
        MainFragment fragment = new MainFragment();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
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

        // Get data from Bundle (created in method newInstance)
        if (getArguments() == null) {
            return view;
        }

        // Get data from Bundle
        int position = getArguments().getInt(KEY_POSITION, -1);
        switch (position) {
            // Update recyclerView with it
            // Load articles of NY Times when launching the app
            case 0 :
                // Call during UI creation
                configureRecyclerViewTP();
                // Execute stream after UI creation
                executeTopStoriesRequest();
                break;
            case 1 :
                configureRecyclerViewMP();
                executeMostPopularRequest();
                break;
        }

        // executeArticleSearchRequest();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Dispose subscription when activity is destroyed
        this.disposeWhenDestroy();
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Configure RecyclerViews, Adapters, LayoutManager & glue it together

    private void configureRecyclerViewTP(){
        // Reset list
        results = new ArrayList<>();
        // Create adapter passing the list of TopStories articles
        topStoriesAdapter = new TopStoriesAdapter(this.results, Glide.with(this));
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(this.topStoriesAdapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureRecyclerViewMP(){
        // Reset list
        resultMPList = new ArrayList<>();
        // Create adapter passing the list of MostPopular articles
        mostPopularAdapter = new MostPopularAdapter(this.resultMPList, Glide.with(this));
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(this.mostPopularAdapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // Execute our Stream
    private void executeTopStoriesRequest(){
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStreams.fetchTopStories(NYTService.API_TOPSTORIES_SECTION)
                .subscribeWith(new DisposableObserver<NYTTopStories>() {
                    @Override
                    public void onNext(NYTTopStories topStories) {
                        Log.e("TAG","On Next");
                        // Update RecyclerView after getting results from New York Times API
                        updateUI(topStories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG","On Complete");
                    }
                });
    }

    // Execute our Stream
    private void executeMostPopularRequest(){
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStreams.fetchMostPopular(NYTService.API_PERIOD)
                .subscribeWith(new DisposableObserver<NYTMostPopular>() {
                    @Override
                    public void onNext(NYTMostPopular mostPopular) {
                        Log.e("TAG","On Next");
                        // Update UI with result of topStories
                        updateUIMP(mostPopular);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG","On Complete");
                    }
                });
    }

/*
    // Execute our Stream
    private void executeArticleSearchRequest(){
        // Update UI
        this.updateUIWhenStartingHTTPRequest(textViewArticleSearch);
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStreams.fetchArticleSearch(NYTService.API_FILTER_QUERY_SOURCE,
                NYTService.API_FILTER_QUERY_NEWS_DESK, NYTService.API_FILTER_SORT_ORDER)
                .subscribeWith(new DisposableObserver<NYTArticleSearch>() {
                    @Override
                    public void onNext(NYTArticleSearch articleSearch) {
                        Log.e("TAG","On Next");
                        // Update UI with result of topStories
                        updateUIWithArticleSearch(articleSearch);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG","On Complete");
                    }
                });
    }
*/

    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //  Update UI
    private void updateUI(NYTTopStories topStories){
        results.addAll(topStories.getResults());
        topStoriesAdapter.notifyDataSetChanged();
    }

    //  Update UI of MostPopular
    private void updateUIMP(NYTMostPopular mostPopular){
        resultMPList.addAll(mostPopular.getResults());
        mostPopularAdapter.notifyDataSetChanged();
    }


}
