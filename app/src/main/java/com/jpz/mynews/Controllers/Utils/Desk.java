package com.jpz.mynews.Controllers.Utils;

public enum Desk {
    // The desks of API
    Environment (Service.API_DESK_ENVIRONMENT),
    Business (Service.API_DESK_BUSINESS),
    Foreign (Service.API_DESK_FOREIGN),
    Science (Service.API_DESK_SCIENCE),
    Sports (Service.API_DESK_SPORTS),
    T_Magazine (Service.API_DESK_T_MAGAZINE);

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
