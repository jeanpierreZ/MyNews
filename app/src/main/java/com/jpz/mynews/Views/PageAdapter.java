package com.jpz.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jpz.mynews.Controllers.Fragments.ArticleSearchFragment;
import com.jpz.mynews.Controllers.Fragments.MostPopularFragment;
import com.jpz.mynews.Controllers.Fragments.TopStoriesFragment;
import com.jpz.mynews.Controllers.Utils.Desk;

public class PageAdapter extends FragmentStatePagerAdapter {

    // Field for the number of page to show
    private static final int NUM_ITEMS = 5;

    // Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        // Number of page to show
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        // Page to return
        switch (position) {
            case 0:
                return TopStoriesFragment.newInstance();
            case 1:
                return MostPopularFragment.newInstance();
            case 2:
                return ArticleSearchFragment.newInstance(Desk.Foreign);
            case 3:
                return ArticleSearchFragment.newInstance(Desk.Financial);
            case 4:
                return ArticleSearchFragment.newInstance(Desk.Technology);
            default:
                return null;
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
