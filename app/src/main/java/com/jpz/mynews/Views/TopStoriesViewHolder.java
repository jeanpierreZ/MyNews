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
    //Represent a line of TopStories in the RecyclerView

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

        // Display title of TopStories in NYTResult
        this.textViewTitle.setText(result.getTitle());
        this.textViewSection.setText(sectionSubsection);
        this.textViewUpdatedDate.setText(result.getUpdatedDate());
    }

/*
    private String parseDate(String topStoriesDate) {
        // Build date in dd/MM/yyyy for updatedDate

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-HH:mm", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        Date date;
        String newDate = null;

        try {
            date = inputFormat.parse(topStoriesDate);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }
*/

}
