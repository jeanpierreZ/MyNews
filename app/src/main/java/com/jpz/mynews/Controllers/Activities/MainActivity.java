package com.jpz.mynews.Controllers.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jpz.mynews.Controllers.Fragments.NewsFragment;
import com.jpz.mynews.R;
import com.jpz.mynews.Controllers.Adapters.PageAdapter;

public class MainActivity extends BaseActivity implements NewsFragment.OnWebClickedListener {

    // Create a key for the url of the chosen article to be consulted on the website
    public static final String KEY_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is the FrameLayout area within the activity_base.xml
        FrameLayout contentFrameLayout = findViewById(R.id.activity_base_frame_layout);
        // Inflate the activity to load
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        // Display settings of ViewPager
        configureViewPagerAndTabs();
    }

    //----------------------------------------------------------------------------------
    // Methods for Menu in Toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_search:
                startSearchActivity();
                return true;
            case R.id.menu_activity_main_notifications:
                startNotificationsActivity();
                return true;
            case R.id.menu_activity_main_help:
                // Create a dialog window
                AlertDialog.Builder helpDialog = new AlertDialog.Builder(MainActivity.this);
                helpDialog.setMessage(getString(R.string.helpDialog));
                // Configure cancel button
                helpDialog.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                helpDialog.show();
                return true;
            case R.id.menu_activity_main_about:
                // Create a dialog window
                AlertDialog.Builder aboutDialog = new AlertDialog.Builder(MainActivity.this);
                aboutDialog.setMessage(getString(R.string.aboutDialog));
                // Configure cancel button
                aboutDialog.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------------------------------------------------------------
    // Private methods to configure design

    private void configureViewPagerAndTabs() {
        // Get ViewPager from layout
        pager = findViewById(R.id.activity_main_viewpager);
        // Set PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), getApplicationContext()));
        pager.setCurrentItem(0);
        // Get TabLayout from layout
        TabLayout tabs = findViewById(R.id.activity_main_tabs);
        //  Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs are displayed with scrolling
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    //----------------------------------------------------------------------------------
    // Implements methods from NewsFragment to create Intent for WebViewActivity

    @Override
    public void OnWebClicked(int position, String url) {
        // Spread the click with the url of the article to open WebViewActivity
        Intent webViewActivity = new Intent(MainActivity.this, WebViewActivity.class);
        webViewActivity.putExtra(KEY_URL, url);
        startActivity(webViewActivity);
    }
}
