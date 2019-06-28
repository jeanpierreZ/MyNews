package com.jpz.mynews;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jpz.mynews.controllers.activities.SearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jpz.mynews.Delay.waitFor;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchActivityTest {

    private String queryTerms;
    private String hintText;

    @Rule
    public ActivityTestRule<SearchActivity> activityRule
            = new ActivityTestRule<>(SearchActivity.class);

    @Before
    public void initializeString() {
        // Specify a valid string
        queryTerms = "Espresso";
        hintText = "Search query term";
    }

    @Test
    public void hintVisibilityTest(){
        // Check hint visibility for query
        onView(withId(R.id.base_search_fragment_query)).check(matches(withHint(hintText)));
    }

    @Test
    public void markText() {
        // Check if the text displayed correspond to the text enter
        onView(withId(R.id.base_search_fragment_query))
                .perform(typeText(queryTerms),closeSoftKeyboard());
        onView(withId(R.id.base_search_fragment_query)).check(matches(withText("Espresso")));
    }

    @Test
    public void ensureResultQueryFragmentStarted() {
        // Check if ResultQueryFragment starts when click on searchButton

        // Put a space as a query (to have always a result !)...
        onView(withId(R.id.base_search_fragment_query))
                .perform(typeText(" "),closeSoftKeyboard());
        // ...and check a CheckBox to enable searchButton...
        onView(withId(R.id.base_search_fragment_checkbox_one))
                .check(matches(isNotChecked())).perform(click());

        // ...then click on searchButton
        onView(withId(R.id.base_search_fragment_button)).perform(click());

        // wait 2 seconds and check if the recycler view is displayed
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.fragment_news_recycler_view)).check(matches(isDisplayed()));
    }
}
