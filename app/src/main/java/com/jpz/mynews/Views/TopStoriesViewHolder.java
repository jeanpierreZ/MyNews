package com.jpz.mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jpz.mynews.Models.NYTResult;
import com.jpz.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class TopStoriesViewHolder extends RecyclerView.ViewHolder {
    // Represent an item (line) of TopStories in the RecyclerView
    private TextView textViewTitle;
    private TextView textViewSection;
    private TextView textViewUpdatedDate;

    // Constructor
    public TopStoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.fragment_main_item_title);
        textViewSection = itemView.findViewById(R.id.fragment_main_item_section);
        textViewUpdatedDate = itemView.findViewById(R.id.fragment_main_item_date);
    }

    public void updateWithTopStories(NYTResult result){
        // Build string for section and subsection
        String sectionSubsection;
        String section = result.getSection();
        String subSection = result.getSubsection();
        // if subsection is empty, don't call it
        if (subSection.equals(""))
            sectionSubsection = section;
        else
            sectionSubsection = section + " > " + subSection;

        // Display settings of TopStories in NYTResult
        textViewTitle.setText(result.getTitle());
        textViewSection.setText(sectionSubsection);
        textViewUpdatedDate.setText(convertDate(result.getUpdatedDate()));
    }

    private String convertDate(String topStoriesDate) {
        // Build date in dd/MM/yyyy for updatedDate
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date date;
        String newDate = "";

        try {
            date = inputFormat.parse(topStoriesDate);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

}
