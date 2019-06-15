package com.jpz.mynews;


import android.support.test.filters.LargeTest;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.jpz.mynews.Controllers.Activities.SearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


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

/*
    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button
        onView(withId(R.id.notifications_fragment_query))
                .perform(typeText(queryTerms), closeSoftKeyboard());
        onView(withId(R.id.changeTextBt)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.textToBeChanged))
                .check(matches(withText(queryTerms)));
    }
*/

    @Test
    public void hintVisibilityTest(){
        // Check hint visibility for query
        onView(withId(R.id.search_fragment_query)).check(matches(withHint(hintText)));

        // Enter name
        onView(withId(R.id.search_fragment_query)).perform(typeText(queryTerms),closeSoftKeyboard());
        onView(withId(R.id.search_fragment_query)).check(matches(withText("Espresso")));
    }

}
