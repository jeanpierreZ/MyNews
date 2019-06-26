package com.jpz.mynews.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import com.jpz.mynews.controllers.adapters.AdapterNews;
import com.jpz.mynews.controllers.utils.ConvertMethods;
import com.jpz.mynews.models.GenericNews;
import com.jpz.mynews.R;

import java.lang.ref.WeakReference;

public class ViewHolderNews extends RecyclerView.ViewHolder implements View.OnClickListener {
    // Represent an item (line) in the RecyclerView

    private TextView textViewTitle;
    private TextView textViewSection;
    private TextView textViewDate;
    private ImageView imageView;

    private ConvertMethods convertMethods = new ConvertMethods();

    // Declare a Weak Reference to our Callback
    private WeakReference<AdapterNews.Listener> callbackWeakRef;

    public ViewHolderNews(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.fragment_item_title);
        textViewSection = itemView.findViewById(R.id.fragment_item_section);
        textViewDate = itemView.findViewById(R.id.fragment_item_date);
        imageView = itemView.findViewById(R.id.fragment_item_image);
    }

    public void updateViewHolder(GenericNews genericNews, RequestManager glide, AdapterNews.Listener callback){
        // Update widgets
        textViewTitle.setText(genericNews.title);
        textViewSection.setText(convertMethods.convertSectionSubsection
                (genericNews.section, genericNews.subSection));
        textViewDate.setText(convertMethods.convertDate(genericNews.date));
        glide.load(genericNews.image).into(imageView);

        // Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<>(callback);
        // Implement Listener
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // When a click happens, we fire our listener to get the item position in the list
        AdapterNews.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickItem(getAdapterPosition());
    }

}