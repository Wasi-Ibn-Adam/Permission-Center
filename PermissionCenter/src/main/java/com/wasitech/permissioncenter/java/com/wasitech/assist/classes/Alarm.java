package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import com.wasitech.basics.classes.Format;

import java.util.Calendar;
import java.util.Comparator;

public class Alarm {
    public static final int ACTIVE = 1;
    private final int h;
    private final int m;
    private int id;
    private String talk;
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alarm(int id, int h, int m, String talk, boolean active) {
        this.h = h;
        this.m = m;
        this.id = id;
        this.talk = talk;
        this.active = active;
    }

    public Alarm(int h, int m, String talk) {
        this.h = h;
        this.m = m;
        if (talk != null)
            this.talk = talk;
        else
            this.talk = " ";
        active = true;
    }

    public Alarm(int h, int m, String talk, boolean active) {
        this.h = h;
        this.m = m;
        if (talk != null)
            this.talk = talk;
        else
            this.talk = " ";
        this.active = active;
    }

    public int getH() {
        return h;
    }

    public int getM() {
        return m;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTalk() {
        return talk;
    }

    public String getDetails() {
        if (talk != null && !talk.trim().equalsIgnoreCase(""))
            return talk;
        else
            return "No Extra Detail.";
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    public Calendar getCalender(Calendar now) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (cal.compareTo(now) <= 0) {
            cal.add(Calendar.DATE, 1);
        }
        return cal;
    }

    public long getTime(Calendar now) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (cal.compareTo(now) <= 0) {
            cal.add(Calendar.DATE, 1);
        }
        return cal.getTimeInMillis();
    }

    public String getTime() {
        String min = Format.Max2(m);
        if (h >= 12) {
            if (h == 12)
                return h + ":" + min + " PM";
            else
                return (h - 12) + ":" + min + " PM";
        } else {
            if (h == 0)
                return 12 + ":" + min + " AM";
            else
                return h + ":" + min + " AM";
        }
    }

    public static Comparator<Alarm> AlarmComparator = (alarm1, alarm2) -> alarm1.getTime().compareTo(alarm2.getTime());

    public String getTimeInNumbers() {
        return getTime().split(" ")[0];
    }

    public String getMeridian() {
        return getTime().split(" ")[1];
    }

}
