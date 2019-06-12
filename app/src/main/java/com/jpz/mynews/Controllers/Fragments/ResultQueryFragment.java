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
import android.widget.Toast;

import com.jpz.mynews.Controllers.Utils.APIClient;
import com.jpz.mynews.Controllers.Utils.ConvertMethods;
import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.AdapterNews;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

import static com.jpz.mynews.Controllers.Activities.SearchActivity.KEY_BEGIN_DATE;
import static com.jpz.mynews.Controllers.Activities.SearchActivity.KEY_DESKS;
import static com.jpz.mynews.Controllers.Activities.SearchActivity.KEY_END_DATE;
import static com.jpz.mynews.Controllers.Activities.SearchActivity.KEY_QUERY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultQueryFragment extends NewsFragment implements AdapterNews.Listener {

    private ConvertMethods convertMethods = new ConvertMethods();
    private SearchQuery searchQuery = new SearchQuery();

    // Use for pagination
    private int page;

    // Fields used for the research of articles in request
    private String beginDateAfterConversion;
    private String endDateAfterConversion;
    private String selectedDesks;

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
                    else {
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
            }
        });
        return view;
    }

    @Override
    protected void fetchData() {

        final Context context = getActivity();

        // Call methods to fetch fields for the request
        fetchQueryTerms();
        fetchBeginDate();
        fetchEndDate();
        fetchDesks();

        // Execute the stream subscribing to Observable defined inside APIClient
        this.disposable = APIClient.getArticleSearchNews
                (selectedDesks, page, searchQuery.queryTerms, beginDateAfterConversion, endDateAfterConversion)
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG", "On Next ResultQueryFragment");
                        // Check if there is no result in the first page of the list...
                        if (page == 0) {
                            if (genericNewsList.size() == 0)
                                // ...and inform the user
                                Toast.makeText(context,
                                        "There is no result for your request \""+searchQuery.queryTerms
                                                +"\" from "+searchQuery.beginDate+" to "
                                                +searchQuery.endDate+" with the categories chosen.",
                                        Toast.LENGTH_LONG).show();
                            else
                                // Else update UI with a list of ArticleSearch
                                updateUI(genericNewsList);
                        }
                        // Update UI with a list of ArticleSearch for following pages
                        else
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
    }

    private void fetchNextPage() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // When end of list is reached, fetch next page
                page = ++page;
                fetchData();
                adapterNews.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private void fetchQueryTerms() {
        // Get the query terms to research
        if (getArguments() != null)
            searchQuery.queryTerms = getArguments().getString(KEY_QUERY);
        Log.i("TAG", "RESULT queryTerms : "+ searchQuery.queryTerms);
    }

    private void fetchBeginDate() {
        // Get the begin date to research
        if (getArguments() != null)
            searchQuery.beginDate = getArguments().getString(KEY_BEGIN_DATE);

        // If there is no date saved, set ""
        if (searchQuery.beginDate != null) {
            if (searchQuery.beginDate.equals(""))
                beginDateAfterConversion = null;
            else {
                beginDateAfterConversion = convertMethods.convertBeginOrEndDate(searchQuery.beginDate);
                Log.i("TAG", "RESULT beginDate : "+ beginDateAfterConversion);
            }
        }
    }

    private void fetchEndDate() {
        // Get the end date to research
        if (getArguments() != null)
            searchQuery.endDate = getArguments().getString(KEY_END_DATE);

        // If there is no date saved, set ""
        if (searchQuery.endDate != null) {
            if (searchQuery.endDate.equals(""))
                endDateAfterConversion = null;
            else {
                endDateAfterConversion = convertMethods.convertBeginOrEndDate(searchQuery.endDate);
                Log.i("TAG", "RESULT endDate : " + searchQuery.endDate);
            }
        }
    }

    private void fetchDesks() {
        // Get desk values ro research
        if (getArguments() != null)
            searchQuery.desks = getArguments().getStringArray(KEY_DESKS);

        // Formatting desks chosen for the request
        if (searchQuery.desks != null) {
            Log.i("TAG", "RESULT desks : " +
                    searchQuery.desks[0]+searchQuery.desks[1]+searchQuery.desks[2]+
                    searchQuery.desks[3]+searchQuery.desks[4]+searchQuery.desks[5]);
            selectedDesks =
                    "news_desk:(\"" + searchQuery.desks[0] + "\" \"" + searchQuery.desks[1] +
                            "\" \"" + searchQuery.desks[2] + "\" \"" + searchQuery.desks[3] +
                            "\" \"" + searchQuery.desks[4] + "\" \"" + searchQuery.desks[5] +"\")";
        }
    }
}
