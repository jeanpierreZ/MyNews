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

    // Fields used for the research of articles
    private String queryTerms;
    private String beginDate;
    private String endDate;
    private String beginDateBeforeConvert;
    private String endDateBeforeConvert;
    private String desk;

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
        // Get context for sharedPreferences
        final Context context = getActivity();
        if (context != null) {
            prefs = new MySharedPreferences(context);
        }

        // Call methods to fetch fields for the request
        fetchQueryTerms();
        fetchBeginDate();
        fetchEndDate();
        fetchDesks();

        // Execute the stream subscribing to Observable defined inside APIClient
        this.disposable = APIClient.getArticleSearchNews
                (desk, page, queryTerms, beginDate, endDate)
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG", "On Next ResultQueryFragment");
                        // Check if there is no result in the first page of the list...
                        if (page == 0) {
                            if (genericNewsList.size() == 0)
                                // ...and inform the user
                                Toast.makeText(context,
                                        "There is no result for your request \""+queryTerms
                                                +"\" from "+beginDateBeforeConvert+" to "
                                                +endDateBeforeConvert+" with the categories chosen.",
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
        queryTerms = prefs.getQueryTerms();
        Log.i("TAG", "queryTerms : "+ queryTerms);
    }

    private void fetchBeginDate() {
        // Get the begin date to research
        beginDateBeforeConvert = prefs.getBeginDate();
        // If there is no date saved, set ""
        if (beginDateBeforeConvert.equals(""))
            beginDate = null;
        else {
            beginDate = convertMethods.convertBeginOrEndDate(beginDateBeforeConvert);
            Log.i("TAG", "beginDate : "+ beginDate);
        }
    }

    private void fetchEndDate() {
        // Get the end date to research
        endDateBeforeConvert = prefs.getEndDate();
        // If there is no date saved, set ""
        if (endDateBeforeConvert.equals(""))
            endDate = null;
        else {
            endDate = convertMethods.convertBeginOrEndDate(endDateBeforeConvert);
            Log.i("TAG", "endDate : " + endDate);
        }
    }

    private void fetchDesks() {
        // Get desk values from boxes
        String[] desks = prefs.getBoxesValues();
        // Add value form each desk
        String deskOne = desks[0];
        String deskTwo = desks[1];
        String deskThree = desks[2];
        String deskFour = desks[3];
        String deskFive = desks[4];
        String deskSix = desks[5];
        Log.i("TAG", "desks : " +deskOne+deskTwo+deskThree+deskFour+deskFive+deskSix);
        // Formatting desks chosen for the request
        desk = "news_desk:(\"" + deskOne + "\" \"" + deskTwo + "\" \"" + deskThree
                + "\" \"" +  deskFour + "\" \"" +  deskFive + "\" \"" + deskSix +"\")";
    }
}
