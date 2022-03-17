package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

public class Tingno {
    private String uid,text, sender, time;
    public static final String TING="Ting";
    public static final String YOU="You";
    public static final String DEVELOPER="Developer";
    public Tingno(String uid,String text, String sender, String time) {
        this.text = text;
        this.sender = sender;
        this.time = time;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Tingno{" +
                "text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
