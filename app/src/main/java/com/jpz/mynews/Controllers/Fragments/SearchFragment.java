package com.jpz.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jpz.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get layout for SearchFragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Get widgets from layout
        EditText query = view.findViewById(R.id.search_fragment_query);

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

        Button button = view.findViewById(R.id.search_fragment_button);


        return view;

    }

}
