package com.jpz.mynews.Models;

import java.io.Serializable;

public class SearchQuery implements Serializable {

    // Class used to save data for SearchActivity and NotificationsActivity
    public String queryTerms;
    public String beginDate;
    public String endDate;
    public String[] desks = new String[6];

    // Boolean to verify the state of the notificationSwitch
    public Boolean switchIsChecked;
}