package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

public class Sms {
    protected String sender,receiver,msg,time;
    protected int who;
    public static final int SENDER=0;
    public static final int RECEIVER=1;

    public Sms(){}
    public Sms(String sender, String receiver, String msg, String time,int who) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.time = time;
        this.who=who;

    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
