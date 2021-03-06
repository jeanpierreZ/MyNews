package com.jpz.mynews.controllers.activities;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jpz.mynews.controllers.fragments.NewsFragment;
import com.jpz.mynews.controllers.fragments.SearchFragment;
import com.jpz.mynews.controllers.fragments.ResultQueryFragment;
import com.jpz.mynews.controllers.utils.MyIdlingResources;
import com.jpz.mynews.models.SearchQuery;
import com.jpz.mynews.R;

import static com.jpz.mynews.controllers.activities.MainActivity.KEY_URL;

public class SearchActivity extends AppCompatActivity
implements SearchFragment.OnSearchOrNotifyClickedListener, NewsFragment.OnWebClickedListener {

    SearchFragment searchFragment = new SearchFragment();
    ResultQueryFragment resultQueryFragment = new ResultQueryFragment();

    private Bundle bundle = new Bundle();

    // Create a key for Bundle, used to save data in SearchQuery
    public static final String KEY_SEARCH_QUERY = "KEY_SEARCH_QUERY";

    private MyIdlingResources myIdlingResources = new MyIdlingResources();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // For testing
        myIdlingResources.configureEspressoIdlingResource();

        // Display settings of Toolbar & SearchFragment
        configureToolbar();
        configureSearchFragment();
    }

    private void configureToolbar(){
        //Get the toolbar (Serialise)
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureSearchFragment(){
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        searchFragment = (SearchFragment)
                getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);
        if (searchFragment == null) {
            // Create new SearchFragment
            searchFragment = new SearchFragment();
            // Add it to FrameLayout fragment_container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, searchFragment)
                    .commit();
        }
    }

    //----------------------------------------------------------------------------------
    // Implements listener from SearchFragment to open ResultQueryFragment

    @Override
    public void onSearchOrNotifyClicked(final SearchQuery searchQuery) {
        // For testing
        myIdlingResources.incrementIdleResource();
        // Put values from SearchFragment to set Arguments for ResultQueryFragment
        bundle.putSerializable(KEY_SEARCH_QUERY, searchQuery);
        resultQueryFragment.setArguments(bundle);

        // Replace searchFragment in the fragment_container view with resultQueryFragment,
        // and add the transaction to the back stack so the user can navigate back
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_search_frame_layout, resultQueryFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        // For testing
        myIdlingResources.decrementIdleResource();
    }

    //----------------------------------------------------------------------------------
    // Implement listener from NewsFragment to open WebViewActivity when click on an article

    @Override
    public void onWebClicked(int position, String url) {
        // Spread the click with the url of the article to open WebViewActivity
        Intent webViewActivity = new Intent(SearchActivity.this, WebViewActivity.class);
        webViewActivity.putExtra(KEY_URL, url);
        startActivity(webViewActivity);
    }

    //----------------------------------------------------------------------------------
    // For testing

    @VisibleForTesting
    public CountingIdlingResource getEspressoIdlingResourceForSearchActivity() {
        return this.myIdlingResources.getEspressoIdlingResource();
    }
}