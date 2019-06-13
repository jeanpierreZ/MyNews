package com.jpz.mynews.Controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SearchAndNotifyFragment extends Fragment {

    // Booleans for enable the searchButton
    private Boolean queryInput = false;

    public SearchAndNotifyFragment() {
        // Required empty public constructor
    }

    // Overloading methods for child fragments
    protected abstract int getFragmentLayout();
    protected abstract void createCallbackToParentActivity();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout identifier from abstract method
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    protected Boolean getEditQueryBoolean(CharSequence s) {
        // Verify that the term is entered as the first condition to enable the button or switch
        if (s.toString().length() != 0) {
            queryInput = true;
        } else if (s.toString().length() == 0) {
            queryInput = false;
        }
        return queryInput;
    }

    protected String getEditQueryText(Boolean queryInput, EditText editQuery) {
        // Get the text entered
        String queryTerms;
        if (queryInput)
            queryTerms = editQuery.getText().toString();
        else
            queryTerms = null;
        return queryTerms;
    }

    protected Boolean getBoxChecked(boolean isChecked) {
        // Verify that a checkBox is checked as the second condition to enable the button or switch
        boolean boxNumberChecked;
        if (isChecked)
            boxNumberChecked = true;
        else
            boxNumberChecked = false;
        return boxNumberChecked;
    }

    protected String getDeskFromBox(boolean isChecked, CheckBox checkBox) {
        // If a checkBox is checked, load desk value in a string
        String desk;
        if (isChecked) {
            desk = checkBox.getText().toString();
        } else {
            desk = null;
        }
        return desk;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    // Declare our interface that will be implemented by any container activity
    public interface OnSearchAndNotifyClickedListener {
        void OnSearchOrNotifyClicked(View view);
        void saveQueryTermsValue(String queryTerms);
        void saveBeginDateValue(String beginDate);
        void saveEndDateValue(String endDate);
        void saveDesksValues(String[] deskList);
    }

}
