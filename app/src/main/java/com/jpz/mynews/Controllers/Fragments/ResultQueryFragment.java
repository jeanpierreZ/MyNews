package com.jpz.mynews.Controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
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
import com.jpz.mynews.Controllers.Adapters.AdapterNews;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

import static com.jpz.mynews.Controllers.Activities.SearchActivity.KEY_SEARCH_QUERY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultQueryFragment extends NewsFragment implements AdapterNews.Listener {

    private ConvertMethods convertMethods = new ConvertMethods();
    private SearchQuery searchQuery = new SearchQuery();

    // Fields to detect more scrolling
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private int visibleItem, totalItems, firstVisibleItem;
    // The total number of items in the data set after the last load. Start from 0.
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

                    // If the size of the list is less than 10 (pagination size from the API),
                    // it's useless to load another page
                    if (totalItems < 10)
                        loading = false;

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
        });
        return view;
    }

    // HTTP (RxJAVA)
    @Override
    protected void fetchData() {
        // Get context from the activity
        final Context context = getActivity();

        // Get SearchQuery values for the research
        if (getArguments() != null)
            searchQuery = (SearchQuery) getArguments().getSerializable(KEY_SEARCH_QUERY);

        // Just for tags
        if (searchQuery != null) {
            Log.i("TAG", "ResultQuery queryTerms : "+ searchQuery.queryTerms);
            Log.i("TAG", "ResultQuery Page : " + page);
            Log.i("TAG", "ResultQuery dateAfterConversion : begin "
                    + fetchDate(searchQuery.beginDate) + " end " + fetchDate(searchQuery.endDate));
            Log.i("TAG", "ResultQuery desks : " +
                    searchQuery.desks[0]+searchQuery.desks[1]+searchQuery.desks[2]+
                    searchQuery.desks[3]+searchQuery.desks[4]+searchQuery.desks[5]);
        }

        if (searchQuery != null)
        // Execute the stream subscribing to Observable defined inside APIClient
        this.disposable = APIClient.getArticleSearchNews
                (fetchDesksValues(searchQuery.desks), page, searchQuery.queryTerms,
                        fetchDate(searchQuery.beginDate), fetchDate(searchQuery.endDate))
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
                                // Else update UI with the result list of ArticleSearch
                                updateUI(genericNewsList);
                        }
                        // For following pages, update UI with a list of ArticleSearch
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

    // Load the next page when end of the list has been reached
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

    // Convert the begin or end date from DatePickerDialog to research
    @VisibleForTesting()
    public String fetchDate(String beginOrEndDate) {
        if (beginOrEndDate != null) {
            // If there is no date saved, set null
            if (beginOrEndDate.equals(""))
                beginOrEndDate = null;
            else {
                beginOrEndDate = convertMethods.convertDateToSearch(beginOrEndDate);
            }
        }
        return beginOrEndDate;
    }

    // Formatting desks chosen for the request
    @VisibleForTesting()
    public String fetchDesksValues(String[] desks) {
        String formatDesksValue = "";
        if (desks != null)
            formatDesksValue = "news_desk:(\"" + desks[0] + "\" \"" + desks[1]
                    + "\" \"" + desks[2] + "\" \"" + desks[3]
                    + "\" \"" + desks[4] + "\" \"" + desks[5] +"\")";

        return formatDesksValue;
    }
}
