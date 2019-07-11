package com.jpz.mynews;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;

import com.jpz.mynews.controllers.activities.NotificationsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class NotificationsActivityTest {

    @Rule
    public ActivityTestRule<NotificationsActivity> activityRule
            = new ActivityTestRule<>(NotificationsActivity.class);

    @Before
    public void registerIdling() {
        IdlingRegistry.getInstance()
                .register(activityRule.getActivity().getEspressoIdlingResourceForNotificationsActivity());
    }

    @After
    public void unregisterIdling() {
        IdlingRegistry.getInstance()
                .unregister(activityRule.getActivity().getEspressoIdlingResourceForNotificationsActivity());
    }

    @Test
    public void ensureActivateDeactivateNotifications() {
        // Check if Notifications are activated when click on the switch

        // Put a space as a query (to have always a result !)...
        onView(withId(R.id.base_search_fragment_query))
                .perform(typeText(" "),closeSoftKeyboard());
        // ...and check a CheckBox to enable the switch...
        onView(withId(R.id.base_search_fragment_checkbox_one))
                .check(matches(isNotChecked())).perform(click());
        // ...then click on the switch
        onView(withId(R.id.base_search_switch)).perform(click());
        // Check if the snackBar with the message of activation is displayed
        onView(withText(R.string.activatedNotifications))
                .check(matches(isDisplayed()));
        // Then reinitialize the screen...
        onView(withId(R.id.base_search_switch)).perform(click());
        //...and check if the snackBar with the message of deactivation is displayed
        onView(withText(R.string.canceledNotifications))
                .check(matches(isDisplayed()));
        onView(withId(R.id.base_search_fragment_checkbox_one))
                .check(matches(isChecked())).perform(click());
        onView(withId(R.id.base_search_fragment_query))
                .perform(clearText());
    }
}