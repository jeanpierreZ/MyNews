package com.jpz.mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.jpz.mynews.Controllers.Utils.GetData;
import com.jpz.mynews.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAPI extends RecyclerView.Adapter<ViewHolderAPI> {

    // Declaring callback
    private final Listener callback;

    // For data
    private List<GetData> getDataList;

    // Declaring a Glide object
    private RequestManager glide;


    public AdapterAPI(List<GetData> getDataList, RequestManager glide, Listener callback) {
        this.getDataList = getDataList;
        this.glide = glide;
        this.callback = callback;
    }

    // Create interface for callback
    public interface Listener {
        void onClickTitle(int position);
    }

    @NonNull
    @Override
    public ViewHolderAPI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create ViewHolderAPI and inflating its xml layout
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, viewGroup, false);

        return new ViewHolderAPI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAPI viewHolderAPI, int position) {
        viewHolderAPI.updateViewHolder(this.getDataList.get(position), this.glide, this.callback);
    }

    @Override
    public int getItemCount() {
        return this.getDataList.size();
    }

    // Return the position of an item in the list
    public GetData getPosition(int position){
        return this.getDataList.get(position);
    }

}
