package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("subsection")
    @Expose
    private String subsection;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("published_date")
    @Expose
    private String publishedDate;

    @SerializedName("short_url")
    @Expose
    private String shortUrl;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("multimedia")
    @Expose
    private List<Multimedium> multimedia = null;

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    @SerializedName("media")
    @Expose
    private List<Medium> media = null;


    public List<Medium> getMedia() {
        return media;
    }

    public String getSubsection() {
        return subsection;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getSection() {
        return section;
    }

}
