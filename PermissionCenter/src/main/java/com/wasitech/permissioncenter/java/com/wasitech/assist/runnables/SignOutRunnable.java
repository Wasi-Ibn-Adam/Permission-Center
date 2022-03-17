package com.wasitech.permissioncenter.java.com.wasitech.assist.runnables;

import android.content.Context;
import android.content.Intent;

import com.facebook.login.LoginManager;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.services_receivers.AudioRecordingHeadService;
import com.wasitech.assist.services_receivers.BackgroundService;
import com.wasitech.assist.services_receivers.CameraHeadService;
import com.wasitech.assist.services_receivers.CommandHeadService;
import com.wasitech.assist.services_receivers.PhoneFinderService;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;
import com.wasitech.register.activity.Profile;

public class SignOutRunnable implements Runnable {
    private final Context context;
    private final LocalDB db;

    public SignOutRunnable(Context context) {
        this.context = context;
        db = new LocalDB(context);
    }

    @Override
    public void run() {
        firebaseUpdate();
        ProcessApp.signOut();
        context.stopService(Intents.AlwaysOn(context));
        try {
            LoginManager.getInstance().logOut();
        } catch (Exception e) {
            Issue.print(e,SignOutRunnable.class.getName());
        }
        deleteDB();

        prefsUpdate(Params.PASSWORD_ERROR_PIC);
        prefsUpdate(Params.PHONE_FINDER);

        context.stopService(new Intent(context, CommandHeadService.class));
        context.stopService(new Intent(context, CameraHeadService.class));
        context.stopService(new Intent(context, AudioRecordingHeadService.class));
        context.stopService(new Intent(context, BackgroundService.class));
        context.stopService(new Intent(context, PhoneFinderService.class));
        Profile.User.removeUser();
        onComplete();
    }

    public void onComplete() { }

    private void deleteDB(){
        db.safeDelete();
    }
    private void firebaseUpdate(){
        new CloudDB.ProfileCenter(context).uploadProfile();
    }
    private void prefsUpdate(String key){
        ProcessApp.getPref().edit().putBoolean(key, false).apply();
    }
}