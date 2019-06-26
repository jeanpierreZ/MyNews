package com.jpz.mynews.controllers.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.jpz.mynews.controllers.fragments.NewsFragment;
import com.jpz.mynews.controllers.fragments.SearchFragment;
import com.jpz.mynews.controllers.fragments.ResultQueryFragment;
import com.jpz.mynews.models.SearchQuery;
import com.jpz.mynews.R;

import static com.jpz.mynews.controllers.activities.MainActivity.KEY_URL;

public class SearchActivity extends BaseActivity
        implements SearchFragment.OnSearchOrNotifyClickedListener, NewsFragment.OnWebClickedListener {

    SearchFragment searchFragment = new SearchFragment();
    ResultQueryFragment resultQueryFragment = new ResultQueryFragment();

    private Bundle bundle = new Bundle();

    // Create a key for Bundle, used to save data in SearchQuery
    public static final String KEY_SEARCH_QUERY = "KEY_SEARCH_QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is the FrameLayout area within the activity_base.xml
        FrameLayout contentFrameLayout = findViewById(R.id.activity_base_frame_layout);
        // Inflate the activity to load
        getLayoutInflater().inflate(R.layout.activity_search, contentFrameLayout);

        // Display settings of Toolbar & NavigationView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setElevation(8);

        navigationView.getMenu().getItem(0).setVisible(true);
        navigationView.getMenu().getItem(1).setVisible(false);

        configureSearchFragment();
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
    public void onSearchOrNotifyClicked(SearchQuery searchQuery) {
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
    }

    //----------------------------------------------------------------------------------
    // Implement listener from NewsFragment to open WebViewActivity

    @Override
    public void OnWebClicked(int position, String url) {
        // Spread the click with the url of the article to open WebViewActivity
        Intent webViewActivity = new Intent(SearchActivity.this, WebViewActivity.class);
        webViewActivity.putExtra(KEY_URL, url);
        startActivity(webViewActivity);
    }
}