package com.jpz.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpz.mynews.Controllers.Utils.APIClient;
import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterAPI;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleSearchFragment extends NewsFragment implements AdapterAPI.Listener {

    // Create keys for Bundle
    private static final String KEY_POSITION = "position";

    public ArticleSearchFragment() {
        // Required empty public constructor
    }

    public static ArticleSearchFragment newInstance(Desk desk) {

        // Create new fragment
        ArticleSearchFragment fragment = new ArticleSearchFragment();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putSerializable(KEY_POSITION, desk);
        fragment.setArguments(args);

        return(fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout of this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Get RecyclerView from layout and serialise it
        recyclerView = view.findViewById(R.id.fragment_news_recycler_view);

        // Get ProgressBar
        progressBar = view.findViewById(R.id.fragment_news_progressbar);

        // Call during UI creation
        configureRecyclerView();

        // Get data from Bundle (created in method newInstance)
        if (getArguments() == null) {
            return view;
        }

        // Get data from Bundle
        Desk desk = (Desk) getArguments().getSerializable(KEY_POSITION);
        if (desk != null)
            switch (desk) {
                // Load desk articles of ArticleSearch API when launching the app
                // Execute streams after UI creation
                case Foreign:
                    executeRequest(desk.toDesk(), page);
                    break;
                case Financial:
                    executeRequest(desk.toDesk(), page);
                    break;
                case Technology:
                    executeRequest(desk.toDesk(), page);
                    break;
            }

        return view;
    }

    @Override
    protected void executeRequest(String desk, int page) {
        // Execute the stream subscribing to Observable defined inside Stream
        this.disposable = APIClient.getArticleSearchNews(desk, page)
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

    @Override
    protected void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                executeRequest(desk, ++page);
                adapterAPI.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }
}
