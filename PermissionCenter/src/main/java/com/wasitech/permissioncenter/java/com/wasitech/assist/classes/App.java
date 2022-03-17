package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class App {

    private String name;
    private String packageName;

    public App() {
    }

    public App(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return name + " -> " + packageName;
    }

    public static Comparator<App> comparator = (app1, app2) -> app1.getName().toUpperCase().compareTo(app2.getName().toUpperCase());

    public static List<App> removeDuplicate(List<App> list) {
        Map<String, App> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            cleanMap.put(list.get(i).getPackageName(), list.get(i));
        }
        return new ArrayList<>(cleanMap.values());
    }

}
