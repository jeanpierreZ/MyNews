package com.jpz.mynews.Controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.jpz.mynews.Controllers.Utils.MySharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends BaseSearchFragment {

    private MySharedPreferences prefs;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the parent layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Make the switch and the divider visible, because they are used for notifications
        notificationSwitch.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);

        // Use MySharedPreferences...
        Context context = getActivity();
        if (context != null)
        prefs = new MySharedPreferences(context);

        // ... to load data from the active notification
        searchQuery.switchIsChecked = prefs.getSwitchState();
        searchQuery.queryTerms = prefs.getQueryTerms();
        searchQuery.desks = prefs.getDesksValues();

        Log.i("TAG", "NotificationsFragment give queryTerms : "+ searchQuery.queryTerms);
        Log.i("TAG", "NotificationsFragment give checkedState : "+ searchQuery.switchIsChecked);
        Log.i("TAG", "NotificationsFragment give boxOne : "
                + searchQuery.desks[0] + searchQuery.desks[1] + searchQuery.desks[2]
                + searchQuery.desks[3] + searchQuery.desks[4] + searchQuery.desks[5]);

        // If the switch was checked to notify, remember the state of widgets
        if (searchQuery.switchIsChecked) {

            // Check the switch
            notificationSwitch.setChecked(true);

            // Display the query of the notification in the EditText
            editQuery.setText(searchQuery.queryTerms);

            // Check the boxes who have a value
            if (searchQuery.desks[0] != null)
                boxOne.setChecked(true);
            if (searchQuery.desks[1] != null)
                boxTwo.setChecked(true);
            if (searchQuery.desks[2] != null)
                boxThree.setChecked(true);
            if (searchQuery.desks[3] != null)
                boxFour.setChecked(true);
            if (searchQuery.desks[4] != null)
                boxFive.setChecked(true);
            if (searchQuery.desks[5] != null)
                boxSix.setChecked(true);
        }

        //---------------------------------------------------------------

        // Action when click on the switch
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Spread the click to the parent activity with values for the notifications
                searchQuery.switchIsChecked = isChecked;
                mCallback.onSearchOrNotifyClicked(searchQuery);

                Log.i("TAG", "NotificationsFragment save switchIsChecked : "+ searchQuery.switchIsChecked);
                Log.i("TAG", "NotificationsFragment save queryTerms : "+ searchQuery.queryTerms);
                Log.i("TAG", "NotificationsFragment desks : "
                        +searchQuery.desks[0]+searchQuery.desks[1]+searchQuery.desks[2]
                        +searchQuery.desks[3]+searchQuery.desks[4]+searchQuery.desks[5]);
            }
        });

        //---------------------------------------------------------------

        return view;
    }
}