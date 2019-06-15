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
import android.view.View;
import android.widget.Toast;

import com.jpz.mynews.Controllers.Fragments.NotificationsFragment;
import com.jpz.mynews.Controllers.Fragments.SearchAndNotificationsFragment;
import com.jpz.mynews.Controllers.Utils.NotificationReceiver;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;

import java.util.Calendar;

import static android.app.AlarmManager.INTERVAL_DAY;

public class NotificationsActivity extends AppCompatActivity implements SearchAndNotificationsFragment.OnSearchAndNotifyClickedListener {

    NotificationsFragment notificationsFragment = new NotificationsFragment();

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private Context context;

    private SearchQuery searchQuery = new SearchQuery();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Get context for AlarmManager and Intent
        context = getApplicationContext();
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

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

    //----------------------------------------------------------------------------------
    // Implements methods from NotificationsFragment to set data for AlarmManager

    @Override
    public void onSearchOrNotifyClicked(View view) {

        startAlarm();
    }

    @Override
    public void onNotificationUnchecked(View view) {
        // If switch button for notifications is unchecked, cancel the alarm
        cancelAlarm();
    }

    @Override
    public void saveQueryTermsValue(String queryTerms) {
        searchQuery.queryTerms = queryTerms;
        Log.i("LOG","Notif Activity " + searchQuery.queryTerms);

    }

    @Override
    public void saveBeginDateValue(String beginDate) {
        searchQuery.beginDate = beginDate;

        Log.i("LOG","Notif Activity " + searchQuery.beginDate);

    }

    @Override
    public void saveEndDateValue(String endDate) {
        searchQuery.endDate = endDate;

        Log.i("LOG","Notif Activity " + searchQuery.endDate);

    }

    @Override
    public void saveDesksValues(String[] deskList) {
        searchQuery.desks = deskList;

        Log.i("LOG","desks" +
                searchQuery.desks[0] + searchQuery.desks[1] +
                searchQuery.desks[2] + searchQuery.desks[3] +
                searchQuery.desks[4] + searchQuery.desks[5]);

    }

    private void startAlarm() {

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

        Toast.makeText(context, R.string.activatedAlarm, Toast.LENGTH_SHORT).show();
    }

    private void cancelAlarm() {

        alarmMgr.cancel(alarmIntent);
        Toast.makeText(context, R.string.canceledAlarm, Toast.LENGTH_SHORT).show();
    }

}