package com.wasitech.permissioncenter.java.com.wasitech.assist.jokes;

import android.content.Context;

import com.wasitech.basics.app.ProcessApp;

public class Joke {
    private final boolean twoPart;
    private final String p1, p2;

    public Joke(String p1) {
        twoPart = false;
        this.p1 = p1;
        this.p2 = null;
    }

    public Joke(String p1, String p2) {
        twoPart = true;
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean isTwoPart() {
        return twoPart;
    }

    public String getP1() {
        return p1;
    }

    public String getP2() {
        return p2;
    }

    public void talk(final Context context) {
        if (twoPart){
            ProcessApp.talkWithDelay(context,p1,700L,p2);
        }
        else
            ProcessApp.talk(context, p1);
    }

}
