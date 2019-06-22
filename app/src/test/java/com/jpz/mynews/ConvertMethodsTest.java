package com.jpz.mynews;

import com.jpz.mynews.Controllers.Utils.ConvertMethods;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConvertMethodsTest {

    private ConvertMethods convertMethods = new ConvertMethods();

    private String dateNews;
    private String section;
    private String subSection;
    private String subSectionSecond;
    private String subSectionThird;
    private String dateBeginOrEnd;

    @Before
    public void setStringsForTests() {
        // Original date to convert
        dateNews = "2019-05-06T03:18:15-04:00";
        dateBeginOrEnd = "13/06/2019";

        // Originals section & subsections to convert
        section = "Section";
        subSection = "Subsection";
        subSectionSecond = "";
        subSectionThird = null;
    }

    @Test
    public void convertDateTest() {
        assertEquals("06/05/19", convertMethods.convertDate(dateNews));
    }

    @Test
    public void convertSectionSubsectionTest() {
        assertEquals("Section" + " > " + "Subsection",
                convertMethods.convertSectionSubsection(section, subSection));
    }

    @Test
    public void convertSectionSubsectionSecondTest() {
        assertEquals("Section",
                convertMethods.convertSectionSubsection(section, subSectionSecond));
    }

    @Test
    public void convertSectionSubsectionThirdTest() {
        assertEquals("Section",
                convertMethods.convertSectionSubsection(section, subSectionThird));
    }

    @Test
    public void convertDateToSearchTest() {
        assertEquals("20190613", convertMethods.convertDateToSearch(dateBeginOrEnd));

    }
}
