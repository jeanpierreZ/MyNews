package com.jpz.mynews;


import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.Controllers.Utils.MySharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

public class MySharedPrefsTest {

    private MySharedPreferences prefs;
    private String queryTerms;
    private String beginDate;
    private String endDate;
    private String[] checkBoxes = new String[6];

    @Before
    public void setStringsForTests() {
        // Query terms for research
        queryTerms = "query terms";

        beginDate = "20190514";
        endDate = "20190531";

        String checkBoxOne = Desk.Foreign.toDesk();
        String checkBoxTwo = Desk.Business.toDesk();
        String checkBoxThree = Desk.Magazine.toDesk();
        String checkBoxFour = Desk.Environment.toDesk();
        String checkBoxFive = Desk.Science.toDesk();
        String checkBoxSix = Desk.Sports.toDesk();

        checkBoxes[0] = checkBoxOne;
        checkBoxes[1] = checkBoxTwo;
        checkBoxes[2] = checkBoxThree;
        checkBoxes[3] = checkBoxFour;
        checkBoxes[4] = checkBoxFive;
        checkBoxes[5] = checkBoxSix;
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
        // Mock with value put in method saveBeginDate()
        when(prefs.getQueryTerms()).thenReturn(beginDate);
        assertEquals("20190514", prefs.getQueryTerms());
    }

    @Test
    public void getEndDateTest() {
        // Mock context
        prefs = mock(MySharedPreferences.class);
        // Mock with value put in method saveEndDate()
        when(prefs.getQueryTerms()).thenReturn(endDate);
        assertEquals("20190531", prefs.getQueryTerms());
    }

    @Test
    public void getBoxesValuesTest() {
        // Mock context
        prefs = mock(MySharedPreferences.class);
        // Mock with value put in method saveBoxesValues()
        when(prefs.getBoxesValues()).thenReturn(checkBoxes);

        String[] expectedResult = {"Foreign", "Business", "Magazine",
                "Environment", "Science", "Sports"};

        assertArrayEquals(expectedResult, prefs.getBoxesValues());
    }
}
