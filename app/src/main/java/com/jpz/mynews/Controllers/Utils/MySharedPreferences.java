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
    private String checkBoxOneKey = "checkBoxOneKey";
    private String checkBoxTwoKey = "checkBoxTwoKey";
    private String checkBoxThreeKey = "checkBoxThreeKey";
    private String checkBoxFourKey = "checkBoxFourKey";
    private String checkBoxFiveKey = "checkBoxFiveKey";
    private String checkBoxSixKey = "checkBoxSixKey";

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

    // Save the checkBox values from the research
    public void saveBoxesValues(String checkBoxOne, String checkBoxTwo, String checkBoxThree,
                                String checkBoxFour, String checkBoxFive, String checkBoxSix) {
        prefs.edit().putString(checkBoxOneKey, checkBoxOne).apply();
        prefs.edit().putString(checkBoxTwoKey, checkBoxTwo).apply();
        prefs.edit().putString(checkBoxThreeKey, checkBoxThree).apply();
        prefs.edit().putString(checkBoxFourKey, checkBoxFour).apply();
        prefs.edit().putString(checkBoxFiveKey, checkBoxFive).apply();
        prefs.edit().putString(checkBoxSixKey, checkBoxSix).apply();
    }

    // Get the checkBox values from the research
    public String[] getBoxesValues() {
        String checkBoxOne = prefs.getString(checkBoxOneKey, null);
        String checkBoxTwo = prefs.getString(checkBoxTwoKey, null);
        String checkBoxThree = prefs.getString(checkBoxThreeKey, null);
        String checkBoxFour = prefs.getString(checkBoxFourKey, null);
        String checkBoxFive = prefs.getString(checkBoxFiveKey, null);
        String checkBoxSix = prefs.getString(checkBoxSixKey, null);

        String[] whole = {checkBoxOne, checkBoxTwo, checkBoxThree, checkBoxFour, checkBoxFive, checkBoxSix};

        return whole;
    }

}
