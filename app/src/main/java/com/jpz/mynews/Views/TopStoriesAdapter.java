package com.jpz.mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.jpz.mynews.Models.Result;
import com.jpz.mynews.R;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {
    // Link the RecyclerView view to the controller MainFragment

    // Create interface for callback
    public interface Listener {
        void onClickTitle(int position);
    }

    // Declaring callback
    private final Listener callback;

    // For data
    private List<Result> resultList;

    // Declaring a Glide object
    private RequestManager glide;

    // Constructor
    public TopStoriesAdapter(List<Result> resultList, RequestManager glide, Listener callback) {
        this.resultList = resultList;
        this.glide = glide;
        this.callback = callback;
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

    // Update viewHolder with a return of Top Stories
    @Override
    public void onBindViewHolder(@NonNull TopStoriesViewHolder topStoriesViewHolder, int position) {
        topStoriesViewHolder.updateWithTopStories(this.resultList.get(position), this.glide, this.callback);
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return  this.resultList.size();
    }

    // Return the position of an item in the list
    public Result getPosition(int position){
        return this.resultList.get(position);
    }
}
