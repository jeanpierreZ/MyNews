package com.jpz.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayOfWeek {

    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("missing")
    @Expose
    private Integer missing;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("other")
    @Expose
    private Integer other;
    @SerializedName("terms")
    @Expose
    private List<Term> terms = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMissing() {
        return missing;
    }

    public void setMissing(Integer missing) {
        this.missing = missing;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

}
