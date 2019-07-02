package com.jpz.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("hits")
    @Expose
    private Integer hits;

    public Integer getHits() {
        return hits;
    }
}