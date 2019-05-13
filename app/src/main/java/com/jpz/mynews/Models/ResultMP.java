package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResultMP {

    @SerializedName("url")
    @Expose
    private String url;


    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("published_date")
    @Expose
    private String publishedDate;

    @SerializedName("media")
    @Expose
    private List<MediumMP> media = null;
    @SerializedName("uri")
    @Expose
    private String uri;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }


    public List<MediumMP> getMedia() {
        return media;
    }

    public void setMedia(List<MediumMP> media) {
        this.media = media;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
