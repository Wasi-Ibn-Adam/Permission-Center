package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

public class ServiceHead {
    private String name;
    private int res;

    public ServiceHead(String name, int res) {
        this.name = name;
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
