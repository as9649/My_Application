package com.example.myapplication.Obj;

import java.util.ArrayList;

public class Organization {
    private String keyId, orgName, logoUrl;
    private ArrayList<User> workers, managers;
    private static ArrayList<String> organizations=new ArrayList<String>();



    public Organization(String keyId, String orgName, String logoUrl, ArrayList<User> workers, ArrayList<User> managers) {
        this.keyId = keyId;
        this.orgName=orgName;
        this.logoUrl=logoUrl;
        this.workers=workers;
        this.managers=managers;
        if (organizations.isEmpty())
            organizations.add("organization:");
        Organization.organizations.add(this.orgName);
    }

    public static ArrayList<String> getOrganizations() {
        return organizations;
    }

    public static void setOrganizations(ArrayList<String> organizations) {
        Organization.organizations = organizations;
    }


    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public ArrayList<User> getWorkers() {
        return workers;
    }

//    public void setWorkers(ArrayList<String> workers) {
//        this.workers = workers;
//    }

    public ArrayList<User> getManagers() {
        return managers;
    }

//    public void setManagers(ArrayList<String> managers) {
//        this.managers = managers;
//    }
}
