package com.jpz.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jpz.mynews.Controllers.Utils.APIClient;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.R;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends NewsFragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    // For data
    private Disposable disposable;

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

    }

    @Override
    public List fetchNews(int page) {
        return fetchNews(1);
    }

    public MostPopularFragment() {
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
        executeMostPopularRequest();

        return view;
    }

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
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


}
