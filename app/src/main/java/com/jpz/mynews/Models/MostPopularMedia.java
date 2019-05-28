package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopularMedia {

    @SerializedName("media-metadata")
    @Expose
    private List<MostPopularMediaMetada> mostPopularMediaMetadaList = null;

    public List<MostPopularMediaMetada> getMostPopularMediaMetadaList() {
        return mostPopularMediaMetadaList;
    }

}
