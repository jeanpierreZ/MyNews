package com.jpz.mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import com.jpz.mynews.Controllers.Utils.GetData;
import com.jpz.mynews.Models.API;
import com.jpz.mynews.Models.ListAPI;
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

    public void updateViewHolder(ListAPI listAPI, RequestManager glide, AdapterAPI.Listener callback){
        // Update widgets
        textViewTitle.setText(listAPI.title());
        textViewSection.setText(listAPI.sectionSubsection());
        textViewDate.setText(convertDate(listAPI.date()));
        glide.load(listAPI.image()).into(imageView);

        // Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<>(callback);
        // Implement Listener
        itemView.setOnClickListener(this);
    }

    public String convertDate(String DateAPI) {
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
