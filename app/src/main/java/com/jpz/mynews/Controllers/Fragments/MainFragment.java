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

import com.jpz.mynews.Controllers.Utils.NYTService;
import com.jpz.mynews.Controllers.Utils.NYTStreams;
import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.NYTArticleSearch;
import com.jpz.mynews.Models.NYTMostPopular;
import com.jpz.mynews.Models.NYTResult;
import com.jpz.mynews.Models.NYTResultMP;
import com.jpz.mynews.Models.NYTTopStories;
import com.jpz.mynews.R;
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

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.fragment_main_recycler_view);

        // Load articles of NY Times when launching the app
        configureRecyclerView(); // Call during UI creation
        executeTopStoriesRequest(); // Execute stream after UI creation
        //executeMostPopularRequest();
        //executeArticleSearchRequest();

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

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // Reset list
        results = new ArrayList<>();
        // Create adapter passing the list of users
        topStoriesAdapter = new TopStoriesAdapter(this.results);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(this.topStoriesAdapter);
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

    /*
    // Execute our Stream
    private void executeMostPopularRequest(){
        // Update UI
        this.updateUIWhenStartingHTTPRequest(textViewMostPopular);
        // Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStreams.fetchMostPopular(NYTService.API_PERIOD)
                .subscribeWith(new DisposableObserver<NYTMostPopular>() {
                    @Override
                    public void onNext(NYTMostPopular mostPopular) {
                        Log.e("TAG","On Next");
                        // Update UI with result of topStories
                        updateUIWithMostPopular(mostPopular);
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

}
