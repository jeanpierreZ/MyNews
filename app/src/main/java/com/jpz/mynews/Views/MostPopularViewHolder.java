package com.jpz.mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.jpz.mynews.Models.ResultMP;
import com.jpz.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class MostPopularViewHolder extends RecyclerView.ViewHolder {
    // Represent an item (line) of MostPopular in the RecyclerView

    private TextView textViewTitle;
    private TextView textViewSection;
    private TextView textViewUpdatedDate;
    private ImageView imageView;

    // Constructor
    public MostPopularViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.fragment_main_item_title);
        textViewSection = itemView.findViewById(R.id.fragment_main_item_section);
        textViewUpdatedDate = itemView.findViewById(R.id.fragment_main_item_date);
        imageView = itemView.findViewById(R.id.fragment_main_item_image);
    }

    public void updateWithMostPopular(ResultMP resultMP, RequestManager glide){
        // Display settings of MostPopular in ResultMP
        textViewTitle.setText(resultMP.getTitle());
        textViewSection.setText(resultMP.getSection());
        textViewUpdatedDate.setText(convertDate(resultMP.getPublishedDate()));

        // If MediaMetadatumMP is empty don't display the photo
        if ( resultMP.getMedia().get(0).getMediaMetadata().size() != 0)
            glide.load(resultMP.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(imageView);
    }

    private String convertDate(String topStoriesDate) {
        // Build date in dd/MM/yyyy for updatedDate
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

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
