package com.jpz.mynews.controllers.fragments;


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

import com.jpz.mynews.controllers.utils.APIClient;
import com.jpz.mynews.controllers.utils.Desk;
import com.jpz.mynews.models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.controllers.adapters.AdapterNews;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleSearchFragment extends NewsFragment implements AdapterNews.Listener {

    // Create a key for Bundle, use to instantiate the fragment with a desk
    private static final String KEY_POSITION = "position";

    // Fields to detect more scrolling
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private int visibleItem, totalItems, firstVisibleItem;
    // The total number of items in the data set after the last load. Start from 0.
    private int previousTotal = 0;
    // Loading is true if we are still waiting for the last set of data to load.
    private boolean loading = true;

    public ArticleSearchFragment() {
        // Required empty public constructor
    }

    public static ArticleSearchFragment newInstance(Desk desk) {
        // Create new fragment
        ArticleSearchFragment fragment = new ArticleSearchFragment();
        // Create bundle and add it the desk
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

                    // If the size of the list is less than 10 (pagination size from the API),
                    // it's useless to load another page
                    if (totalItems < 10)
                        loading = false;
                    else {
                        // If totalItems is less than previousTotal (because of SwipeRefresh), the
                        // list is invalidated and should be reset back to initial state
                        if (totalItems < previousTotal) {
                            previousTotal = totalItems;
                            loading = true;
                        }

                        // Data are loading and previousTotal is actualized with totalItems
                        if ((loading) && (totalItems > previousTotal)) {
                            loading = false;
                            previousTotal = totalItems;
                        }

                        // Data aren't loading and end of the list has been reached, so call next page
                        if (!loading && (totalItems - visibleItem) <= firstVisibleItem) {
                            fetchNextPage();
                            loading = true;
                        }
                    }
                }
            }
        });
        return view;
    }

    // HTTP (RxJAVA)
    @Override
    protected void fetchData() {
        // Get data from Bundle (created in newInstance)
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
                            // Update UI with the list of ArticleSearch
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

    // Load the next page when end of the list has been reached
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