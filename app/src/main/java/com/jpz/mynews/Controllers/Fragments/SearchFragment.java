package com.jpz.mynews.Controllers.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends SearchAndNotificationsFragment {

    private Calendar calendar;
    private Context context;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout for SearchFragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        context = getActivity();

        // Make the button, the texts and  the editTexts visible, because they are used for search
        searchButton.setVisibility(View.VISIBLE);
        textOne.setVisibility(View.VISIBLE);
        textTwo.setVisibility(View.VISIBLE);
        editBeginDate.setVisibility(View.VISIBLE);
        editEndDate.setVisibility(View.VISIBLE);

        //---------------------------------------------------------------
        // Create & display DatePicker in EditText, save date

        calendar = Calendar.getInstance();

        // ---  --- --- --- --- --- --- --- --- --- --- ---
        // Actions when click on editText under "Begin Date"
        // ---  --- --- --- --- --- --- --- --- --- --- ---

        // Display and save the begin date chosen
        final DatePickerDialog.OnDateSetListener beginDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                displayBeginDate();
                searchQuery.beginDate = editBeginDate.getText().toString();
            }
        };

        // Display the calendar to choose the begin date
        editBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, beginDateSetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // ---  --- --- --- --- --- --- --- --- --- --- ---
        // Actions when click on editText under "End Date"
        // ---  --- --- --- --- --- --- --- --- --- --- ---

        // Display and save the end date chosen
        final DatePickerDialog.OnDateSetListener EndDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                displayEndDate();
                searchQuery.endDate = editEndDate.getText().toString();
            }
        };

        // Display the calendar to choose the end date
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, EndDateSetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //---------------------------------------------------------------

        // Action when click on the button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Spread the click to the parent activity with values for the research
                mCallback.onSearchOrNotifyClicked(searchQuery);
            }
        });

        //---------------------------------------------------------------

        return view;
    }

    //---------------------------------------------------------------
    // Private methods to display the dates in the editTexts

    private void displayBeginDate() {
        // Set date format
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        // Show calendar to choose begin date
        editBeginDate.setText(sdf.format(calendar.getTime()));
    }

    private void displayEndDate() {
        // Set date format
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        // Show calendar to choose begin date
        editEndDate.setText(sdf.format(calendar.getTime()));
    }
}