package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Response {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;
    @SerializedName("facets")
    @Expose
    private Facets facets;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public Facets getFacets() {
        return facets;
    }

    public void setFacets(Facets facets) {
        this.facets = facets;
    }

}
