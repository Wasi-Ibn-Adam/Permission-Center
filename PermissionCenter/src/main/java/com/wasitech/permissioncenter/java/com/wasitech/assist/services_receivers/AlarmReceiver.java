package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.Alarm;
import com.wasitech.basics.notify.OldNotificationMaker;

import com.wasitech.database.LocalDB;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("action")) {
            new OldNotificationMaker(context.getApplicationContext()).Manager().cancel(4);
            if (OldNotificationMaker.tone != null) {
                OldNotificationMaker.tone.stop();
                OldNotificationMaker.tone = null;
            }
        } else {
            String time = intent.getStringExtra("time");
            String talk = intent.getStringExtra("talk");
            int h = intent.getIntExtra("h", 0);
            int m = intent.getIntExtra("m", 0);
            LocalDB db = new LocalDB(context.getApplicationContext());
            Alarm alarm = db.getAlarm(h, m, talk);
            if (alarm != null && alarm.isActive()) {
                Basics.AudioVideo.Vibrator(context.getApplicationContext(), 1000L);
                if (talk != null){
                    ProcessApp.talk(context.getApplicationContext(), talk);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> new OldNotificationMaker(context).Alarm("Alarm", "Its " + time),2000L);
                }
                else {
                    new OldNotificationMaker(context).Alarm("Alarm", "Its " + time);
                }
                db.setAlarmState(alarm.getId(), false);
            }
        }
    }
}
