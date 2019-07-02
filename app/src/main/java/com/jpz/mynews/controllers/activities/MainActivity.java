package com.jpz.mynews.controllers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jpz.mynews.controllers.fragments.NewsFragment;
import com.jpz.mynews.R;
import com.jpz.mynews.controllers.adapters.PageAdapter;

public class MainActivity extends AppCompatActivity
        implements NewsFragment.OnWebClickedListener, NavigationView.OnNavigationItemSelectedListener {

    // For design
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager pager;

    // Create a key for the url of the chosen article to be consulted on the website
    public static final String KEY_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Display settings of Navigation Drawer (layout + view), Toolbar and ViewPager
        configureToolbar();
        configureDrawerLayout();
        configureViewPagerAndTabs();
        configureNavigationView();
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
    // Methods for NavigationView in NavigationDrawer

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle Navigation Item Click
        switch (item.getItemId()){
            case R.id.activity_main_drawer_topStories:
                pager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_mostPopular:
                pager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_foreign:
                pager.setCurrentItem(2);
                break;
            case R.id.activity_main_drawer_business:
                pager.setCurrentItem(3);
                break;
            case R.id.activity_main_drawer_magazine:
                pager.setCurrentItem(4);
                break;
            case R.id.activity_main_drawer_search:
                startSearchActivity();
                break;
            case R.id.activity_main_drawer_notifications:
                startNotificationsActivity();
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //----------------------------------------------------------------------------------
    // Private methods to configure design

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        toolbar = findViewById(R.id.toolbar);
        // Set the Toolbar
        setSupportActionBar(toolbar);
    }

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

    private void configureDrawerLayout(){
        drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        // "Hamburger icon"
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //----------------------------------------------------------------------------------
    // Private methods to launch activities

    private void startSearchActivity() {
        Intent searchActivity =
                new Intent(MainActivity.this, SearchActivity.class);
        startActivity(searchActivity);
    }

    private void startNotificationsActivity() {
        Intent notificationsActivity =
                new Intent(MainActivity.this, NotificationsActivity.class);
        startActivity(notificationsActivity);
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