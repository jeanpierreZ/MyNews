package com.jpz.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jpz.mynews.Controllers.Fragments.MainFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    // Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        // Number of page to show
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        // Page to return
        return(MainFragment.newInstance(position));
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
                return "Technology";
            default:
                return null;
        }
    }
}
