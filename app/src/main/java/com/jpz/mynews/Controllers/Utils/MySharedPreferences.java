package com.jpz.mynews.Controllers.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPreferences {

    private SharedPreferences prefs;

    // Keys for the research
    private String queryKey = "queryKey";
    private String beginDateKey = "beginDateKey";
    private String endDateKey = "endDateKey";

    // Constructor
    public MySharedPreferences(Context context){
        prefs = context.getSharedPreferences("Preferences", MODE_PRIVATE);
    }

    // Save the query terms from the research
    public void saveQueryTerms(String queryTerms) {
        prefs.edit().putString(queryKey, queryTerms).apply();
    }

    // Get the query terms from the research
    public String getQueryTerms() {
        return prefs.getString(queryKey, null);
    }

    // Save the begin date from the research
    public void saveBeginDate(String beginDate) {
        prefs.edit().putString(beginDateKey, beginDate).apply();
    }

    // Get the begin date from the research
    public String getBeginDate() {
        return prefs.getString(beginDateKey, null);
    }

    // Save the end date from the research
    public void saveEndDate(String endDate) {
        prefs.edit().putString(endDateKey, endDate).apply();
    }

    // Get the end date from the research
    public String getEndDate() {
        return prefs.getString(endDateKey, null);
    }

}
