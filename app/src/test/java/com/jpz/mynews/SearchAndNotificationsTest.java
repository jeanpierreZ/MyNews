package com.jpz.mynews;


import com.jpz.mynews.Controllers.Fragments.SearchAndNotificationsFragment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SearchAndNotificationsTest extends SearchAndNotificationsFragment {

    private CharSequence s;
    private boolean isChecked;

    @Override
    protected int getFragmentLayout() {
        return 0;
    }

    @Override
    protected void setSearchAndNotifyEnabled(Boolean queryOrBox) {

    }

    @Override
    protected void createCallbackToParentActivity() {

    }

    @Before
    public void initializeString() {
        s = "abc";
        isChecked = true;
    }


    @Test
    public void getEditQueryBooleanTest() {
        assertTrue("If there is a charSequence, boolean assert true", getEditQueryBoolean(s));
    }

    @Test
    public void getBoxCheckedTest() {
        assertTrue("If a box is checked, boolean assert true", getBoxChecked(isChecked));
    }
}
