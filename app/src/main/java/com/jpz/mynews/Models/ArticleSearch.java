package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearch {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("response")
    @Expose
    private Response response;

    public String getStatus() {
        return status;
    }

    public Response getResponse() {
        return response;
    }

}