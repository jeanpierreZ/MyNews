package com.jpz.mynews;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jpz.mynews.Controllers.Activities.SearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TextAndButtonsTest {

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
        onView(withId(R.id.search_fragment_query)).check(matches(withHint(hintText)));
    }

    @Test
    public void markText() {
        // Enter text
        onView(withId(R.id.search_fragment_query)).perform(typeText(queryTerms),closeSoftKeyboard());
        onView(withId(R.id.search_fragment_query)).check(matches(withText("Espresso")));
    }
}
