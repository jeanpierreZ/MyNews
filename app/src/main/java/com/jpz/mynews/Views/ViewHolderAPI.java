package com.jpz.mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import com.jpz.mynews.Models.GenericNews;
import com.jpz.mynews.R;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class ViewHolderAPI extends RecyclerView.ViewHolder implements View.OnClickListener {
    // Represent an item (line) in the RecyclerView

    private TextView textViewTitle;
    private TextView textViewSection;
    private TextView textViewDate;
    private ImageView imageView;

    // Declare a Weak Reference to our Callback
    private WeakReference<AdapterAPI.Listener> callbackWeakRef;

    public ViewHolderAPI(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.fragment_main_item_title);
        textViewSection = itemView.findViewById(R.id.fragment_main_item_section);
        textViewDate = itemView.findViewById(R.id.fragment_main_item_date);
        imageView = itemView.findViewById(R.id.fragment_main_item_image);
    }

    public void updateViewHolder(GenericNews genericNews, RequestManager glide, AdapterAPI.Listener callback){
        // Update widgets
        textViewTitle.setText(genericNews.title);
        textViewSection.setText(convertSectionSubsection(genericNews.section, genericNews.subSection));
        textViewDate.setText(convertDate(genericNews.date));
        glide.load(genericNews.image).into(imageView);

        // Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<>(callback);
        // Implement Listener
        itemView.setOnClickListener(this);
    }

    private String convertSectionSubsection(String section, String subSection) {
        // Display section & subsection of an article
        String sectionSubsection;
        // If subsection is null...
        if (subSection != null)

            // If subsection is empty, don't call it
            if (subSection.isEmpty())
                sectionSubsection = section;
            else
            sectionSubsection = section + " > " + subSection;

        // ...don't call it
        else
            sectionSubsection = section;

        return sectionSubsection;
    }

    private String convertDate(String DateAPI) {
        // Build date in dd/MM/yyyy for PubDate
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        Date date;
        String newDate = "";

        try {
            date = inputFormat.parse(DateAPI);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

    @Override
    public void onClick(View v) {
        // When a click happens, we fire our listener.
        AdapterAPI.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickItem(getAdapterPosition());
    }

}
