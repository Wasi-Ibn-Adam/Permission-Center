package com.wasitech.permissioncenter.java.com.wasitech.ting.Noti;

import com.wasitech.assist.classes.TingUser;
import com.wasitech.assist.classes.Tingno;

public class UserWithTingNo extends TingUser {
    private Tingno tingno;

    public UserWithTingNo(TingUser user, Tingno tingno) {
        super(user);
        this.tingno = tingno;
    }

    public Tingno getTingno() {
        return tingno;
    }

    public void setTingno(Tingno tingno) {
        this.tingno = tingno;
    }
}
