package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStories {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("subsection")
    @Expose
    private String subsection;

    @SerializedName("published_date")
    @Expose
    private String publishedDate;

    @SerializedName("short_url")
    @Expose
    private String shortUrl;

    @SerializedName("multimedia")
    @Expose
    private List<TopStoriesMultimedia> topStoriesMultimediaList = null;

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public List<TopStoriesMultimedia> getTopStoriesMultimediaList() {
        return topStoriesMultimediaList;
    }
}
