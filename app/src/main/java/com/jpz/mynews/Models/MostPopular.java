package com.jpz.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopular {


    @SerializedName("results")
    @Expose
    private List<ResultMP> results = null;


    public List<ResultMP> getResults() {
        return results;
    }

    public void setResults(List<ResultMP> results) {
        this.results = results;
    }
}