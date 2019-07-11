package com.jpz.mynews.controllers.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jpz.mynews.controllers.fragments.BaseSearchFragment;
import com.jpz.mynews.controllers.fragments.NotificationsFragment;
import com.jpz.mynews.controllers.utils.MyIdlingResources;
import com.jpz.mynews.controllers.utils.MySharedPreferences;
import com.jpz.mynews.controllers.utils.NotificationReceiver;
import com.jpz.mynews.models.SearchQuery;
import com.jpz.mynews.R;

import java.util.Calendar;

import static android.app.AlarmManager.INTERVAL_DAY;

public class NotificationsActivity extends AppCompatActivity
        implements BaseSearchFragment.OnSearchOrNotifyClickedListener {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private MySharedPreferences prefs;

    private MyIdlingResources myIdlingResources = new MyIdlingResources();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // For testing
        myIdlingResources.configureEspressoIdlingResource();

        // Get context for AlarmManager, Intent and sharedPreferences
        Context context = getApplicationContext();
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        prefs = new MySharedPreferences(getApplicationContext());

        // Display settings Toolbar and NotificationsFragment
        configureToolbar();
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
        NotificationsFragment notificationsFragment = (NotificationsFragment)
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

    //----------------------------------------------------------------------------------
    // Implements method from BaseSearchFragment to set data for AlarmManager
    @Override
    public void onSearchOrNotifyClicked(SearchQuery searchQuery) {
        // Save the state of the switch and reuse it with data when NotificationsFragment is open

        // If the switch is checked, activate notifications...
        if (searchQuery.switchIsChecked) {
            startNotifications();
            // ...wih values form the fragment
            prefs.saveSwitchState(true);
            prefs.saveQueryTerms(searchQuery.queryTerms);
            prefs.saveDesksValues(searchQuery.desks[0], searchQuery.desks[1], searchQuery.desks[2],
                    searchQuery.desks[3], searchQuery.desks[4], searchQuery.desks[5]);
        }
        else {
            // If the switch is unchecked, deactivate notifications...
            cancelNotifications();
            // ... and reset data in MySharedPreferences
            prefs.saveSwitchState(false);
            prefs.saveQueryTerms(null);
            prefs.saveDesksValues(null, null, null,
                    null, null, null);
        }
    }

    //----------------------------------------------------------------------------------
    // Methods to start or cancel notifications

    private void startNotifications() {
        // Set the schedule for 7:30 p.m.
        Calendar notificationCalendar = Calendar.getInstance();
        notificationCalendar.setTimeInMillis(System.currentTimeMillis());
        notificationCalendar.set(Calendar.HOUR_OF_DAY, 19);
        notificationCalendar.set(Calendar.MINUTE, 30);
        notificationCalendar.set(Calendar.SECOND, 0);

        // If the schedule chosen has passed, set the alarmMgr for the next day
        if (notificationCalendar.before(Calendar.getInstance())){
            notificationCalendar.add(Calendar.DATE, 1);
        }

        // Set alarmMgr to start at the schedule fixed and once per day
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, notificationCalendar.getTimeInMillis(),
                INTERVAL_DAY, alarmIntent);

        // For testing
        myIdlingResources.incrementIdleResource();
        // Confirm with a message
        // Create instance of snackBar
        Snackbar snackBarActivate = Snackbar.make(findViewById(R.id.activity_notifications_frame_layout),
                R.string.activatedNotifications, Snackbar.LENGTH_SHORT);
        // Get snackBar view
        View snackViewActivate = snackBarActivate.getView();
        // Change snackBar text color
        TextView textSnackActivate = snackViewActivate.findViewById(android.support.design.R.id.snackbar_text);
        textSnackActivate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textSnackActivate.setTextColor(getResources().getColor(R.color.colorAccent));
        snackBarActivate.show();
        // For testing
        myIdlingResources.decrementIdleResource();
    }

    private void cancelNotifications() {
        // Cancel alarmMgr
        alarmMgr.cancel(alarmIntent);

        // For testing
        myIdlingResources.incrementIdleResource();
        // Confirm with a message
        Snackbar snackBarCancel = Snackbar.make(findViewById(R.id.activity_notifications_frame_layout),
                R.string.canceledNotifications, Snackbar.LENGTH_SHORT);
        View snackViewCancel = snackBarCancel.getView();
        TextView textSnackCancel = snackViewCancel.findViewById(android.support.design.R.id.snackbar_text);
        textSnackCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textSnackCancel.setTextColor(getResources().getColor(R.color.colorPrimary));
        snackBarCancel.show();
        // For testing
        myIdlingResources.decrementIdleResource();
    }

    //----------------------------------------------------------------------------------
    // For testing
    @VisibleForTesting
    public CountingIdlingResource getEspressoIdlingResourceForNotificationsActivity() {
        return this.myIdlingResources.getEspressoIdlingResource();
    }
}