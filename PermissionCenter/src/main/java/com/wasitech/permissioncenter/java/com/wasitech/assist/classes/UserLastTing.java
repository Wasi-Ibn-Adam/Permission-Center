package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import com.wasitech.ting.Chat.Module.Ting;
import com.wasitech.assist.command.family.QA;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class UserLastTing extends TingUser {
    private Ting ting;
    public UserLastTing(TingUser user, Ting ting) {
        super(user);
        this.ting = ting;
    }

    public Ting getTing() {
        return ting;
    }

    public void setTing(Ting ting) {
        this.ting = ting;
    }

    public String getTime(){
        return ting.getTinggo().getTime();
    }
    public String getText(){
        return ting.getTinggo().getText();
    }

    public String getTimeText(){
        Date d=QA.getTingDateBack(getTime());
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        int n=c.get(Calendar.DAY_OF_MONTH);
        int a=QA.dayN()-n;
        if(a==0){
            return Tinggo.onlyTime(getTime());
        }
        if(a==1){
            return "Yesterday";
        }
        else
            return Tinggo.onlyDate(getTime());
    }
    public static Comparator<UserLastTing> Comparator = (t2, t1) -> QA.getTingDateBack(t1.getTime()).compareTo(QA.getTingDateBack(t2.getTime()));

}
