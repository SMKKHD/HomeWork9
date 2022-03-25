package com.example.homework9;

import java.util.ArrayList;

public class TheaterList {
    //private Theatre theatre;
    private String date;
    private ArrayList <Theatre> TList;
    private static TheaterList Singleton = null;


    public TheaterList(){
        TList = new ArrayList<Theatre>();

    }
    public static TheaterList getInstance(){
        if(Singleton == null){
            Singleton = new TheaterList();
        }
        return Singleton;
    }

    public void addList(Theatre theatre){
    this.TList.add(theatre);
    }

    public ArrayList<Theatre> getTList() {
        return TList;
    }

    public String loopForList(String movie){
        String id = "";
        for (int i = 0; i < TList.size(); i++){

            if(TList.get(i).getPlace().equals(movie) == true){
                id = TList.get(i).getID();
            }

        }
        return id;
    }
}
