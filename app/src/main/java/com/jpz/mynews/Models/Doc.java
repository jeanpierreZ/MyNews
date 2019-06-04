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
    private List<Multimedium> multimedia = null;

    @SerializedName("headline")
    @Expose
    private Headline headline;

    @SerializedName("pub_date")
    @Expose
    private String pubDate;

    @SerializedName("section_name")
    @Expose
    private String sectionName;

    @SerializedName("subsection_name")
    @Expose
    private String subsectionName;

    public String getWebUrl() {
        return webUrl;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

}