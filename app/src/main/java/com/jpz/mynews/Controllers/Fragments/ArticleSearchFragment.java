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

    private int page;

    // Create keys for Bundle
    private static final String KEY_POSITION = "position";

    // Fields (Boolean) to detect more scrolling
    protected LinearLayoutManager layoutManager;
    protected ProgressBar progressBar;
    protected boolean loadMore = false;
    protected int currentItems, totalItems, scrollOutItems;

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
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    loadMore = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (layoutManager != null) {
                    currentItems = layoutManager.getChildCount();
                    totalItems = layoutManager.getItemCount();
                    scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                    if (loadMore && currentItems + scrollOutItems == totalItems)
                        // Data fetch
                        loadMore = false;
                    fetchNextPage();
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

            if (desk != null)
            // Execute the stream subscribing to Observable defined inside APIClient
            this.disposable = APIClient.getArticleSearchNews(desk.toDesk(), page)
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
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = ++page;
                fetchData();
                adapterNews.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }
}
