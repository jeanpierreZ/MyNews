package com.jpz.mynews;


import com.jpz.mynews.controllers.utils.Desk;
import com.jpz.mynews.controllers.utils.MySharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

public class MySharedPrefsTest {

    private MySharedPreferences prefs;
    private String queryTerms;
    private Boolean isChecked;
    private String[] checkBoxes = new String[6];

    @Before
    public void setStringsForTests() {
        queryTerms = "query terms";

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

        isChecked = true;
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
    public void getSwitchStateTest() {
        // Mock context
        prefs = mock(MySharedPreferences.class);
        // Mock with value put in method saveBoxesValues()
        when(prefs.getSwitchState()).thenReturn(isChecked);

        assertTrue("true", prefs.getSwitchState());
    }

    @Test
    public void getBoxesValuesTest() {
        // Mock context
        prefs = mock(MySharedPreferences.class);
        // Mock with value put in method saveBoxesValues()
        when(prefs.getDesksValues()).thenReturn(checkBoxes);

        String[] expectedResult = {"Foreign", "Business", "Magazine",
                "Environment", "Science", "Sports"};

        assertArrayEquals(expectedResult, prefs.getDesksValues());
    }
}
