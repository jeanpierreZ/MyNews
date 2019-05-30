package com.jpz.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.jpz.mynews.Controllers.Utils.APIClient;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterAPI;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleSearchFragment extends NewsFragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterAPI adapterAPI;

    // For data
    private Disposable disposable;
    private int page;

    @Override
    protected void updateRecyclerView() {

    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void updateOnClickItem() {

    }

    @Override
    protected void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                executeArticleSearchRequest(++page);
                adapterAPI.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }

    @Override
    public List fetchNews(int page) {
        return fetchNews(2);
    }

    public ArticleSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Get RecyclerView from layout and serialise it
        recyclerView = view.findViewById(R.id.fragment_news_recycler_view);

        // Get ProgressBar
        progressBar = view.findViewById(R.id.fragment_news_progressbar);

        configureRecyclerView();
        executeArticleSearchRequest(page);

        return view;
    }

    public static ArticleSearchFragment newInstance() {
        return (new ArticleSearchFragment());
    }

    // Execute ArticleSearch stream
    private void executeArticleSearchRequest(int page){
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = APIClient.fetchSearchToGeneric(page)
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

}
