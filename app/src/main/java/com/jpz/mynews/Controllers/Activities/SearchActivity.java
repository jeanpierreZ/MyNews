package com.jpz.mynews.Controllers.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.jpz.mynews.Controllers.Fragments.ResearchFragment;
import com.jpz.mynews.Controllers.Fragments.ResultQueryFragment;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;


public class SearchActivity extends AppCompatActivity implements ResearchFragment.OnSearchClickedListener {

    ResearchFragment researchFragment = new ResearchFragment();
    ResultQueryFragment resultQueryFragment = new ResultQueryFragment();

    private SearchQuery searchQuery = new SearchQuery();

    private Bundle bundle = new Bundle();

    // Create keys for Bundles
    public static final String KEY_QUERY = "query";
    public static final String KEY_BEGIN_DATE = "beginDate";
    public static final String KEY_END_DATE = "endDate";
    public static final String KEY_DESKS = "desks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Display settings toolbar
        configureToolbar();

        // Display settings ResearchFragment
        configureResearchFragment();
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

    private void configureResearchFragment(){
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        researchFragment = (ResearchFragment)
                getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);
        if (researchFragment == null) {
            // Create new ResearchFragment
            researchFragment = new ResearchFragment();
            // Add it to FrameLayout fragment_container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, researchFragment)
                    .commit();
        }
    }

    @Override
    public void OnSearchClicked(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace researchFragment in the fragment_container view with resultQueryFragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.activity_search_frame_layout, resultQueryFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


    //----------------------------------------------------------------------------------
    // Implements methods from ResearchFragment to set Arguments for ResultQueryFragment

    @Override
    public void saveQueryTermsValue(String queryTerms) {
        searchQuery.queryTerms = queryTerms;

        bundle.putString(KEY_QUERY, searchQuery.queryTerms);
        Log.i("LOG","bundle " + searchQuery.queryTerms);

        resultQueryFragment.setArguments(bundle);
    }

    @Override
    public void saveBeginDateValue(String beginDate) {
        searchQuery.beginDate = beginDate;

        bundle.putString(KEY_BEGIN_DATE, searchQuery.beginDate);
        Log.i("LOG","bundle " + searchQuery.beginDate);

        resultQueryFragment.setArguments(bundle);
    }

    @Override
    public void saveEndDateValue(String endDate) {
        searchQuery.endDate = endDate;

        bundle.putString(KEY_END_DATE, searchQuery.endDate);
        Log.i("LOG","bundle " + searchQuery.endDate);

        resultQueryFragment.setArguments(bundle);
    }

    @Override
    public void saveDesksValues(String[] deskList) {
        searchQuery.desks = deskList;

        bundle.putStringArray(KEY_DESKS, searchQuery.desks);
        Log.i("LOG","bundle deskOne" + searchQuery.desks[0] + "bundle deskTwo" + searchQuery.desks[1]);

        resultQueryFragment.setArguments(bundle);
    }
}
