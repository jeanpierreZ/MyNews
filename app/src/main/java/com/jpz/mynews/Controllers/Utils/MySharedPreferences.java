package com.jpz.mynews.Controllers.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPreferences {

    private SharedPreferences prefs;

    // Keys for the research
    private static final String QUERY_KEY = "QUERY_KEY";
    private static final String BEGIN_DATE_KEY = "BEGIN_DATE_KEY";
    private static final String END_DATE_KEY = "END_DATE_KEY";
    private static final String CHECK_BOX_ONE_KEY = "CHECK_BOX_ONE_KEY";
    private static final String CHECK_BOX_TWO_KEY = "CHECK_BOX_TWO_KEY";
    private static final String CHECK_BOX_THREE_KEY = "CHECK_BOX_THREE_KEY";
    private static final String CHECK_BOX_FOUR_KEY = "CHECK_BOX_FOUR_KEY";
    private static final String CHECK_BOX_FIVE_KEY = "CHECK_BOX_FIVE_KEY";
    private static final String CHECK_BOX_SIX_KEY = "CHECK_BOX_SIX_KEY";

    // Constructor
    public MySharedPreferences(Context context){
        prefs = context.getSharedPreferences("Preferences", MODE_PRIVATE);
    }

    // Save the query terms from the research
    public void saveQueryTerms(String queryTerms) {
        prefs.edit().putString(QUERY_KEY, queryTerms).apply();
    }

    // Get the query terms from the research
    public String getQueryTerms() {
        return prefs.getString(QUERY_KEY, null);
    }

    // Save the begin date from the research
    public void saveBeginDate(String beginDate) {
        prefs.edit().putString(BEGIN_DATE_KEY, beginDate).apply();
    }

    // Get the begin date from the research
    public String getBeginDate() {
        return prefs.getString(BEGIN_DATE_KEY, null);
    }

    // Save the end date from the research
    public void saveEndDate(String endDate) {
        prefs.edit().putString(END_DATE_KEY, endDate).apply();
    }

    // Get the end date from the research
    public String getEndDate() {
        return prefs.getString(END_DATE_KEY, null);
    }

    // Save the checkBox values from the research
    public void saveDesksValues(String checkBoxOne, String checkBoxTwo, String checkBoxThree,
                                String checkBoxFour, String checkBoxFive, String checkBoxSix) {
        prefs.edit().putString(CHECK_BOX_ONE_KEY, checkBoxOne).apply();
        prefs.edit().putString(CHECK_BOX_TWO_KEY, checkBoxTwo).apply();
        prefs.edit().putString(CHECK_BOX_THREE_KEY, checkBoxThree).apply();
        prefs.edit().putString(CHECK_BOX_FOUR_KEY, checkBoxFour).apply();
        prefs.edit().putString(CHECK_BOX_FIVE_KEY, checkBoxFive).apply();
        prefs.edit().putString(CHECK_BOX_SIX_KEY, checkBoxSix).apply();
    }

    // Get the checkBox values from the research
    public String[] getDesksValues() {
        String checkBoxOne = prefs.getString(CHECK_BOX_ONE_KEY, null);
        String checkBoxTwo = prefs.getString(CHECK_BOX_TWO_KEY, null);
        String checkBoxThree = prefs.getString(CHECK_BOX_THREE_KEY, null);
        String checkBoxFour = prefs.getString(CHECK_BOX_FOUR_KEY, null);
        String checkBoxFive = prefs.getString(CHECK_BOX_FIVE_KEY, null);
        String checkBoxSix = prefs.getString(CHECK_BOX_SIX_KEY, null);

        String[] whole = {checkBoxOne, checkBoxTwo, checkBoxThree, checkBoxFour, checkBoxFive, checkBoxSix};

        return whole;
    }

}
