package com.jpz.mynews.Controllers.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class ConvertMethods {

    public String convertSectionSubsection(String section, String subSection) {
        // Display section & subsection of an article
        String sectionSubsection;
        // If subsection is null...
        if (subSection != null)

            // If subsection is empty, don't call it
            if (subSection.isEmpty())
                sectionSubsection = section;
            else
                sectionSubsection = section + " > " + subSection;

            // ...don't call it
        else
            sectionSubsection = section;

        return sectionSubsection;
    }

    public String convertDate(String dateNews) {
        // Build date in dd/MM/yyyy for PubDate
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        Date date;
        String newDate = "";

        try {
            date = inputFormat.parse(dateNews);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

    public String convertBeginOrEndDate(String BeginEndDate) {
        // Build date in dd/MM/yyyy for PubDate
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        Date date;
        String newDate = "";

        try {
            date = inputFormat.parse(BeginEndDate);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

}
