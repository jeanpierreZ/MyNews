package com.jpz.mynews.Controllers.Utils;

public enum Desk {
    // The desks of API
    Environment (Service.API_DESK_ENVIRONMENT),
    Financial (Service.API_DESK_FINANCIAL),
    Foreign (Service.API_DESK_FOREIGN),
    Science (Service.API_DESK_SCIENCE),
    Sports (Service.API_DESK_SPORTS),
    Technology (Service.API_DESK_TECHNOLOGY);

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
