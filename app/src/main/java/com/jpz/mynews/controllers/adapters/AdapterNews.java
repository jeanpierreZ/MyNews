package com.jpz.mynews.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.jpz.mynews.models.GenericNews;
import com.jpz.mynews.R;
import com.jpz.mynews.views.ViewHolderNews;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<ViewHolderNews> {

    // Declaring callback
    private final Listener callback;

    // For data
    private List<GenericNews> genericNewsList;

    // Declaring a Glide object
    private RequestManager glide;

    public AdapterNews(List<GenericNews> genericNewsList, RequestManager glide, Listener callback) {
        this.genericNewsList = genericNewsList;
        this.glide = glide;
        this.callback = callback;
    }

    // Create interface for callback
    public interface Listener {
        void onClickItem(int position);
    }

    @NonNull
    @Override
    public ViewHolderNews onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create ViewHolderNews and inflating its xml layout
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_item, viewGroup, false);

        return new ViewHolderNews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNews viewHolderNews, int position) {
        viewHolderNews.updateViewHolder(this.genericNewsList.get(position), this.glide, this.callback);
    }

    @Override
    public int getItemCount() {
        return this.genericNewsList.size();
    }

    // Return the position of an item in the list
    public GenericNews getPosition(int position){
        return this.genericNewsList.get(position);
    }

}