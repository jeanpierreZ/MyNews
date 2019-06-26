package com.jpz.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStoriesResponse {

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    @SerializedName("results")
    @Expose
    private List<Result> resultList = null;

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList (List<Result> resultList) {
        this.resultList = resultList;
    }

}