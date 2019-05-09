package com.jpz.mynews.Controllers.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jpz.mynews.Controllers.Fragments.MainFragment;
import com.jpz.mynews.R;
import com.jpz.mynews.Views.PageAdapter;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureViewPager();
        configureAndShowMainFragment();
    }

    private void configureViewPager(){
        // Get ViewPager from layout
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        // Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);
    }


    private void configureAndShowMainFragment(){

        mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_main_viewpager);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_viewpager, mainFragment)
                    .commit();
        }
    }
}
