package com.jpz.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {

    @SerializedName("web_url")
    @Expose
    private String webUrl;

    @SerializedName("multimedia")
    @Expose
    private List<MultimediumAS> multimedia = null;

    @SerializedName("headline")
    @Expose
    private Headline headline;

    @SerializedName("pub_date")
    @Expose
    private String pubDate;

    @SerializedName("news_desk")
    @Expose
    private String newsDesk;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    @SerializedName("uri")
    @Expose
    private String uri;

    public String getWebUrl() {
        return webUrl;
    }

    public List<MultimediumAS> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediumAS> multimedia) {
        this.multimedia = multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getUri() {
        return uri;
    }

}