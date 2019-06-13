package com.jpz.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends SearchAndNotifyFragment {

    // Widgets layout
    private Switch notificationSwitch;
    private EditText editQuery;
    private CheckBox boxOne, boxTwo, boxThree, boxFour, boxFive,boxSix;

    // Booleans for enable the searchButton
    private Boolean queryInput = false;
    private Boolean boxOneChecked = false;
    private Boolean boxTwoChecked = false;
    private Boolean boxThreeChecked = false;
    private Boolean boxFourChecked = false;
    private Boolean boxFiveChecked = false;
    private Boolean boxSixChecked = false;

    private SearchQuery searchQuery = new SearchQuery();

    // Declare callback
    private OnSearchAndNotifyClickedListener mCallback;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view != null) {
            // Get widgets from layout
            editQuery = view.findViewById(R.id.notifications_fragment_query);

            boxOne = view.findViewById(R.id.notifications_fragment_checkbox_one);
            boxTwo = view.findViewById(R.id.notifications_fragment_checkbox_two);
            boxThree = view.findViewById(R.id.notifications_fragment_checkbox_three);
            boxFour = view.findViewById(R.id.notifications_fragment_checkbox_four);
            boxFive = view.findViewById(R.id.notifications_fragment_checkbox_five);
            boxSix = view.findViewById(R.id.notifications_fragment_checkbox_six);

            notificationSwitch = view.findViewById(R.id.notifications_switch);
        }

        //Initialize the switch to be disabled on fragment creation
        notificationSwitch.setEnabled(false);

        //---------------------------------------------------------------
        // Detect if a query is entered

        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                queryInput = getEditQueryBoolean(s);
                searchQuery.queryTerms = getEditQueryText(queryInput, editQuery);
                setSearchAndNotifyEnabled(queryInput);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //---------------------------------------------------------------
        // Take today's date and save it for daily notification

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        searchQuery.beginDate = sdf.format(today);
        searchQuery.endDate = sdf.format(today);

        //---------------------------------------------------------------
        // Set texts & values for checkBoxes

        boxOne.setText(Desk.Foreign.toDesk());
        boxTwo.setText(Desk.Business.toDesk());
        boxThree.setText(Desk.Magazine.toDesk());
        boxFour.setText(Desk.Environment.toDesk());
        boxFive.setText(Desk.Science.toDesk());
        boxSix.setText(Desk.Sports.toDesk());

        // If a checkBox is checked, load desk value in a string
        // Verify it also as the second condition to enable the search button

        boxOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boxOneChecked = getBoxChecked(isChecked);
                searchQuery.desks[0] = getDeskFromBox(boxOneChecked, boxOne);
                setSearchAndNotifyEnabled(boxOneChecked);
            }
        });

        boxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boxTwoChecked = getBoxChecked(isChecked);
                searchQuery.desks[1] = getDeskFromBox(boxTwoChecked, boxTwo);
                setSearchAndNotifyEnabled(boxTwoChecked);
            }
        });

        boxThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boxThreeChecked = getBoxChecked(isChecked);
                searchQuery.desks[2] = getDeskFromBox(boxThreeChecked, boxThree);
                setSearchAndNotifyEnabled(boxThreeChecked);
            }
        });

        boxFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boxFourChecked = getBoxChecked(isChecked);

                searchQuery.desks[3] = getDeskFromBox(boxFourChecked, boxFour);

                setSearchAndNotifyEnabled(boxFourChecked);
            }
        });

        boxFive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boxFiveChecked = getBoxChecked(isChecked);
                searchQuery.desks[4] = getDeskFromBox(boxFiveChecked, boxFive);
                setSearchAndNotifyEnabled(boxFiveChecked);
            }
        });

        boxSix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boxSixChecked = getBoxChecked(isChecked);
                searchQuery.desks[5] = getDeskFromBox(boxSixChecked, boxSix);
                setSearchAndNotifyEnabled(boxSixChecked);
            }
        });

        //---------------------------------------------------------------
        // Actions when click on search button

        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Spread the click to the parent activity
                mCallback.OnSearchOrNotifyClicked(v);

                // Call the methods to save value for the research
                mCallback.saveQueryTermsValue(searchQuery.queryTerms);
                Log.i("TAG", "NotificationsFragment save queryTerms : "+ searchQuery.queryTerms);

                mCallback.saveBeginDateValue(searchQuery.beginDate);
                Log.i("TAG", "NotificationsFragment save beginDate : "+ searchQuery.beginDate);

                mCallback.saveEndDateValue(searchQuery.endDate);
                Log.i("TAG", "NotificationsFragment save endDate : "+ searchQuery.endDate);

                mCallback.saveDesksValues(searchQuery.desks);
                Log.i("TAG", "NotificationsFragment desks : " +searchQuery.desks[0]+searchQuery.desks[1]+
                        searchQuery.desks[2]+searchQuery.desks[3]+searchQuery.desks[4]+searchQuery.desks[5]);
            }
        });
        //---------------------------------------------------------------

        return view;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_notifications;
    }

    private void setSearchAndNotifyEnabled(Boolean query) {
        // Active the switch button when the query is entered and at least one checkBox is checked
        if (queryInput && (boxOneChecked || boxTwoChecked || boxThreeChecked
                || boxFourChecked || boxFiveChecked || boxSixChecked))
            notificationSwitch.setEnabled(true);
        else
            notificationSwitch.setEnabled(false);
    }

    @Override
    protected void createCallbackToParentActivity() {
        try {
            // Parent activity will automatically subscribe to callback
            mCallback = (OnSearchAndNotifyClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnNotifyClickedListener");
        }
    }

}
