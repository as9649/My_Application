package com.example.myapplication.Obj;

public class Presence {
    private String date;
    //private User user;
    private double latitude, longitude;

    public Presence(String date, double latitude, double longitude){
        this.date=date;
        //this.user=user;
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
