package com.jpz.mynews.Models;

import java.io.Serializable;

public class SearchQuery implements Serializable {

    // Class used for SearchActivity
    public String queryTerms;
    public String beginDate;
    public String endDate;

    public String[] desks = {null, null, null, null, null, null};
}
