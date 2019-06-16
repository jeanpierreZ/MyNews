package com.jpz.mynews.Controllers.Utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;

import static com.jpz.mynews.Controllers.Utils.ApplicationNotification.CHANNEL_ID;

public class NotificationReceiver extends BroadcastReceiver {

    private MySharedPreferences prefs;

    @Override
    public void onReceive(Context context, Intent intent) {

         prefs = new MySharedPreferences(context.getApplicationContext());

        launchQuery();

        String title = context.getString(R.string.app_name);
        String text = "Text";

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Set the notification's content and channel
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Notify the builder
        notificationManager.notify(0, builder.build());
    }

    private void launchQuery() {

        SearchQuery searchQuery = new SearchQuery();

        // Get the transferred data from source activity
        searchQuery.queryTerms = prefs.getQueryTerms();
        Log.i("LOG","Notif Receiver " + searchQuery.queryTerms);

        // The sequel is coming soon...

    }

}
