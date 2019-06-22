package com.jpz.mynews;

import com.jpz.mynews.Controllers.Fragments.ResultQueryFragment;
import com.jpz.mynews.Controllers.Utils.Desk;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResultQueryFragmentTest {

    private ResultQueryFragment resultQueryFragment = new ResultQueryFragment();

    private String beginOrEndDate;
    private String beginOrEndDateExpected;
    private String[] desks = {Desk.Foreign.toDesk(), Desk.Business.toDesk(), Desk.Magazine.toDesk(),
            Desk.Environment.toDesk(), Desk.Science.toDesk(), Desk.Sports.toDesk()};
    private String desksExpected;

    @Before
    public void setStringsForTests() {
        // Original fields to test
        beginOrEndDate = "21/06/2019";

        // Fields expected
        beginOrEndDateExpected = "20190621";
        desksExpected = "news_desk:(\"" + desks[0] + "\" \"" + desks[1]
                + "\" \"" + desks[2] + "\" \"" + desks[3]
                + "\" \"" + desks[4] + "\" \"" + desks[5] +"\")";
    }

    @Test
    public void fetchDateTest() {
        assertEquals(beginOrEndDateExpected, resultQueryFragment.fetchDate(beginOrEndDate));
    }

    @Test
    public void fetchDesksValuesTest() {
        assertEquals(desksExpected, resultQueryFragment.fetchDesksValues(desks));
    }
}