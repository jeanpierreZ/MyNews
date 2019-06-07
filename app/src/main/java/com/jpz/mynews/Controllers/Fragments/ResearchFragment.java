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
import android.widget.TextView;

import com.jpz.mynews.Controllers.Utils.Desk;
import com.jpz.mynews.Controllers.Utils.MySharedPreferences;
import com.jpz.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResearchFragment extends Fragment {

    private Button searchButton;

    private EditText editQuery;
    private EditText editBeginDate, editEndDate;

    private CheckBox checkBoxOne, checkBoxTwo, checkBoxThree, checkBoxFour, checkBoxFive, checkBoxSix;
    private String checkBoxOneValue, checkBoxTwoValue, checkBoxThreeValue,
            checkBoxFourValue, checkBoxFiveValue, checkBoxSixValue;

    private Boolean queryInput = false;
    private Boolean boxOneChecked = false;
    private Boolean boxTwoChecked = false;
    private Boolean boxThreeChecked = false;
    private Boolean boxFourChecked = false;
    private Boolean boxFiveChecked = false;
    private Boolean boxSixChecked = false;

    private Calendar calendar;

    private MySharedPreferences prefs;

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
        if (context != null) {
            prefs = new MySharedPreferences(context);
        }

        // Get widgets from layout
        editQuery = view.findViewById(R.id.search_fragment_query);

        TextView textOne = view.findViewById(R.id.search_fragment_text_one);
        editBeginDate = view.findViewById(R.id.search_fragment_edit_begin_date);

        TextView textTwo = view.findViewById(R.id.search_fragment_text_two);
        editEndDate = view.findViewById(R.id.search_fragment_edit_end_date);

        checkBoxOne = view.findViewById(R.id.search_fragment_checkbox_one);
        checkBoxTwo = view.findViewById(R.id.search_fragment_checkbox_two);
        checkBoxThree = view.findViewById(R.id.search_fragment_checkbox_three);
        checkBoxFour = view.findViewById(R.id.search_fragment_checkbox_four);
        checkBoxFive = view.findViewById(R.id.search_fragment_checkbox_five);
        checkBoxSix = view.findViewById(R.id.search_fragment_checkbox_six);

        searchButton = view.findViewById(R.id.search_fragment_button);

        //Initialize the search button to be disabled on fragment creation
        searchButton.setEnabled(false);

        //------------------------------------------------------------------------------------------
        // Detect if a query is entered
        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Verify that the term is entered as the first condition to enable the search button
                if (s.toString().length() != 0)
                    queryInput = true;
                else if (s.toString().length() == 0)
                    queryInput = false;
                setSearchButtonEnabled();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //------------------------------------------------------------------------------------------
        // Create, display & save DatePicker in EditText

        calendar = Calendar.getInstance();

        // Actions when click on editText under "Begin Date"
        final DatePickerDialog.OnDateSetListener beginDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                displayBeginDate();
            }
        };

        editBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, beginDateSetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Actions when click on editText under "End Date"
        final DatePickerDialog.OnDateSetListener EndDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                displayEndDate();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, EndDateSetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //------------------------------------------------------------------------------------------
        // Set texts & values for checkBoxes

        checkBoxOne.setText(Desk.Foreign.toDesk());
        checkBoxTwo.setText(Desk.Business.toDesk());
        checkBoxThree.setText(Desk.T_Magazine.toDesk());
        checkBoxFour.setText(Desk.Environment.toDesk());
        checkBoxFive.setText(Desk.Science.toDesk());
        checkBoxSix.setText(Desk.Sports.toDesk());

        // If a checkBox is checked, load desk value in a string
        // Verify it also as the second condition to enable the search button

        checkBoxOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxOneValue = checkBoxOne.getText().toString();
                    boxOneChecked = true;
                }
                else {
                    checkBoxOneValue = null;
                    boxOneChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        checkBoxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxTwoValue = checkBoxTwo.getText().toString();
                    boxTwoChecked = true;
                }
                else {
                    checkBoxTwoValue = null;
                    boxTwoChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        checkBoxThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxThreeValue = checkBoxThree.getText().toString();
                    boxThreeChecked = true;
                }
                else {
                    checkBoxThreeValue = null;
                    boxThreeChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        checkBoxFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxFourValue = checkBoxFour.getText().toString();
                    boxFourChecked = true;
                }
                else {
                    checkBoxFourValue = null;
                    boxFiveChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        checkBoxFive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxFiveValue = checkBoxFive.getText().toString();
                    boxFiveChecked = true;
                }
                else {
                    checkBoxFiveValue = null;
                    boxFiveChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        checkBoxSix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBoxSixValue = checkBoxSix.getText().toString();
                    boxSixChecked = true;
                }
                else {
                    checkBoxSixValue = null;
                    boxSixChecked = false;
                }
                setSearchButtonEnabled();
            }
        });

        //------------------------------------------------------------------------------------------
        // Action when click on search button

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the methods to save value for the research
                saveQueryTermsValue();
                saveBeginDateValue();
                saveEndDateValue();
                saveCheckBoxesValues();

                // Spread the click to the parent activity
                mCallback.OnSearchClicked(v);
            }
        });
        //------------------------------------------------------------------------------------------

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

    private void saveQueryTermsValue() {
        // Save the query terms when the search button is clicked
        String queryTerms = editQuery.getText().toString();
        prefs.saveQueryTerms(queryTerms);
        Log.i("TAG", "ResearchFragment save queryTerms : "+ queryTerms);
    }

    private void saveBeginDateValue() {
        // Save the begin date when the search button is clicked
        String beginDate = editBeginDate.getText().toString();
        prefs.saveBeginDate(beginDate);
        Log.i("TAG", "ResearchFragment save beginDate : "+ beginDate);
    }

    private void saveEndDateValue() {
        // Save the end date when the search button is clicked
        String endDate = editEndDate.getText().toString();
        prefs.saveEndDate(endDate);
        Log.i("TAG", "ResearchFragment save endDate : "+ endDate);
    }

    private void saveCheckBoxesValues() {
        // Save the checkBoxes values when the search button is clicked
        prefs.saveBoxesValues(checkBoxOneValue, checkBoxTwoValue, checkBoxThreeValue,
                checkBoxFourValue, checkBoxFiveValue, checkBoxSixValue);
        Log.i("TAG", "ResearchFragment save boxes : "+ checkBoxOneValue + checkBoxTwoValue +
                checkBoxThreeValue + checkBoxFourValue + checkBoxFiveValue + checkBoxSixValue );
    }

    private void setSearchButtonEnabled() {
        // Active the search button when the query is entered and at least a checkBox is checked
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
