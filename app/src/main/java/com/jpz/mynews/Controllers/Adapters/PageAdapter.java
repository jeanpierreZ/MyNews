package com.jpz.mynews.Controllers.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jpz.mynews.Controllers.Fragments.ArticleSearchFragment;
import com.jpz.mynews.Controllers.Fragments.MostPopularFragment;
import com.jpz.mynews.Controllers.Fragments.TopStoriesFragment;
import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.R;

public class PageAdapter extends FragmentStatePagerAdapter {

    // Field for the number of page to show
    private static final int NUM_ITEMS = 5;

    private Context context;

    // Default Constructor
    public PageAdapter(FragmentManager mgr, Context _context) {
        super(mgr);
        context = _context;
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
                return ArticleSearchFragment.newInstance(Desk.Business);
            case 4:
                return ArticleSearchFragment.newInstance(Desk.Magazine);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Return page title
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.topStories);
            case 1:
                return context.getResources().getString(R.string.mostPopular);
            case 2:
                return Desk.Foreign.toDesk();
            case 3:
                return Desk.Business.toDesk();
            case 4:
                return Desk.Magazine.toDesk();
            default:
                return null;
        }
    }
}
