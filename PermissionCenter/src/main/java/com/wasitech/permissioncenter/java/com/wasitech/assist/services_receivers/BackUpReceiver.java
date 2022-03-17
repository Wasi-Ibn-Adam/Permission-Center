package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.assist.command.family.Intents;

import java.util.Calendar;

public class BackUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new OldNotificationMaker(context).Notify("Back up","Just Happened: "+ Calendar.getInstance().getTime().toString(), Intents.MainActivity(context));
        Basics.AudioVideo.Vibrator(context,3500L);
    }
}
