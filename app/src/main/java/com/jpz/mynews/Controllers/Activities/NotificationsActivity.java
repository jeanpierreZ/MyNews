package com.jpz.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jpz.mynews.Controllers.Fragments.BaseSearchFragment;
import com.jpz.mynews.Controllers.Fragments.NotificationsFragment;
import com.jpz.mynews.Controllers.Utils.MySharedPreferences;
import com.jpz.mynews.Controllers.Utils.NotificationReceiver;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;

import java.util.Calendar;

import static android.app.AlarmManager.INTERVAL_DAY;

public class NotificationsActivity extends BaseActivity
        implements BaseSearchFragment.OnSearchOrNotifyClickedListener {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private MySharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is the FrameLayout area within the activity_base.xml
        FrameLayout contentFrameLayout = findViewById(R.id.activity_base_frame_layout);
        // Inflate the activity to load
        getLayoutInflater().inflate(R.layout.activity_notifications, contentFrameLayout);

        // Display settings of Toolbar & NavigationView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setElevation(8);

        navigationView.getMenu().getItem(0).setVisible(true);
        navigationView.getMenu().getItem(1).setVisible(false);

        // Get context for AlarmManager, Intent and sharedPreferences
        Context context = getApplicationContext();
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        prefs = new MySharedPreferences(getApplicationContext());

        // Display settings Toolbar and NotificationsFragment
        configureNotificationsFragment();
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
            Log.i("LOG","Notif Activity switchIsChecked :" + searchQuery.switchIsChecked);

            prefs.saveQueryTerms(searchQuery.queryTerms);
            Log.i("LOG","Notif Activity queryTerms :" + searchQuery.queryTerms);

            prefs.saveDesksValues(searchQuery.desks[0], searchQuery.desks[1], searchQuery.desks[2],
                    searchQuery.desks[3], searchQuery.desks[4], searchQuery.desks[5]);

            Log.i("LOG","Notif Activity desks :" +
                    searchQuery.desks[0] + searchQuery.desks[1] +
                    searchQuery.desks[2] + searchQuery.desks[3] +
                    searchQuery.desks[4] + searchQuery.desks[5]);
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
    }

    private void cancelNotifications() {
        // Cancel alarmMgr
        alarmMgr.cancel(alarmIntent);

        // Confirm with a message
        Snackbar snackBarCancel = Snackbar.make(findViewById(R.id.activity_notifications_frame_layout),
                R.string.canceledNotifications, Snackbar.LENGTH_SHORT);
        View snackViewCancel = snackBarCancel.getView();
        TextView textSnackCancel = snackViewCancel.findViewById(android.support.design.R.id.snackbar_text);
        textSnackCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textSnackCancel.setTextColor(getResources().getColor(R.color.colorPrimary));
        snackBarCancel.show();
    }
}