package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopular {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("published_date")
    @Expose
    private String publishedDate;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("media")
    @Expose
    private List<MostPopularMedia> mostPopularMediaList = null;


    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getUrl() {
        return url;
    }

    public List<MostPopularMedia> getMostPopularMediaList() {
        return mostPopularMediaList;
    }

}
