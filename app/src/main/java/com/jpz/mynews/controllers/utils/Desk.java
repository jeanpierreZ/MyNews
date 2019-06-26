package com.jpz.mynews.controllers.utils;

public enum Desk {
    // Some Filter Query Fields "news_desk" of the API ArticleSearch
    Foreign (Service.API_DESK_FOREIGN),
    Business (Service.API_DESK_BUSINESS),
    Magazine (Service.API_DESK_MAGAZINE),
    Environment (Service.API_DESK_ENVIRONMENT),
    Science (Service.API_DESK_SCIENCE),
    Sports (Service.API_DESK_SPORTS);

    private String name;

    // Constructor
    Desk(String name){
        this.name = name;
    }

    // Method to call the desk in query parameters
    public String toDesk(){
        return name;
    }
}
