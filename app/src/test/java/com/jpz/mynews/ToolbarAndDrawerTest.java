package com.jpz.mynews;

import android.content.Intent;

import com.jpz.mynews.controllers.activities.MainActivity;
import com.jpz.mynews.controllers.activities.NotificationsActivity;
import com.jpz.mynews.controllers.activities.SearchActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.fakes.RoboMenuItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ToolbarAndDrawerTest {

    private MainActivity mainActivity;

    @Before
    public void setUp()
    {
        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .start()
                .resume()
                .get();
    }

    @Test
    public void checkMainActivityNotNull(){
        assertNotNull(mainActivity);
    }

    @Test
    public void clickIconSearchInToolbar_shouldStartSearchActivity() {
        // Check if the SearchActivity starts when click on searchIcon in the toolBar
        mainActivity.onOptionsItemSelected(new RoboMenuItem(R.id.menu_activity_main_search));
        Intent expectedIntent = new Intent(mainActivity, SearchActivity.class);
        Intent actual = shadowOf(mainActivity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickNotificationsInMenu_shouldStartNotificationsActivity() {
        // Check if the SearchActivity starts when click on searchIcon in the toolBar
        mainActivity.onOptionsItemSelected(new RoboMenuItem(R.id.menu_activity_main_notifications));
        Intent expectedIntent = new Intent(mainActivity, NotificationsActivity.class);
        Intent actual = shadowOf(mainActivity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickNotificationsInDrawer_shouldStartNotificationsActivity() {
        // Check if the NotificationsActivity starts, when click on Notifications in the NavigationDrawer
        mainActivity.onNavigationItemSelected(new RoboMenuItem(R.id.activity_main_drawer_notifications));
        Intent expectedIntent = new Intent(mainActivity, NotificationsActivity.class);
        Intent actual = shadowOf(mainActivity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}