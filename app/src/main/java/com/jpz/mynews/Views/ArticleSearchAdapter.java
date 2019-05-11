package com.jpz.mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.R;

import java.util.List;

public class ArticleSearchAdapter extends RecyclerView.Adapter<ArticleSearchViewHolder> {
    // Link the RecyclerView view to the controller MainFragment

    // For data
    private List<Doc> docs;

    // Declaring a Glide object
    private RequestManager glide;

    // Constructor
    public ArticleSearchAdapter(List<Doc> docs, RequestManager glide) {
        this.docs = docs;
        this.glide = glide;
    }

    @NonNull
    @Override
    public ArticleSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create ViewHolder and inflating its xml layout
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, viewGroup, false);

        return new ArticleSearchViewHolder(view);
    }

    // Update viewHolder with a title of TopStories
    @Override
    public void onBindViewHolder(@NonNull ArticleSearchViewHolder articleSearchViewHolder, int position) {
        articleSearchViewHolder.updateWithArticleSearch(this.docs.get(position), this.glide);
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return  this.docs.size();
    }


}
