package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import androidx.annotation.NonNull;

public class Tinggo {
    public static final int ME = 1;
    public static final int OTHER = 0;

    private int who;
    private String uid, text, time;

    public Tinggo() {
    }

    public Tinggo(int who, String uid, String text, String time) {
        this.who = who;
        this.uid = uid;
        this.text = text;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String title) {
        this.uid = title;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public int getWho() {
        return who;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static String onlyTime(String s) {
        return OnlyTime(s);
    }

    private static String OnlyTime(String s) {
        if (s == null)
            return null;
        if (s.contains(" ")) {
            String[] ss = s.split(" ");
            String l = ss[ss.length - 1];
            if (l.contains(":")) {
                return l.substring(0, l.lastIndexOf(":"));
            } else
                return l;
        }
        return s.trim();
    }

    public static String onlyDate(String s) {
        if (s == null)
            return null;
        if (s.contains(" ")) {
            int n = s.trim().lastIndexOf(" ");
            if (n != -1)
                return s.substring(0, n);
        }
        return s.trim();
    }

    @Override
    @NonNull
    public String toString() {
        return who + "::" + uid + "::" + text + "::" + time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
