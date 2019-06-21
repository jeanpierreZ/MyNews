package com.jpz.mynews.Controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SearchAndNotificationsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    // Widgets layout
    protected EditText editQuery;
    protected TextView textOne, textTwo;
    protected EditText editBeginDate, editEndDate;
    protected CheckBox boxOne, boxTwo, boxThree, boxFour, boxFive, boxSix;
    protected Button searchButton;
    protected Switch notificationSwitch;
    protected ImageView divider;

    // Boolean for query
    protected Boolean queryInput = false;

    // Use object SearchQuery to store data
    protected SearchQuery searchQuery = new SearchQuery();

    // Declare callback
    protected SearchAndNotificationsFragment.OnSearchOrNotifyClickedListener mCallback;

    public SearchAndNotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout for SearchAndNotificationsFragment
        View view = inflater.inflate(R.layout.fragment_search_and_notifications, container, false);

        // Get widgets from layout
        editQuery = view.findViewById(R.id.search_notifications_fragment_query);

        textOne = view.findViewById(R.id.search_notifications_fragment_text_one);
        textTwo = view.findViewById(R.id.search_notifications_fragment_text_two);

        editBeginDate = view.findViewById(R.id.search_notifications_fragment_edit_begin_date);
        editEndDate = view.findViewById(R.id.search_notifications_fragment_edit_end_date);

        boxOne = view.findViewById(R.id.search_notifications_fragment_checkbox_one);
        boxTwo = view.findViewById(R.id.search_notifications_fragment_checkbox_two);
        boxThree = view.findViewById(R.id.search_notifications_fragment_checkbox_three);
        boxFour = view.findViewById(R.id.search_notifications_fragment_checkbox_four);
        boxFive = view.findViewById(R.id.search_notifications_fragment_checkbox_five);
        boxSix = view.findViewById(R.id.search_notifications_fragment_checkbox_six);

        searchButton = view.findViewById(R.id.search_notifications_fragment_button);

        notificationSwitch = view.findViewById(R.id.search_notifications_switch);
        divider = view.findViewById(R.id.search_notifications_divider);

        //Initialize the switch and the button to be disabled on fragment creation
        searchButton.setEnabled(false);
        notificationSwitch.setEnabled(false);

        //---------------------------------------------------------------

        // Detect if a query is entered
        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Verify that a query is entered as the first condition to enable the button
                // If there is a query, store it
                if (s.toString().length() != 0) {
                    queryInput = true;
                    searchQuery.queryTerms = editQuery.getText().toString();
                }
                else if (s.toString().length() == 0) {
                    queryInput = false;
                    searchQuery.queryTerms = null;
                }
                setSearchOrNotifyEnabled();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //---------------------------------------------------------------

        // Set texts & values for checkBoxes
        boxOne.setText(Desk.Foreign.toDesk());
        boxTwo.setText(Desk.Business.toDesk());
        boxThree.setText(Desk.Magazine.toDesk());
        boxFour.setText(Desk.Environment.toDesk());
        boxFive.setText(Desk.Science.toDesk());
        boxSix.setText(Desk.Sports.toDesk());

        // Listen for boxes changed state
        boxOne.setOnCheckedChangeListener(this);
        boxTwo.setOnCheckedChangeListener(this);
        boxThree.setOnCheckedChangeListener(this);
        boxFour.setOnCheckedChangeListener(this);
        boxFive.setOnCheckedChangeListener(this);
        boxSix.setOnCheckedChangeListener(this);

        //---------------------------------------------------------------

        return view;
    }

    //---------------------------------------------------------------

    // Boxes listener
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // If a checkBox is checked, load the desk value in a string
        // Also verify it as the second condition to enable the button
        switch (buttonView.getId()) {
            case R.id.search_notifications_fragment_checkbox_one:
                searchQuery.desks[0] = getDeskFromBox(isChecked, boxOne);
                setSearchOrNotifyEnabled();
                break;

            case R.id.search_notifications_fragment_checkbox_two:
                searchQuery.desks[1] = getDeskFromBox(isChecked, boxTwo);
                setSearchOrNotifyEnabled();
                break;

            case R.id.search_notifications_fragment_checkbox_three:
                searchQuery.desks[2] = getDeskFromBox(isChecked, boxThree);
                setSearchOrNotifyEnabled();
                break;

            case R.id.search_notifications_fragment_checkbox_four:
                searchQuery.desks[3] = getDeskFromBox(isChecked, boxFour);
                setSearchOrNotifyEnabled();
                break;

            case R.id.search_notifications_fragment_checkbox_five:
                searchQuery.desks[4] = getDeskFromBox(isChecked, boxFive);
                setSearchOrNotifyEnabled();
                break;

            case R.id.search_notifications_fragment_checkbox_six:
                searchQuery.desks[5] = getDeskFromBox(isChecked, boxSix);
                setSearchOrNotifyEnabled();
                break;
        }
    }

    //---------------------------------------------------------------
    // Methods to enable button or switch and to load desks value

    private void setSearchOrNotifyEnabled() {
        // Active the button or switch when the query is entered and at least one checkBox is checked
        if (queryInput && (boxOne.isChecked() || boxTwo.isChecked() || boxThree.isChecked()
                || boxFour.isChecked() || boxFive.isChecked() || boxSix.isChecked())) {
            searchButton.setEnabled(true);
            notificationSwitch.setEnabled(true);
        }
        else {
            searchButton.setEnabled(false);
            notificationSwitch.setEnabled(false);
        }
    }

    private String getDeskFromBox(boolean isChecked, CheckBox checkBox) {
        // If a checkBox is checked, load desk value in a string in order to store data
        String desk;
        if (isChecked)
            desk = checkBox.getText().toString();
        else
            desk = null;
        return desk;
    }

    //---------------------------------------------------------------
    // Callback Interface with methods

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    protected void createCallbackToParentActivity() {
        try {
            // Parent activity will automatically subscribe to callback
            mCallback = (OnSearchOrNotifyClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnSearchOrNotifyClickedListener");
        }
    }

    // Declare interface that will be implemented by any container activity
    public interface OnSearchOrNotifyClickedListener {
        void onSearchOrNotifyClicked(SearchQuery searchQuery);
    }
}