package com.official.visualgo.classes;

public class Users {
    String name,url,phnnumber;

    public Users(String name, String phnnumber, String url) {
        this.name = name;
        this.url = url;
        this.phnnumber = phnnumber;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getPhnnumber() {
        return phnnumber;
    }
}
