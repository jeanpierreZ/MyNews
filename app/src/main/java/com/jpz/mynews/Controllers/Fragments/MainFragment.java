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
import com.jpz.mynews.Controllers.Utils.Service;
import com.jpz.mynews.Controllers.Utils.Streams;
import com.jpz.mynews.Models.API;
import com.jpz.mynews.Models.ModelAPI;
import com.jpz.mynews.Models.Result;
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
    private WebView webView;

    // For data
    private Disposable disposable;

    // Declare list of results & Adapter
    private List<Result> resultList;
    private AdapterAPI adapterAPI;

    // Create keys for our Bundle
    private static final String KEY_POSITION = "position";

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

        // Get WebView
        webView = view.findViewById(R.id.webview);

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
            // Update recyclerView with it
            // Load articles of NY Times when launching the app
            case TopStories:
                // Execute stream after UI creation
                executeTopStoriesRequest();
                break;
            case MostPopular:
                executeMostPopularRequest();
                break;
            case Foreign:
                executeArticleSearchRequest(Service.API_FILTER_FOREIGN);
                break;
            case Financial:
                executeArticleSearchRequest(Service.API_FILTER_FINANCIAL);
                break;
            case Technology:
                executeArticleSearchRequest(Service.API_FILTER_TECHNOLOGY);
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
        String url = adapterAPI.getPosition(position).getShortUrl();
        webView.loadUrl(url);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Configure RecyclerViews, Adapters, LayoutManager & glue it together

    private void configureRecyclerView(){
        // Reset list
        resultList = new ArrayList<>();
        // Create adapter passing the list of articles
        adapterAPI = new AdapterAPI(resultList, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(adapterAPI);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // Execute TopStories stream
    private void executeTopStoriesRequest(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = Streams.fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .subscribeWith(new DisposableObserver<ModelAPI>() {
                    @Override
                    public void onNext(ModelAPI modelAPI) {
                        Log.i("TAG","On Next TopStories");
                        // Update UI with result of Top Stories
                        updateUI(modelAPI);
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
        this.disposable = Streams.fetchMostPopular(Service.API_PERIOD)
                .subscribeWith(new DisposableObserver<ModelAPI>() {
                    @Override
                    public void onNext(ModelAPI modelAPI) {
                        Log.i("TAG","On Next MostPopular");
                        // Update UI with result of Most Popular
                        updateUI(modelAPI);
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
    private void executeArticleSearchRequest(String filter){
        // Execute the stream subscribing to Observable defined inside Stream
        int page = 0;

        this.disposable = Streams.fetchArticleSearch
                (Service.API_FACET_FIELDS, filter,
                        Service.API_FILTER_SORT_ORDER, page)
                .subscribeWith(new DisposableObserver<ModelAPI>() {
                    @Override
                    public void onNext(ModelAPI modelAPI) {
                        Log.i("TAG","On Next ArticleSearch");
                        // Update UI with a filter of ArticleSearch
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
    private void updateUI(ModelAPI modelAPI){
        resultList.addAll(modelAPI.getResultList());
        adapterAPI.notifyDataSetChanged();
    }

}
