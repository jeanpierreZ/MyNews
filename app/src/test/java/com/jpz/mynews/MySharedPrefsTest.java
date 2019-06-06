package com.jpz.mynews;


import com.jpz.mynews.Controllers.Utils.MySharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

public class MySharedPrefsTest {

    private MySharedPreferences prefs;
    private String queryTerms;
    private String beginDate;

    @Before
    public void setStringsForTests() {
        // Query terms for research
        queryTerms = "query terms";
        beginDate = "20190613";
    }

    @Test
    public void getQueryTermsTest() {
        // Mock context
        prefs = mock(MySharedPreferences.class);
        // Mock with value put in method saveQueryTerms()
        when(prefs.getQueryTerms()).thenReturn(queryTerms);
        assertEquals("query terms", prefs.getQueryTerms());
    }

    @Test
    public void getBeginDateTest() {
        // Mock context
        prefs = mock(MySharedPreferences.class);
        // Mock with value put in method saveQueryTerms()
        when(prefs.getQueryTerms()).thenReturn(beginDate);
        assertEquals("20190613", prefs.getQueryTerms());
    }

}
