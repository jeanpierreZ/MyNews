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
import com.jpz.mynews.Controllers.Utils.GetData;
import com.jpz.mynews.Controllers.Utils.Service;
import com.jpz.mynews.Controllers.Utils.Streams;
import com.jpz.mynews.Models.API;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.Models.APIClient;
import com.jpz.mynews.Models.MostPopular;
import com.jpz.mynews.Models.Result;
import com.jpz.mynews.Models.TopStories;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Result> resultList;
    private AdapterAPI adapterAPI;
    private List<GenericNews> genericNewsList;

    private List<TopStories> topStoriesList = new ArrayList<>();
    private MostPopular mostPopular = new MostPopular();


    //private GenericNews genericNews = new GenericNews();

    private GetData getData;

    private APIClient apiClient;
    private API api;

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
                //executeArticleSearchRequest(Service.API_FILTER_FOREIGN);
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
        this.disposable = Streams.fetchTopStories(Service.API_TOPSTORIES_SECTION)
                .subscribeWith(new DisposableObserver<APIClient>() {
                    @Override
                    public void onNext(APIClient apiClient) {
                        Log.i("TAG","On Next TopStories");
                        // Update UI with result of Top Stories

                        List<GenericNews> list = new ArrayList<>();

                        GenericNews genericNews = new GenericNews();

                        genericNews.title = apiClient.getResultList().get(0).getTitle();
                        Log.i("TAG","title :" + genericNews.title);
                        genericNews.date = apiClient.getResultList().get(0).getPublishedDate();
                        Log.i("TAG","date :" + genericNews.date);
                        genericNews.section = apiClient.getResultList().get(0).getSection();
                        Log.i("TAG","section :" + genericNews.section);
                        genericNews.image = apiClient.getResultList().get(0).getMultimedia().get(0).getUrl();
                        genericNews.url = apiClient.getResultList().get(0).getShortUrl();

                        list.add(genericNews);


                        Log.i("TAG","la liste :" + list);

                        updateUI(list);
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



    /*

    // Execute TopStories stream
    private void generifyTopStoriesRequest(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = Streams.generifyTopStories()
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG","On Next TopStories liste : " + genericNewsList);
                        // Update UI with result of Top Stories

                        //updateUI(genericNewsList);
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


    // Execute TopStories stream
    private void topToString(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = Streams.topStoriesToString()
                .subscribeWith(new DisposableObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> list) {
                        Log.i("TAG","On Next TopStories" + " liste :" + list);
                        // Update UI with result of Top Stories

                        List<GenericNews> genericNewsList = new ArrayList<>();



                        Log.i("TAG","genericNewsList : " + genericNewsList);

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


    // Execute TopStories stream
    private void topToList(){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = Streams.topStoriesToList()
                .subscribeWith(new DisposableObserver<List<TopStories>>() {
                    @Override
                    public void onNext(List<TopStories> list) {
                        Log.i("TAG","On Next TopStories" + "liste :" + list);
                        // Update UI with result of Top Stories


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
*/

        // Execute MostPopular stream
        private void executeMostPopularRequest(){
            // Execute the stream subscribing to Observable defined inside Stream
            this.disposable = Streams.fetchMostPopular(Service.API_PERIOD)
                    .subscribeWith(new DisposableObserver<APIClient>() {
                        @Override
                        public void onNext(APIClient apiClient) {
                            Log.i("TAG","On Next MostPopular");
                            // Update UI with result of Most Popular

                            List<MostPopular> mostPopularList = new ArrayList<>();

                            mostPopular.title(apiClient);
                            mostPopular.date(apiClient);
                            mostPopular.sectionSubsection(apiClient);
                            mostPopular.image(apiClient);
                            mostPopular.url(apiClient);

                            Log.i("TAG","title :" + mostPopular.title(apiClient));

                            /*
                            genericNewsList = mostPopularList.stream()
                                    .filter(elt -> elt != null)
                                    .map(elt -> doSomething(elt))
                                    .collect(Collectors.toList());
                                    */

                            //updateUI();
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

        /*
        // Execute ArticleSearch stream
        private void executeArticleSearchRequest(String filter){
            // Execute the stream subscribing to Observable defined inside Stream
            int page = 0;

            this.disposable = Streams.fetchArticleSearch
                    (Service.API_FACET_FIELDS, filter,
                            Service.API_FILTER_SORT_ORDER, page)
                    .subscribeWith(new DisposableObserver<APIClient>() {
                        @Override
                        public void onNext(APIClient apiClient) {
                            Log.i("TAG","On Next ArticleSearch");
                            // Update UI with a filter of ArticleSearch
                            updateUI(apiClient);

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
    */
    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //  Update UI for Top Stories
    private void updateUI(List<GenericNews> list){
        genericNewsList.addAll(list);
        adapterAPI.notifyDataSetChanged();
    }




}
