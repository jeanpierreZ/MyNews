package com.jpz.mynews.Controllers.Fragments;


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
import android.widget.EditText;
import android.widget.TextView;

import com.jpz.mynews.Controllers.Utils.MySharedPreferences;
import com.jpz.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResearchFragment extends Fragment {

    private Button searchButton;
    private EditText editQuery;
    private MySharedPreferences prefs;

    // Declare callback
    private OnSearchClickedListener mCallback;

    public ResearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout for ResearchFragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Context context = getActivity();
        if (context != null) {
            prefs = new MySharedPreferences(context.getApplicationContext());
        }

        // Get widgets from layout
        editQuery = view.findViewById(R.id.search_fragment_query);

        TextView textOne = view.findViewById(R.id.search_fragment_text_one);
        EditText dateOne = view.findViewById(R.id.search_fragment_edit_date_one);

        TextView textTwo = view.findViewById(R.id.search_fragment_text_two);
        EditText dateTwo = view.findViewById(R.id.search_fragment_edit_date_two);

        CheckBox checkBoxOne = view.findViewById(R.id.search_fragment_checkbox_one);
        CheckBox checkBoxTwo = view.findViewById(R.id.search_fragment_checkbox_two);
        CheckBox checkBoxThree = view.findViewById(R.id.search_fragment_checkbox_three);
        CheckBox checkBoxFour = view.findViewById(R.id.search_fragment_checkbox_four);
        CheckBox checkBoxFive = view.findViewById(R.id.search_fragment_checkbox_five);
        CheckBox checkBoxSix = view.findViewById(R.id.search_fragment_checkbox_six);

        searchButton = view.findViewById(R.id.search_fragment_button);

        searchButton.setEnabled(false);

        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check the query term input
                searchButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Action when click on search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the query terms when button clicked
                String queryTerms = editQuery.getText().toString();
                prefs.saveQueryTerms(queryTerms);
                Log.i("TAG", "ResearchFragment save queryTerms : "+ queryTerms);

                // Spread the click to the parent activity
                mCallback.OnSearchClicked(v);
            }
        });

        return view;
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
