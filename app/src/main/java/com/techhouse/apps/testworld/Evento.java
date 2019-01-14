package com.techhouse.apps.testworld;

public class Evento {

    String sName, sDate, sDescription;

    public Evento() {
    }

    public Evento(String sName, String sDate, String sDescription) {
        this.sName = sName;
        this.sDate = sDate;
        this.sDescription = sDescription;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }
}
