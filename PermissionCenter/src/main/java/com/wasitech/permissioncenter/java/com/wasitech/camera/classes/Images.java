package com.wasitech.permissioncenter.java.com.wasitech.camera.classes;

import com.wasitech.basics.classes.Format;

import java.io.File;

public class Images {
    private String name, path;
    private long time;

    public Images() {
    }

    public Images(String path) {
        this.path = path;
        time = new File(path).lastModified();
        name = new File(path).getName();
    }

    public Images(String name, String path) {
        this.name = name;
        this.path = path;
        time = new File(path).lastModified();
    }

    public Images(String name, String path, long time) {
        this.name = name;
        this.path = path;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public String getDate() {
        return Format.longToTime(time);
    }

    public void setTime(long time) {
        this.time = time;
    }


}
