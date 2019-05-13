package com.jpz.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStories {

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("results")
    @Expose
    private List<ResultTP> results = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultTP> getResults() {
        return results;
    }

    public void setResults(List<ResultTP> results) {
        this.results = results;
    }
}
