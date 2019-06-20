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
import android.widget.ProgressBar;

import com.jpz.mynews.Controllers.Utils.APIClient;
import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterNews;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleSearchFragment extends NewsFragment implements AdapterNews.Listener {

    // Use for pagination
    //private int page;
    private String query;
    private String beginDate;
    private String endDate;

    // Create keys for Bundle
    private static final String KEY_POSITION = "position";

    // Fields to detect more scrolling
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private int visibleItem, totalItems, firstVisibleItem;
    // The total number of items in the dataset after the last load. Starts from 0.
    private int previousTotal = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;

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
        // Call the onCreateView's parent in NewsFragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view != null)
        // Get ProgressBar
        progressBar = view.findViewById(R.id.fragment_news_progressbar);

        // Add On Scroll Listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (layoutManager != null) {
                    visibleItem = layoutManager.getChildCount();
                    totalItems = layoutManager.getItemCount();
                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    // If the size of the result list is less than 10 (pagination size from the API),
                    // it's useless to load another page
                    if (totalItems < 10)
                        loading = false;
                    if (totalItems < previousTotal) {
                        previousTotal = totalItems;
                        loading = true;
                    }
                    else {
                        if ((loading) && (totalItems > previousTotal)) {
                            loading = false;
                            previousTotal = totalItems;
                            }
                        }
                        if (!loading && (totalItems - visibleItem) <= firstVisibleItem) {
                            // End has been reached
                            fetchNextPage();
                            loading = true;
                        }
                }
            }
        });
        return view;
    }

    @Override
    protected void fetchData() {
        // Get data from Bundle (created in method newInstance)
        if (getArguments() != null) {
            Desk desk = (Desk) getArguments().getSerializable(KEY_POSITION);

            Log.i("TAG", "On Next ArticleSearch Page : " + page);

            if (desk != null)
            // Execute the stream subscribing to Observable defined inside APIClient
            this.disposable = APIClient.getArticleSearchNews
                    ("news_desk:(" + desk.toDesk() +")", page, query, beginDate, endDate)
                    .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                        @Override
                        public void onNext(List<GenericNews> genericNewsList) {
                            Log.i("TAG", "On Next ArticleSearch");
                            // Update UI with a list of ArticleSearch
                            updateUI(genericNewsList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG", "On Error ArticleSearch" + Log.getStackTraceString(e));
                        }

                        @Override
                        public void onComplete() {
                            Log.i("TAG", "On Complete ArticleSearch");
                        }
                    });
        }
    }

    private void fetchNextPage() {
        Log.i("TAG", "fetchNextPage");
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = ++page;
                fetchData();
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }
}
