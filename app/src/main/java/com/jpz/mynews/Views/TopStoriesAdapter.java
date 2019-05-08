package com.jpz.mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.jpz.mynews.Models.NYTResult;
import com.jpz.mynews.R;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {
    // Link the RecyclerView view to the controller MainFragment

    // For data
    private List<NYTResult> results;

    // Declaring a Glide object
    private RequestManager glide;

    // Constructor
    public TopStoriesAdapter(List<NYTResult> results, RequestManager glide) {
        this.results = results;
        this.glide = glide;
    }

    @NonNull
    @Override
    public TopStoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create ViewHolder and inflating its xml layout
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, viewGroup, false);

        return new TopStoriesViewHolder(view);
    }

    // Update viewholder with a title of TopStories
    @Override
    public void onBindViewHolder(@NonNull TopStoriesViewHolder topStoriesViewHolder, int position) {
        topStoriesViewHolder.updateWithTopStories(this.results.get(position), this.glide);
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return  this.results.size();
    }
}
