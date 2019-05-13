package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MediumMP {


    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadatumMP> mediaMetadata = null;


    public List<MediaMetadatumMP> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(List<MediaMetadatumMP> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }

}
