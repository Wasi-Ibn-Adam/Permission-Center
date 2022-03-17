package com.wasitech.permissioncenter.java.com.wasitech.basics.classes;

import android.os.Build;

import com.android.volley.VolleyError;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.database.CloudDB;

import java.util.Arrays;
import java.util.Date;

public class Issue {
    private static final String path = Build.MANUFACTURER + "/" + Build.MODEL + "/" + ProcessApp.getUid();
    private static String getPath(String path) {
        return path.replace("::", "/");
    }
    private static void set(String from, String error) {
        try {
            CloudDB.Issues.USER_ISSUES.child(path)
                    .child(getPath(from))
                    .child(new Date().toString())
                    .setValue(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void set(Exception e,String c) {
        print(e,c);
        try {
            String msg = e.getMessage();
            StackTraceElement el = e.getStackTrace()[0];
            if (el != null) {
                String from =c+"/"+ el.getFileName() + "/" + el.getMethodName() + "/" + el.getLineNumber();
                set(from.replace(".","")
                        .replace("#","")
                        .replace("$","")
                        .replace("[","")
                        .replace("]",""), msg);
            }
        } catch (Exception e1) {
            print(e1,Issue.class.getName());
        }
    }
    public static void print(Exception e,String c){
        e.printStackTrace();
    }
    public static void Internet(VolleyError volleyError) {
        Basics.Log("volley error");
        volleyError.printStackTrace();
    }

    public static void Log(Exception e,String function){
        Basics.Log("<<---------------------------------------------------->>");
        Basics.Log("BASIC-FUN-ERROR->> "+function);
        Basics.Log(Arrays.toString(e.getStackTrace()));
        Basics.Log("<<--!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!->>");
    }

}
