package com.jpz.mynews.Controllers.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jpz.mynews.Controllers.Fragments.SearchFragment;
import com.jpz.mynews.R;

public class SearchActivity extends AppCompatActivity {

    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Display settings toolbar
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
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);

        if (searchFragment == null) {
            // Create new search fragment
            searchFragment = new SearchFragment();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, searchFragment)
                    .commit();
        }
    }
}
