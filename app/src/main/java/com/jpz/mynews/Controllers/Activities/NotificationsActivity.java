package com.jpz.mynews.Controllers.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jpz.mynews.Controllers.Fragments.NotificationsFragment;
import com.jpz.mynews.Controllers.Fragments.SearchAndNotifyFragment;
import com.jpz.mynews.R;

public class NotificationsActivity extends AppCompatActivity implements SearchAndNotifyFragment.OnSearchAndNotifyClickedListener {

    NotificationsFragment notificationsFragment = new NotificationsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Display settings toolbar
        configureToolbar();

        // Display settings ResearchFragment
        configureNotificationsFragment();
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

    private void configureNotificationsFragment(){
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        notificationsFragment = (NotificationsFragment)
                getSupportFragmentManager().findFragmentById(R.id.activity_notifications_frame_layout);
        if (notificationsFragment == null) {
            // Create new NotificationsFragment
            notificationsFragment = new NotificationsFragment();
            // Add it to FrameLayout fragment_container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_notifications_frame_layout, notificationsFragment)
                    .commit();
        }
    }

    @Override
    public void OnSearchOrNotifyClicked(View view) {

    }

    @Override
    public void saveQueryTermsValue(String queryTerms) {

    }

    @Override
    public void saveBeginDateValue(String beginDate) {

    }

    @Override
    public void saveEndDateValue(String endDate) {

    }

    @Override
    public void saveDesksValues(String[] deskList) {

    }
}
