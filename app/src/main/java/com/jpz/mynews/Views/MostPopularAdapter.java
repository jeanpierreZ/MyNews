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

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularViewHolder> {
    // Link the RecyclerView view to the controller MainFragment

    // For data
    private List<Result> resultList;

    // Declaring a Glide object
    private RequestManager glide;

    // Constructor
    public MostPopularAdapter(List<Result> resultList, RequestManager glide) {
        this.resultList = resultList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create ViewHolder and inflating its xml layout
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, viewGroup, false);

        return new MostPopularViewHolder(view);
    }

    // Update viewHolder with a return of Most Popular
    @Override
    public void onBindViewHolder(@NonNull MostPopularViewHolder mostPopularViewHolder, int position) {
        mostPopularViewHolder.updateWithMostPopular(this.resultList.get(position), this.glide);
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return  this.resultList.size();
    }

}
