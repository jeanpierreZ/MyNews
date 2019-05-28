package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopularResponse {

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    @SerializedName("results")
    @Expose
    private List<MostPopular> mostPopularList = null;

    public List<MostPopular> getMostPopularList() {
        return mostPopularList;
    }

}
