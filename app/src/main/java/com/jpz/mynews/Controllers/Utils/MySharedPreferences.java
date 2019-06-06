package com.jpz.mynews.Controllers.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPreferences {

    private SharedPreferences prefs;

    // Keys for the research
    private String queryKey = "queryKey";
    private String beginDateKey = "beginDateKey";

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

    // Save the query terms from the research
    public void saveBeginDate(String beginDate) {
        prefs.edit().putString(beginDateKey, beginDate).apply();
    }

    // Save the query terms from the research
    public String getBeginDate() {
        return prefs.getString(beginDateKey, null);
    }

}
