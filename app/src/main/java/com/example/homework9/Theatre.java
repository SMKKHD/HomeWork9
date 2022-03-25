package com.example.homework9;

import java.util.ArrayList;

public class Theatre {
    private String id;
    private String place;



    public Theatre(){

    }

    void setTheatreID(String id){
        //Integer.parseInt(id);
        this.id = id;
    }
    void setPlace(String place){
        this.place = place;
    }

    public String getPlace(){
        return this.place;
    }

    public String getID(){
        return this.id;
    }


}
