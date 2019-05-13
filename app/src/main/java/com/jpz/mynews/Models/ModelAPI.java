package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAPI {

    @SerializedName("status")
    @Expose
    private String status;

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

    @SerializedName("uri")
    @Expose
    private String uri;

    @SerializedName("web_url")
    @Expose
    private String webUrl;

    @SerializedName("pub_date")
    @Expose
    private String pubDate;

    @SerializedName("news_desk")
    @Expose
    private String newsDesk;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    @SerializedName("main")
    @Expose
    private String main;


    public String getStatus() {
        return status;
    }

    public String getMain() {
        return main;
    }

    public String getWebUrl() {
        return webUrl;
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

    public String getUri() {
        return uri;
    }


}
