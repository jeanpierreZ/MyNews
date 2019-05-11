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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

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
import com.jpz.mynews.Views.ArticleSearchAdapter;
import com.jpz.mynews.Views.MostPopularAdapter;
import com.jpz.mynews.Views.TopStoriesAdapter;

import java.util.ArrayList;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements TopStoriesAdapter.Listener {

    private RecyclerView recyclerView;
    private WebView webView;

    // For data
    private Disposable disposable;

    // Declare list of results (NYTResult) & Adapter
    private List<NYTResult> results;
    private TopStoriesAdapter topStoriesAdapter;

    // Declare list of results (NYTResultMP) & Adapter
    private List<NYTResultMP> resultMPList;
    private MostPopularAdapter mostPopularAdapter;

    // Declare list of results (Doc) & Adapter
    private List<Doc> docs;
    private ArticleSearchAdapter articleSearchAdapter;

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

        // Get WebView
        webView = view.findViewById(R.id.webview);

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
            case 2 :
                configureRecyclerArticleSearch();
                executeForeignRequest();
                break;
            case 3 :
                configureRecyclerArticleSearch();
                executeFinancialRequest();
                break;
            case 4 :
                configureRecyclerArticleSearch();
                executeTechnologyRequest();
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
    public void onClickTitle(int position) {
        // Get the position of the item in the RecyclerView and load it
        String url = topStoriesAdapter.getPosition(position).getShortUrl();
        webView.loadUrl(url);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Configure RecyclerViews, Adapters, LayoutManager & glue it together

    private void configureRecyclerViewTP(){
        // Reset list
        results = new ArrayList<>();
        // Create adapter passing the list of TopStories articles and a reference of callback
        topStoriesAdapter = new TopStoriesAdapter(this.results, Glide.with(this), this);
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

    private void configureRecyclerArticleSearch(){
        // Reset list
        docs = new ArrayList<>();
        // Create adapter passing the list of MostPopular articles
        articleSearchAdapter = new ArticleSearchAdapter(this.docs, Glide.with(this));
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(this.articleSearchAdapter);
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
                        // Update UI with result of Top Stories
                        updateUITopStories(topStories);
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
                        // Update UI with result of Most Popular
                        updateUIMostPopular(mostPopular);
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
    private void executeForeignRequest(){
        // Execute the stream subscribing to Observable defined inside NYTStream

        // Loop to display 40 items of result in ArticleSearch
        int page;
        for (page = 0; page < 4; page++)

            this.disposable = NYTStreams.fetchArticleSearch
                    (NYTService.API_FACET_FIELDS, NYTService.API_FILTER_FINANCIAL,
                            NYTService.API_FILTER_SORT_ORDER, page)
                    .subscribeWith(new DisposableObserver<NYTArticleSearch>() {
                        @Override
                        public void onNext(NYTArticleSearch articleSearch) {
                            Log.e("TAG","On Next");
                            // Update UI with result of Technology
                            updateUIArticleSearch(articleSearch);
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
    private void executeFinancialRequest(){
        // Execute the stream subscribing to Observable defined inside NYTStream

        // Loop to display 40 items of result in ArticleSearch
        int page;
        for (page = 0; page < 4; page++)

        this.disposable = NYTStreams.fetchArticleSearch
                (NYTService.API_FACET_FIELDS, NYTService.API_FILTER_FOREIGN,
                NYTService.API_FILTER_SORT_ORDER, page)
                .subscribeWith(new DisposableObserver<NYTArticleSearch>() {
                    @Override
                    public void onNext(NYTArticleSearch articleSearch) {
                        Log.e("TAG","On Next");
                        // Update UI with result of Technology
                        updateUIArticleSearch(articleSearch);
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
    private void executeTechnologyRequest(){
        // Execute the stream subscribing to Observable defined inside NYTStream

        // Loop to display 40 items of result in ArticleSearch
        int page;
        for (page = 0; page < 4; page++)

            this.disposable = NYTStreams.fetchArticleSearch
                    (NYTService.API_FACET_FIELDS, NYTService.API_FILTER_TECHNOLOGY,
                            NYTService.API_FILTER_SORT_ORDER, page)
                    .subscribeWith(new DisposableObserver<NYTArticleSearch>() {
                        @Override
                        public void onNext(NYTArticleSearch articleSearch) {
                            Log.e("TAG","On Next");
                            // Update UI with result of Technology
                            updateUIArticleSearch(articleSearch);
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

    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //  Update UI for TopStories
    private void updateUITopStories(NYTTopStories topStories){
        results.addAll(topStories.getResults());
        topStoriesAdapter.notifyDataSetChanged();
    }

    //  Update UI for MostPopular
    private void updateUIMostPopular(NYTMostPopular mostPopular){
        resultMPList.addAll(mostPopular.getResults());
        mostPopularAdapter.notifyDataSetChanged();
    }

    //  Update UI for Technology ArticleSearch
    private void updateUIArticleSearch(NYTArticleSearch articleSearch){
        docs.addAll(articleSearch.getResponse().getDocs());
        articleSearchAdapter.notifyDataSetChanged();
    }
}
