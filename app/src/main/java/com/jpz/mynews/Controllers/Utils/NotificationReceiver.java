package com.jpz.mynews.Controllers.Utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.jpz.mynews.Controllers.Utils.ApplicationNotification.CHANNEL_ID;

public class NotificationReceiver extends BroadcastReceiver {

    private MySharedPreferences prefs;
    private SearchQuery searchQuery = new SearchQuery();
    private ConvertMethods convertMethods = new ConvertMethods();

    // Fields used for the notification of articles in the request
    private String beginDateAfterConversion;
    private String endDateAfterConversion;
    private String selectedDesks;
    private int articlesCounter;

    // For data
    protected Disposable disposable;

    @Override
    public void onReceive(Context context, Intent intent) {

         prefs = new MySharedPreferences(context.getApplicationContext());

         // Launch the request
        launchQuery();

        String title = context.getString(R.string.app_name);
        String text = "There is " + articlesCounter + " articles for your request \""+ searchQuery.queryTerms +
                "\" with the categories chosen.";

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
        // Call methods to fetch fields for the request
        fetchQueryTerms();
        fetchBeginDate();
        fetchEndDate();
        fetchDesks();

        // Use for pagination
        int page = 0;

        this.disposable = APIClient.getArticleSearchNews
                (selectedDesks, page, searchQuery.queryTerms, beginDateAfterConversion, endDateAfterConversion)
                .subscribeWith(new DisposableObserver<List<GenericNews>>() {
                    @Override
                    public void onNext(List<GenericNews> genericNewsList) {
                        Log.i("TAG", "On Next Notif Receiver");
                        // Check if the number of articles in the list
                        articlesCounter = genericNewsList.size();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "On Error ResultQueryFragment"
                                + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG", "On Complete ResultQueryFragment");
                    }
                });
    }

    private void fetchQueryTerms() {
        // Get the query terms to notify
        searchQuery.queryTerms = prefs.getQueryTerms();
        Log.i("LOG","Notif Receiver " + searchQuery.queryTerms);
    }

    private void fetchBeginDate() {
        // Get the begin date to notify
        // Make the begin date equals today
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        searchQuery.beginDate = sdf.format(today);

        // If there is no date saved, set ""
        if (searchQuery.beginDate != null) {
            if (searchQuery.beginDate.equals(""))
                beginDateAfterConversion = null;
            else {
                beginDateAfterConversion = convertMethods.convertBeginOrEndDate(searchQuery.beginDate);
                Log.i("LOG","Notif Receiver " + searchQuery.beginDate);
            }
        }
    }

    private void fetchEndDate() {
        // Get the end date to notify
        // Make the end date equals today
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        searchQuery.endDate = sdf.format(today);

        // If there is no date saved, set ""
        if (searchQuery.endDate != null) {
            if (searchQuery.endDate.equals(""))
                endDateAfterConversion = null;
            else {
                endDateAfterConversion = convertMethods.convertBeginOrEndDate(searchQuery.endDate);
                Log.i("LOG","Notif Receiver " + searchQuery.endDate);
            }
        }
    }

    private void fetchDesks() {
        // Get the desk values to notify
        searchQuery.desks = prefs.getDesksValues();

        // Formatting desks chosen for the notification
        if (searchQuery.desks != null) {
            Log.i("LOG","Notif Receiver " + searchQuery.desks[0] + searchQuery.desks[1]
                    + searchQuery.desks[2] + searchQuery.desks[3]
                    + searchQuery.desks[4] + searchQuery.desks[5]);

            selectedDesks =
                    "news_desk:(\"" + searchQuery.desks[0] + "\" \"" + searchQuery.desks[1] +
                            "\" \"" + searchQuery.desks[2] + "\" \"" + searchQuery.desks[3] +
                            "\" \"" + searchQuery.desks[4] + "\" \"" + searchQuery.desks[5] +"\")";
        }
    }
}
