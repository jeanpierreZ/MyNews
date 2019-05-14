package com.jpz.mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import com.jpz.mynews.Controllers.Utils.GetData;
import com.jpz.mynews.R;

import java.lang.ref.WeakReference;

public class ViewHolderAPI extends RecyclerView.ViewHolder implements View.OnClickListener {
    // Represent an item (line) in the RecyclerView

    private TextView textViewTitle;
    private TextView textViewSection;
    private TextView textViewUpdatedDate;
    private ImageView imageView;

    // Declare a Weak Reference to our Callback
    private WeakReference<AdapterAPI.Listener> callbackWeakRef;

    public ViewHolderAPI(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.fragment_main_item_title);
        textViewSection = itemView.findViewById(R.id.fragment_main_item_section);
        textViewUpdatedDate = itemView.findViewById(R.id.fragment_main_item_date);
        imageView = itemView.findViewById(R.id.fragment_main_item_image);
    }

    public void updateViewHolder(GetData getData, RequestManager glide, AdapterAPI.Listener callback){
        // Update widgets
        textViewTitle.setText(getData.title());
        textViewSection.setText(getData.sectionSubsection());
        textViewUpdatedDate.setText(getData.convertDate(getData.date()));
        glide.load(getData.image()).into(imageView);

        // Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<>(callback);
        // Implement Listener
        textViewTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // When a click happens, we fire our listener.
        AdapterAPI.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickTitle(getAdapterPosition());
    }

}
