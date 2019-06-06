package com.jpz.mynews.Controllers.Fragments;


import android.content.Context;
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
import com.jpz.mynews.Controllers.Utils.ConvertMethods;
import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.Controllers.Utils.MySharedPreferences;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterNews;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultQueryFragment extends NewsFragment implements AdapterNews.Listener {

    private MySharedPreferences prefs;

    private ConvertMethods convertMethods = new ConvertMethods();

    // Use for pagination
    private int page;

    // Use for research articles
    private String queryTerms;
    private String beginDate;
    private String endDate;

    // Create key for Bundle
    private static final String ARG_POSITION = "position";

    // Fields to detect more scrolling
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private int visibleItem, totalItems, firstVisibleItem;
    // The total number of items in the dataset after the last load. Starts from 0.
    private int previousTotal = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;

    public ResultQueryFragment() {
        // Required empty public constructor
    }

    public static ResultQueryFragment newInstance(int position) {
        // Create fragment and give it an argument specifying the article it should show
        ResultQueryFragment resultQueryFragment = new ResultQueryFragment();
        Bundle args = new Bundle();
        args.putInt(ResultQueryFragment.ARG_POSITION, position);
        resultQueryFragment.setArguments(args);
        return(resultQueryFragment);
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

                    if (loading) {
                        if (totalItems > previousTotal) {
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
        Context context = getActivity();
        if (context != null) {
            prefs = new MySharedPreferences(context.getApplicationContext());
        }
        // Get the query terms to research
        queryTerms = prefs.getQueryTerms();
        Log.i("TAG", "queryTerms : "+ queryTerms);

        String beginDateBeforeConvert = prefs.getBeginDate();
        beginDate = convertMethods.convertBeginOrEndDate(beginDateBeforeConvert);
        Log.i("TAG", "beginDate : "+ beginDate);

        /*
        // Get data from Bundle (created in method newInstance)
        if (getArguments() != null) {
            Desk desk = (Desk) getArguments().getSerializable(KEY_POSITION);
          */

            //if (desk != null)
                // Execute the stream subscribing to Observable defined inside APIClient
                this.disposable = APIClient.getResearchWithTerms
                        ("news_desk:(" + Desk.Business.toDesk() +")", page, queryTerms, beginDate, endDate)
                        .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                            @Override
                            public void onNext(List<GenericNews> genericNewsList) {
                                Log.i("TAG", "On Next ResultQueryFragment");
                                // Update UI with a list of ArticleSearch
                                updateUI(genericNewsList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("TAG", "On Error ResultQueryFragment"
                                        + Log.getStackTraceString(e));
                            }

                            @Override
                            public void onComplete() {
                                Log.i("TAG", "On Complete ResultQueryFragment");
                            }
                        });
        //}
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
