package com.example.homework9;

import java.util.ArrayList;

public class MovieList {
    private ArrayList <String> Movies;
    private static MovieList Singleton = null;
    public MovieList(){
        Movies = new ArrayList<>();
    }

    public static MovieList getSingleton(){
        if(Singleton == null){
            Singleton = new MovieList();
        }
        return Singleton;
    }

    public ArrayList<String> getMovies(){
        return Movies;
    }

    public void setMovies(String movies){
        Movies.add(movies);
    }

    public void clearList(){
        Movies.clear();
    }

}
