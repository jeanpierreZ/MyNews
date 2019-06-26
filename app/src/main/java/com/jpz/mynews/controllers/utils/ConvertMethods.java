package com.jpz.mynews.controllers.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class ConvertMethods {

    // Display and format section & subsection of an article
    public String convertSectionSubsection(String section, String subSection) {
        String sectionSubsection;
        // If subsection is null...
        if (subSection != null) {
            // If subsection is empty, don't call it
            if (subSection.isEmpty())
                sectionSubsection = section;
            else
                sectionSubsection = section + " > " + subSection;
        }
        // ...don't call it
        else
            sectionSubsection = section;

        return sectionSubsection;
    }

    // Format the date received from the API to display it in dd/MM/yyyy
    public String convertDate(String dateFromAPI) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        Date date;
        String newDate = "";
        try {
            date = inputFormat.parse(dateFromAPI);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

    // Format the date received from the DatePickerDialog to make the request
    public String convertDateToSearch(String BeginOrEndDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        Date date;
        String newDate = "";

        try {
            date = inputFormat.parse(BeginOrEndDate);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

}