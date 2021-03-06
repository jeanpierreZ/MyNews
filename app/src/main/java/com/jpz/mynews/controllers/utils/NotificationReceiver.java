package com.jpz.mynews.controllers.utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.jpz.mynews.models.ArticleSearchResponse;
import com.jpz.mynews.models.SearchQuery;
import com.jpz.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.jpz.mynews.controllers.utils.ApplicationNotification.CHANNEL_ID;
import static com.jpz.mynews.controllers.utils.Service.API_FILTER_SORT_ORDER;

public class NotificationReceiver extends BroadcastReceiver {

    // To load data from the notifications request
    private MySharedPreferences prefs;
    private SearchQuery searchQuery = new SearchQuery();

    // Fields used for the notification of articles in the request
    private String selectedDesks;
    private int articlesCounter;

    // For data and Notification/Manager/Compat
    protected Disposable disposable;
    private Context _context;

    @Override
    public void onReceive(Context context, Intent intent) {
        _context = context;
         prefs = new MySharedPreferences(context.getApplicationContext());
         // Start the request with the notification inside
        startRequest();
    }

    //---------------------------------------------------------------

    // Search if there are articles with terms of the request
    private void startRequest() {
        // Get the fields for the request
        fetchQueryTerms();
        fetchDates();
        fetchDesks();
        int page = 0;

        this.disposable = APIClient.fetchArticleSearch
                (selectedDesks, API_FILTER_SORT_ORDER, page, searchQuery.queryTerms,
                        searchQuery.beginDate, searchQuery.endDate)
                .subscribeWith(new DisposableObserver<ArticleSearchResponse>() {
                   @Override
                   public void onNext(ArticleSearchResponse searchResponse) {
                       Log.i("TAG", "On Next Notif Receiver");
                       // Check the number of articles in the request
                       articlesCounter = searchResponse.getResponse().getMeta().getHits();
                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.e("TAG", "On Error Notif Receiver"
                               + Log.getStackTraceString(e));
                   }

                   @Override
                   public void onComplete() {
                       Log.i("TAG", "On Complete Notif Receiver");
                       notifications();
                   }
                });
    }

    //---------------------------------------------------------------

    // Get the query terms to notify
    private void fetchQueryTerms() {
        searchQuery.queryTerms = prefs.getQueryTerms();
    }

    // Get the begin and end dates to notify
    private void fetchDates() {
        // Make the dates equal today
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        searchQuery.beginDate = sdf.format(today);
        searchQuery.endDate = sdf.format(today);
    }

    // Get the desk values to notify
    private void fetchDesks() {
        searchQuery.desks = prefs.getDesksValues();
        // Formatting desks chosen for the notification
        selectedDesks =
                "news_desk:(\"" + searchQuery.desks[0] + "\" \"" + searchQuery.desks[1] +
                        "\" \"" + searchQuery.desks[2] + "\" \"" + searchQuery.desks[3] +
                        "\" \"" + searchQuery.desks[4] + "\" \"" + searchQuery.desks[5] +"\")";
    }

    //---------------------------------------------------------------

    // Display he notification with the result of the request
    private void notifications() {
        String title = _context.getString(R.string.app_name);
        String text;
        switch (articlesCounter) {
            case 0:
                text = _context.getString(R.string.zeroArticle);
                break;
            case 1:
                text = _context.getString(R.string.oneArticle, articlesCounter);
                break;
            default:
                text = _context.getString(R.string.articles, articlesCounter);
        }

        NotificationManager notificationManager
                = (NotificationManager)_context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Set the content and channel of the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_event_note_black_48dp)
                .setContentTitle(title)
                .setContentText(text)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Notify the builder
        notificationManager.notify(0, builder.build());
    }
}