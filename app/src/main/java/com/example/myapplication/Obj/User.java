package com.example.myapplication.Obj;

public class User {
    private String uid, username, organization, profile_img, active_project;
    private boolean active, manager;
    /**
     * 0=boss
     * 1=manager
     * 2=worker
     * 3=user on hold
     */
    public User() {}
    public User(String uid, String username, String organization, String profile_img, boolean active, boolean manager, String active_project) {
        this.uid = uid;
        this.username = username;
        this.organization=organization;
        this.profile_img=profile_img;
        this.active=active;
        this.manager=manager;
        this.active_project=active_project;
    }
    public String getUid() {return uid;}
    public void setUid(String uid) {this.uid = uid;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getOrganization() {return organization;}
    public void setOrganization(String organization) {this.organization = organization;}
    public String getProfile_img() {return profile_img;}
    public void setProfile_img(String profile_img) {this.profile_img = profile_img;}
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

    public boolean isManager(){return manager;}
    public void setManager(boolean isManager){ this.manager=isManager;}
    public String getActive_project() {return active_project;}
    public void setActive_project(String active_project) {this.active_project = active_project;}

}
