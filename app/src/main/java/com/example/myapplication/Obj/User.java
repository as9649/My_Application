package com.example.myapplication.Obj;

public class User {
    private String uid, username, organization;
    private boolean active, manager;
    public User() {}
    public User(String uid, String username, String organization, boolean active, boolean manager) {
        this.uid = uid;
        this.username = username;
        this.organization=organization;
        this.active=active;
        this.manager=manager;
    }
    public String getUid() {return uid;}
    public void setUid(String uid) {this.uid = uid;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getOrganization() {return organization;}
    public void setOrganization(String organization) {this.organization = organization;}
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

    public boolean isManager(){return manager;}
    public void setManager(boolean isManager){ this.manager=isManager;}

}
