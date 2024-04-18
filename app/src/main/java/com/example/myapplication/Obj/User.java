package com.example.myapplication.Obj;

public class User {
    private String uid, username, organization, profile_img, active_project;
    private int position;
    private boolean active;
    /**
     * 0=boss
     * 1=manager
     * 2=worker
     * 3=user on hold
     */
    public User() {}
    public User(String uid, String username, String organization, int position, String profile_img, boolean active, String active_project) {
        this.uid = uid;
        this.username = username;
        this.organization=organization;
        this.position=position;
        this.profile_img=profile_img;
        this.active=active;
        this.active_project=active_project;
    }
    public String getUid() {return uid;}
    public void setUid(String uid) {this.uid = uid;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getOrganization() {return organization;}
    public void setOrganization(String organization) {this.organization = organization;}
    public int getPosition() {return position;}
    public void setPosition(int position) {this.position = position;}
    public String getProfile_img() {return profile_img;}
    public void setProfile_img(String profile_img) {this.profile_img = profile_img;}
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
    public String getActive_project() {return active_project;}
    public void setActive_project(String active_project) {this.active_project = active_project;}

}
