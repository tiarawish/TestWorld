package com.techhouse.apps.testworld;

public class User {
    String sName;
    String sEmail;

    public User() {

    }

    public User(String name, String email) {
        sEmail = email;
        sName = name;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }
}
