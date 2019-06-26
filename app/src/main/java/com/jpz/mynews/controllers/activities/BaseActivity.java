package com.jpz.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jpz.mynews.R;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Display settings of Navigation Drawer (layout + view), Toolbar
        configureToolbar();
        configureDrawerLayout();
        configureNavigationView();
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
            case R.id.activity_main_drawer_homepage:
                startMainActivity();
                break;
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
    // Protected methods to configure design

    protected void configureToolbar(){
        // Get the toolbar view inside the activity layout
        toolbar = findViewById(R.id.toolbar);
        // Set the Toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    protected void configureDrawerLayout(){
        drawerLayout = findViewById(R.id.activity_base_drawer_layout);
        // "Hamburger icon"
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    protected void configureNavigationView(){
        navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //----------------------------------------------------------------------------------
    // Protected methods to start activities

    protected void startMainActivity() {
        Intent mainActivity =
                new Intent(BaseActivity.this, MainActivity.class);
        startActivity(mainActivity);
        drawerLayout.closeDrawers();
    }

    protected void startSearchActivity() {
        Intent searchActivity =
                new Intent(BaseActivity.this, SearchActivity.class);
        startActivity(searchActivity);
        drawerLayout.closeDrawers();
    }

    protected void startNotificationsActivity() {
        Intent notificationsActivity =
                new Intent(BaseActivity.this, NotificationsActivity.class);
        startActivity(notificationsActivity);
        drawerLayout.closeDrawers();
    }
}
