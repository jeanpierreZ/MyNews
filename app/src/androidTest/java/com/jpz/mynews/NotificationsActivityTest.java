package com.jpz.mynews;

import android.support.test.rule.ActivityTestRule;

import com.jpz.mynews.controllers.activities.NotificationsActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jpz.mynews.Delay.waitFor;

public class NotificationsActivityTest {

    @Rule
    public ActivityTestRule<NotificationsActivity> activityRule
            = new ActivityTestRule<>(NotificationsActivity.class);

    @Test
    public void ensureNotificationsAreActivated() {
        // Check if Notifications are activated when click on the switch

        // Put a space as a query (to have always a result !)...
        onView(withId(R.id.base_search_fragment_query))
                .perform(typeText(" "),closeSoftKeyboard());
        // ...and check a CheckBox to enable the switch...
        onView(withId(R.id.base_search_fragment_checkbox_one))
                .check(matches(isNotChecked())).perform(click());

        // ...then click on the switch
        onView(withId(R.id.base_search_switch)).perform(click());

        // wait 1 second and check if the snackBar with the message of activation is displayed
        onView(isRoot()).perform(waitFor(1000));
        onView(withText(R.string.activatedNotifications))
                .check(matches(isDisplayed()));

        // Then reinitialize the screen
        onView(withId(R.id.base_search_switch)).perform(click());
        onView(withId(R.id.base_search_fragment_checkbox_one))
                .check(matches(isChecked())).perform(click());
        onView(withId(R.id.base_search_fragment_query))
                .perform(clearText());
    }
}