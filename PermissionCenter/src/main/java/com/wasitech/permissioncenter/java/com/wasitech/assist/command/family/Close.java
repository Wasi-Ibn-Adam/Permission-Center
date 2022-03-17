package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.wasitech.assist.actions.Open;

import com.wasitech.basics.classes.Issue;

public class Close extends ActivityCompat {
    String txtBack;
    public Close(Context context, String str) {

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(homeIntent);


        ActivityManager am = (ActivityManager)context.getSystemService(Activity.ACTIVITY_SERVICE);
        String packName=new Open(context).packageNme(str);
        if(packName!=null&&!packName.isEmpty()) {
            String name="";
            try {
                name=(String) context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(packName,PackageManager.GET_META_DATA));
                am.killBackgroundProcesses(packName);
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Close.class.getName());
            }
            txtBack="Closing "+name;
        }
        else{
            txtBack="App Not Found";
        }
    }
    public String closeApp() { return txtBack; }
}
