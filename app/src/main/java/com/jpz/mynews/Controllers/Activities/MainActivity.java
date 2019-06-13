package com.jpz.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jpz.mynews.Controllers.Fragments.NewsFragment;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.PageAdapter;

public class MainActivity extends AppCompatActivity implements NewsFragment.OnWebClickedListener {

    // Create key for Intent
    public static final String KEY_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();
        configureViewPagerAndTabs();
    }

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
                Intent searchActivity = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchActivity);
                return true;
            case R.id.menu_activity_main_notifications:
                Intent notificationsActivity = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(notificationsActivity);
                return true;
            case R.id.menu_activity_main_help:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_activity_main_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Set the Toolbar
        setSupportActionBar(toolbar);
    }

    private void configureViewPagerAndTabs(){
        // Get ViewPager from layout
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        // Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);
        // Get TabLayout from layout
        TabLayout tabs= findViewById(R.id.activity_main_tabs);
        //  Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs are displayed with scrolling
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    //----------------------------------------------------------------------------------
    // Implements methods from NewsFragment to create Intent for WebViewActivity

    @Override
    public void OnWebClicked(int position, String url) {
        // Spread the click with the url of the article to WebViewActivity
        Intent webViewActivity = new Intent(MainActivity.this, WebViewActivity.class);
        webViewActivity.putExtra(KEY_URL, url);
        startActivity(webViewActivity);
    }
}
