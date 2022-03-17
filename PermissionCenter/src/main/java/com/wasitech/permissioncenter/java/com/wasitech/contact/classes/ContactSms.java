package com.wasitech.permissioncenter.java.com.wasitech.contact.classes;

import com.wasitech.assist.classes.Sms;

public class ContactSms extends Sms {
    private String name;

    public ContactSms() {

    }

    public ContactSms(String name, String sender, String receiver, String msg, String time, int who) {
        super(sender, receiver, msg, time, who);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSms(Sms lastSms) {
        if (lastSms == null)
            return;
        sender = lastSms.getSender();
        receiver = lastSms.getReceiver();
        time = lastSms.getTime();
        msg = lastSms.getMsg();
    }
}
