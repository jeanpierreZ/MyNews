package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAPI {

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    @SerializedName("results")
    @Expose
    private List<ResultAPI> resultAPIList = null;

    public List<ResultAPI> getResultAPIList() {
        return resultAPIList;
    }

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

}
