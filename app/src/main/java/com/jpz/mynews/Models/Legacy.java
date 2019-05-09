package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Legacy {

    @SerializedName("hasthumbnail")
    @Expose
    private String hasthumbnail;
    @SerializedName("thumbnailheight")
    @Expose
    private Integer thumbnailheight;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("xlargewidth")
    @Expose
    private Integer xlargewidth;
    @SerializedName("xlargeheight")
    @Expose
    private Integer xlargeheight;
    @SerializedName("xlarge")
    @Expose
    private String xlarge;
    @SerializedName("hasxlarge")
    @Expose
    private String hasxlarge;

    public String getHasthumbnail() {
        return hasthumbnail;
    }

    public void setHasthumbnail(String hasthumbnail) {
        this.hasthumbnail = hasthumbnail;
    }

    public Integer getThumbnailheight() {
        return thumbnailheight;
    }

    public void setThumbnailheight(Integer thumbnailheight) {
        this.thumbnailheight = thumbnailheight;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getXlargewidth() {
        return xlargewidth;
    }

    public void setXlargewidth(Integer xlargewidth) {
        this.xlargewidth = xlargewidth;
    }

    public Integer getXlargeheight() {
        return xlargeheight;
    }

    public void setXlargeheight(Integer xlargeheight) {
        this.xlargeheight = xlargeheight;
    }

    public String getXlarge() {
        return xlarge;
    }

    public void setXlarge(String xlarge) {
        this.xlarge = xlarge;
    }

    public String getHasxlarge() {
        return hasxlarge;
    }

    public void setHasxlarge(String hasxlarge) {
        this.hasxlarge = hasxlarge;
    }

}
