package com.jpz.mynews.Controllers.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.Models.SearchQuery;
import com.jpz.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResearchFragment extends Fragment {

    // Widgets layout
    private Button searchButton;
    private EditText editQuery;
    private EditText editBeginDate, editEndDate;
    private CheckBox boxOne, boxTwo, boxThree, boxFour, boxFive, boxSix;

    // Booleans for enable the searchButton
    private Boolean queryInput = false;
    private Boolean boxOneChecked = false;
    private Boolean boxTwoChecked = false;
    private Boolean boxThreeChecked = false;
    private Boolean boxFourChecked = false;
    private Boolean boxFiveChecked = false;
    private Boolean boxSixChecked = false;

    private Calendar calendar;

    private SearchQuery searchQuery = new SearchQuery();

    private Context context;

    // Declare callback
    private OnSearchClickedListener mCallback;

    public ResearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout for ResearchFragment
        View view = inflater.inflate(R.layout.fragment_research, container, false);

        context = getActivity();

        // Get widgets from layout
        editQuery = view.findViewById(R.id.search_fragment_query);

        editBeginDate = view.findViewById(R.id.search_fragment_edit_begin_date);
        editEndDate = view.findViewById(R.id.search_fragment_edit_end_date);

        boxOne = view.findViewById(R.id.search_fragment_checkbox_one);
        boxTwo = view.findViewById(R.id.search_fragment_checkbox_two);
        boxThree = view.findViewById(R.id.search_fragment_checkbox_three);
        boxFour = view.findViewById(R.id.search_fragment_checkbox_four);
        boxFive = view.findViewById(R.id.search_fragment_checkbox_five);
        boxSix = view.findViewById(R.id.search_fragment_checkbox_six);

        searchButton = view.findViewById(R.id.search_fragment_button);

        //Initialize the search button to be disabled on fragment creation
        searchButton.setEnabled(false);

        //---------------------------------------------------------------
        // Detect if a query is entered

        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Verify that the term is entered as the first condition to enable the search button
                if (s.toString().length() != 0) {
                    queryInput = true;
                    searchQuery.queryTerms = editQuery.getText().toString();
                }
                else if (s.toString().length() == 0) {
                    queryInput = false;
                    searchQuery.queryTerms = null;
                }
                setSearchButtonEnabled();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //---------------------------------------------------------------
        // Create, display & save DatePicker in EditText

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
                if (isChecked) {
                    searchQuery.desks[0] = boxOne.getText().toString();
                    boxOneChecked = true;
                }
                else {
                    searchQuery.desks[0] = null;
                    boxOneChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        boxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchQuery.desks[1] = boxTwo.getText().toString();
                    boxTwoChecked = true;
                }
                else {
                    searchQuery.desks[1] = null;
                    boxTwoChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        boxThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchQuery.desks[2] = boxThree.getText().toString();
                    boxThreeChecked = true;
                }
                else {
                    searchQuery.desks[2] = null;
                    boxThreeChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        boxFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchQuery.desks[3] = boxFour.getText().toString();
                    boxFourChecked = true;
                }
                else {
                    searchQuery.desks[3] = null;
                    boxFourChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        boxFive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchQuery.desks[4] = boxFive.getText().toString();
                    boxFiveChecked = true;
                }
                else {
                    searchQuery.desks[4] = null;
                    boxFiveChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        boxSix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchQuery.desks[5] = boxSix.getText().toString();
                    boxSixChecked = true;
                }
                else {
                    searchQuery.desks[5] = null;
                    boxSixChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        //---------------------------------------------------------------
        // Actions when click on search button

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Spread the click to the parent activity
                mCallback.OnSearchClicked(v);

                // Call the methods to save value for the research
                mCallback.saveQueryTermsValue(searchQuery.queryTerms);
                Log.i("TAG", "ResearchFragment save queryTerms : "+ searchQuery.queryTerms);

                mCallback.saveBeginDateValue(searchQuery.beginDate);
                Log.i("TAG", "ResearchFragment save beginDate : "+ searchQuery.beginDate);

                mCallback.saveEndDateValue(searchQuery.endDate);
                Log.i("TAG", "ResearchFragment save endDate : "+ searchQuery.endDate);

                mCallback.saveDesksValues(searchQuery.desks);
                Log.i("TAG", "ResearchFragment desks : " +searchQuery.desks[0]+searchQuery.desks[1]+
                        searchQuery.desks[2]+searchQuery.desks[3]+searchQuery.desks[4]+searchQuery.desks[5]);
            }
        });
        //---------------------------------------------------------------

        return view;
    }

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

    private void setSearchButtonEnabled() {
        // Active the search button when the query is entered and at least one checkBox is checked
        if (queryInput && (boxOneChecked || boxTwoChecked || boxThreeChecked
                || boxFourChecked || boxFiveChecked || boxSixChecked))
            searchButton.setEnabled(true);
        else
        searchButton.setEnabled(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    // Declare our interface that will be implemented by any container activity
    public interface OnSearchClickedListener {
        void OnSearchClicked(View view);
        void saveQueryTermsValue(String queryTerms);
        void saveBeginDateValue(String beginDate);
        void saveEndDateValue(String endDate);
        void saveDesksValues(String[] deskList);
    }

    // Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            // Parent activity will automatically subscribe to callback
            mCallback = (OnSearchClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnSearchClickedListener");
        }
    }
}