package com.example.myapplication.Obj;

public class Presence {
    private String date, username, uid;
    //private User user;
    private double latitude, longitude;

    public Presence(String date, double latitude, double longitude){
        this.date=date;
        //this.user=user;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Presence() {
    }

    public Presence(String date, String username, String uid, double latitude, double longitude) {
        this.date = date;
        this.username = username;
        this.uid = uid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public String getUid() {
        return uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
