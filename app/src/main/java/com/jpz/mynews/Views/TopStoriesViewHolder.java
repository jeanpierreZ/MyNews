package com.jpz.mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jpz.mynews.Models.NYTResult;
import com.jpz.mynews.R;

public class TopStoriesViewHolder extends RecyclerView.ViewHolder {
    //Represent a line of TopStories in the RecyclerView

    private TextView textViewTitle;

    // Constructor
    public TopStoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.fragment_main_item_title);
    }

    public void updateWithTopStories(NYTResult result){
        // Display title of TopStories in NYTResult
        this.textViewTitle.setText(result.getTitle());
    }
}
