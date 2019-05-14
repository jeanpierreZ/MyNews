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
import android.webkit.WebView;

import com.bumptech.glide.Glide;
import com.jpz.mynews.Controllers.Utils.GetData;
import com.jpz.mynews.Controllers.Utils.Service;
import com.jpz.mynews.Controllers.Utils.Streams;
import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.ModelAPI;
import com.jpz.mynews.Models.Result;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.ArticleSearchAdapter;
import com.jpz.mynews.Views.MostPopularAdapter;
import com.jpz.mynews.Views.AdapterAPI;
import com.jpz.mynews.Views.TopStoriesAdapter;

import java.util.ArrayList;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements AdapterAPI.Listener {

    private RecyclerView recyclerView;
    private WebView webView;

    // For data
    private Disposable disposable;

    // Declare list of results (Result & Doc) & Adapter
    private List<Result> resultList;
    private TopStoriesAdapter topStoriesAdapter;
    private MostPopularAdapter mostPopularAdapter;

    private List<Doc> docList;
    private ArticleSearchAdapter articleSearchAdapter;

    private List<GetData> getDataList;
    private AdapterAPI adapterAPI;

    private ModelAPI modelAPI;

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
                configureRecyclerView();
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

        String url = adapterAPI.getPosition(position).getDataList.get(0).shorturl();
        webView.loadUrl(url);

        /*
        String url = topStoriesAdapter.getPosition(position).getShortUrl();
        webView.loadUrl(url);
        */
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Configure RecyclerViews, Adapters, LayoutManager & glue it together
/*
    private void configureRecyclerViewTP(){
        // Reset list
        resultList = new ArrayList<>();
        // Create adapter passing the list of Top Stories articles and a reference of callback
        topStoriesAdapter = new TopStoriesAdapter(this.resultList, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(this.topStoriesAdapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
*/

    private void configureRecyclerView(){
        // Reset list
        getDataList = new ArrayList<>();
        // Create adapter passing the list of Top Stories articles
        adapterAPI = new AdapterAPI(this.getDataList, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(this.adapterAPI);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private void configureRecyclerViewMP(){
        // Reset list
        resultList = new ArrayList<>();
        // Create adapter passing the list of Most Popular articles
        mostPopularAdapter = new MostPopularAdapter(this.resultList, Glide.with(this));
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(this.mostPopularAdapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureRecyclerArticleSearch(){
        // Reset list
        docList = new ArrayList<>();
        // Create adapter passing the list of Article Search articles
        articleSearchAdapter = new ArticleSearchAdapter(this.docList, Glide.with(this));
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(this.articleSearchAdapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    // Execute our Stream
    private void executeTopStoriesRequest(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = Streams.fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .subscribeWith(new DisposableObserver<ModelAPI>() {
                    @Override
                    public void onNext(ModelAPI modelAPI) {
                        Log.i("TAG","On Next TopStories");
                        // Update UI with result of Top Stories
                        updateUITopStories(modelAPI);
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

    // Execute our Stream
    private void executeMostPopularRequest(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = Streams.fetchMostPopular(Service.API_PERIOD)
                .subscribeWith(new DisposableObserver<ModelAPI>() {
                    @Override
                    public void onNext(ModelAPI modelAPI) {
                        Log.i("TAG","On Next MostPopular");
                        // Update UI with result of Most Popular
                        updateUIMostPopular(modelAPI);
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

    // Execute our Stream
    private void executeForeignRequest(){
        // Execute the stream subscribing to Observable defined inside Stream

        // Loop to display 40 items of result in ArticleSearch
        int page = 0;
        //for (page = 0; page < 4; page++)

            this.disposable = Streams.fetchArticleSearch
                    (Service.API_FACET_FIELDS, Service.API_FILTER_FINANCIAL,
                            Service.API_FILTER_SORT_ORDER, page)
                    .subscribeWith(new DisposableObserver<ModelAPI>() {
                        @Override
                        public void onNext(ModelAPI modelAPI) {
                            Log.i("TAG","On Next Foreign");
                            // Update UI with result of Technology

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG","On Error Foreign" + Log.getStackTraceString(e));
                        }

                        @Override
                        public void onComplete() {
                            Log.i("TAG","On Complete Foreign");
                        }
                    });
    }

    // Execute our Stream
    private void executeFinancialRequest(){
        // Execute the stream subscribing to Observable defined inside Stream

        // Loop to display 40 items of result in ArticleSearch
        int page = 0;
        //for (page = 0; page < 4; page++)

        this.disposable = Streams.fetchArticleSearch
                (Service.API_FACET_FIELDS, Service.API_FILTER_FOREIGN,
                Service.API_FILTER_SORT_ORDER, page)
                .subscribeWith(new DisposableObserver<ModelAPI>() {
                    @Override
                    public void onNext(ModelAPI modelAPI) {
                        Log.i("TAG","On Next Financial");
                        // Update UI with result of Technology
                        updateUIArticleSearch(modelAPI);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error Financial" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG","On Complete Financial");
                    }
                });
    }

    // Execute our Stream
    private void executeTechnologyRequest(){
        // Execute the stream subscribing to Observable defined inside Stream

        // Loop to display 40 items of result in ArticleSearch
        int page= 0;
        //for (page = 0; page < 4; page++)

            this.disposable = Streams.fetchArticleSearch
                    (Service.API_FACET_FIELDS, Service.API_FILTER_TECHNOLOGY,
                            Service.API_FILTER_SORT_ORDER, page)
                    .subscribeWith(new DisposableObserver<ModelAPI>() {
                        @Override
                        public void onNext(ModelAPI modelAPI) {
                            Log.i("TAG","On Next Technology");
                            // Update UI with result of Technology
                            updateUIArticleSearch(modelAPI);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG","On Error Technology" + Log.getStackTraceString(e));
                        }

                        @Override
                        public void onComplete() {
                            Log.i("TAG","On Complete Technology");
                        }
                    });
    }

    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //  Update UI for Top Stories
    private void updateUITopStories(ModelAPI modelAPI){
        getDataList.addAll(modelAPI.getDataList());
        adapterAPI.notifyDataSetChanged();
    }

    //  Update UI for Most Popular
    private void updateUIMostPopular(ModelAPI modelAPI){
        resultList.addAll(modelAPI.getResultList());
        mostPopularAdapter.notifyDataSetChanged();
    }

    //  Update UI for Article Search
    private void updateUIArticleSearch(ModelAPI modelAPI){
        docList.addAll(modelAPI.getResponse().getDocs());
        articleSearchAdapter.notifyDataSetChanged();
    }
}
