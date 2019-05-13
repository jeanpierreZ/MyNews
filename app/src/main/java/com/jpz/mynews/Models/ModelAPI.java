package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAPI {

    @SerializedName("results")
    @Expose
    private List<ResultAPI> resultAPIList = null;

    public List<ResultAPI> getResultAPIList() {
        return resultAPIList;
    }



}
