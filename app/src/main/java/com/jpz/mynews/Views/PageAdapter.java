package com.jpz.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jpz.mynews.Controllers.Fragments.MainFragment;
import com.jpz.mynews.Models.API;

public class PageAdapter extends FragmentStatePagerAdapter {

    // Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        // Number of page to show
        return(5);
    }

    @Override
    public Fragment getItem(int position) {
        // Page to return
        switch (position) {
            case 0:
                return MainFragment.newInstance(API.TopStories);
            case 1:
                return MainFragment.newInstance(API.MostPopular);
            case 2:
                return MainFragment.newInstance(API.Foreign);
            case 3:
                return MainFragment.newInstance(API.Financial);
            case 4:
                return MainFragment.newInstance(API.Technology);
            default:
                return MainFragment.newInstance(API.TopStories);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Return page title
        switch (position) {
            case 0:
                return "Top Stories";
            case 1:
                return "Most Popular";
            case 2:
                return "Foreign";
            case 3:
                return "Financial";
            case 4:
                return "Technology";
            default:
                return null;
        }
    }
}
