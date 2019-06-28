package com.jpz.mynews;

import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jpz.mynews.controllers.activities.MainActivity;
import com.jpz.mynews.controllers.activities.NotificationsActivity;
import com.jpz.mynews.controllers.activities.SearchActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static com.jpz.mynews.Delay.waitFor;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    // Input Intents then...
    @Before
    public void initializeIntents() {
        Intents.init();
    }

    // ... output Intents to call tests one after the other
    @After
    public void releaseIntents() {
        Intents.release();
    }

    @Test
    public void ensureSearchFragmentStarted() {
        // Check if a text, which is only on searchFragment, is displayed when click to the searchIcon in the toolBar
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.menu_activity_main_search)).perform(click());
        onView(withId(R.id.base_search_fragment_text_beginDate))
                .check(matches(withText(R.string.beginDate)));
    }

    @Test
    public void ensureSearchActivityStarted() {
        // Check if the SearchActivity starts, due to the intent, when click on searchIcon in the toolBar
        onView(withId(R.id.menu_activity_main_search)).perform(click());
        intended(hasComponent(SearchActivity.class.getName()));
    }

    @Test
    public void ensureNotificationsActivityStarted() {
        // Check if the NotificationsActivity starts, due to the intent, when click on Notifications in the toolBar
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.notifications)).perform(click());
        intended(hasComponent(NotificationsActivity.class.getName()));
    }
}