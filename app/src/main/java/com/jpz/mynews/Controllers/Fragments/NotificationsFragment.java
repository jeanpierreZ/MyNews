package com.jpz.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends SearchAndNotificationsFragment {

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

        //---------------------------------------------------------------

        // Action when click on the switch
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Spread the click to the parent activity with values for the notifications
                searchQuery.isChecked = isChecked;
                mCallback.onSearchOrNotifyClicked(searchQuery);

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