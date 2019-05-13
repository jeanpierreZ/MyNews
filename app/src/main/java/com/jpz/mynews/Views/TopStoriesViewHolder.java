package com.jpz.mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.jpz.mynews.Models.ModelAPI;
import com.jpz.mynews.Models.ResultAPI;
import com.jpz.mynews.Models.ResultTP;
import com.jpz.mynews.R;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class TopStoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // Represent an item (line) of TopStories in the RecyclerView

    private TextView textViewTitle;
    private TextView textViewSection;
    private TextView textViewUpdatedDate;
    private ImageView imageView;

    // Declare a Weak Reference to our Callback
    private WeakReference<TopStoriesAdapter.Listener> callbackWeakRef;

    // Constructor
    public TopStoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.fragment_main_item_title);
        textViewSection = itemView.findViewById(R.id.fragment_main_item_section);
        textViewUpdatedDate = itemView.findViewById(R.id.fragment_main_item_date);
        imageView = itemView.findViewById(R.id.fragment_main_item_image);
    }

    public void updateWithTopStories(ResultAPI resultAPI, RequestManager glide, TopStoriesAdapter.Listener callback){
        // Build string for section and subsection
        String sectionSubsection;
        String section = resultAPI.getSection();
        String subSection = resultAPI.getSubsection();
        // If subsection is empty, don't call it
        if (subSection.equals(""))
            sectionSubsection = section;
        else
            sectionSubsection = section + " > " + subSection;

        // Display settings of TopStories in ResultTP
        textViewTitle.setText(resultAPI.getTitle());
        textViewSection.setText(sectionSubsection);
        textViewUpdatedDate.setText(convertDate(resultAPI.getPublishedDate()));

        // If Multimedium is empty don't display the photo
        if ( resultAPI.getMultimedia().size() != 0)
        glide.load(resultAPI.getMultimedia().get(0).getUrl()).into(imageView);

        // Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<>(callback);
        // Implement Listener
        textViewTitle.setOnClickListener(this);
    }

    private String convertDate(String topStoriesDate) {
        // Build date in dd/MM/yyyy for updatedDate
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        Date date;
        String newDate = "";

        try {
            date = inputFormat.parse(topStoriesDate);
            newDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }
        return newDate;
    }

    @Override
    public void onClick(View v) {
        // When a click happens, we fire our listener.
        TopStoriesAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickTitle(getAdapterPosition());
    }
}
