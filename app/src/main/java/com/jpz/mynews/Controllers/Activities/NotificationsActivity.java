package com.jpz.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.jpz.mynews.Controllers.Fragments.NotificationsFragment;
import com.jpz.mynews.Controllers.Fragments.SearchAndNotificationsFragment;
import com.jpz.mynews.Controllers.Utils.MySharedPreferences;
import com.jpz.mynews.Controllers.Utils.NotificationReceiver;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;

import java.util.Calendar;

import static android.app.AlarmManager.INTERVAL_DAY;

public class NotificationsActivity extends AppCompatActivity implements SearchAndNotificationsFragment.OnSearchOrNotifyClickedListener {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private Context context;

    private MySharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Get context for AlarmManager, Intent and sharedPreferences
        context = getApplicationContext();
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        prefs = new MySharedPreferences(getApplicationContext());

        // Display settings toolbar
        configureToolbar();

        // Display settings NotificationsFragment
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

    // Implements method from SearchAndNotificationsFragment to set data for AlarmManager
    @Override
    public void onSearchOrNotifyClicked(SearchQuery searchQuery) {
        // If the switch is checked, activate notifications...
        if (searchQuery.isChecked) {
            startNotifications();

            // ...wih values form the fragment
            prefs.saveQueryTerms(searchQuery.queryTerms);
            Log.i("LOG","Notif Activity queryTerms" + searchQuery.queryTerms);

            prefs.saveDesksValues(searchQuery.desks[0], searchQuery.desks[1], searchQuery.desks[2],
                    searchQuery.desks[3], searchQuery.desks[4], searchQuery.desks[5]);

            Log.i("LOG","Notif Activity desks" +
                    searchQuery.desks[0] + searchQuery.desks[1] +
                    searchQuery.desks[2] + searchQuery.desks[3] +
                    searchQuery.desks[4] + searchQuery.desks[5]);
        }
        else
            // If the switch is unchecked, deactivate notifications
            cancelNotifications();
    }

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

        // Set alarmMgr with to start at the schedule fixed and once per day
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, notificationCalendar.getTimeInMillis(),
                INTERVAL_DAY, alarmIntent);
        // Confirm with a toast message
        Toast.makeText(context, R.string.activatedNotifications, Toast.LENGTH_SHORT).show();
    }

    private void cancelNotifications() {
        alarmMgr.cancel(alarmIntent);
        // Confirm with a toast message
        Toast.makeText(context, R.string.canceledNotifications, Toast.LENGTH_SHORT).show();
    }
}