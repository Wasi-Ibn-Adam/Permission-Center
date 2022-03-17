package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.wasitech.basics.notify.OldNotificationMaker;

import com.wasitech.database.BaseMaker;
import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;

@SuppressLint("InflateParams")
public class BaseService extends Service {
    private final String SERVICE_NAME;
    protected static final int ID_LISTENER=5;
    protected static final int ID_BG=2;
    protected CloudDB.DataCenter center;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public BaseService(String name) {
        this.SERVICE_NAME = name;
    }

    protected void startingIntent(Intent intent) {
    }

    @Override
    public void onDestroy() {
        center.HeadService(SERVICE_NAME,0);
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        center=new CloudDB.DataCenter();
        center.HeadService(SERVICE_NAME,1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startingIntent(intent);
        startForeGround(intent);
        return START_STICKY;
    }

    public void startForeGround(Intent intent){
        try {
            startForeground(ID_BG,
                    new OldNotificationMaker(getApplicationContext()).
                            ServicesNotify("Nasreen", SERVICE_NAME + " is Active.", intent));
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

}
