package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;

import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.activators.Activator;
import com.wasitech.assist.activators.Whistler;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.notify.OldNotificationMaker;

import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

public class PhoneFinderService extends BaseService implements Whistler {
    private Activator detectorThread;
    private Activator.RecorderThread recorderThread;
    private Handler handler;

    public PhoneFinderService() {
        super(Params.PHONE_FINDER);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        startWhistleDetection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(ID_LISTENER, new OldNotificationMaker(getApplicationContext()).ServicesNotify("Nasreen", "Phone Finder.", null));
        return START_STICKY;
    }

    @Override
    public void onWhistleDetected() {
        startWhistleDetection();
        ProcessApp.talk(getApplicationContext(), "Beep Beep Beep.");
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            manager.setTorchMode((manager.getCameraIdList())[0], true);
        } catch (CameraAccessException e) {
            Issue.print(e, PhoneFinderService.class.getName());
        }
        handler.postDelayed(() -> {
            try {
                manager.setTorchMode((manager.getCameraIdList())[0], false);
            } catch (CameraAccessException e) {
                Issue.print(e, PhoneFinderService.class.getName());
            }
            Basics.AudioVideo.Vibrator(getApplicationContext(), 2000L);
        }, 800);
    }

    private void startWhistleDetection() {
        try {
            stopWhistleDetection();
        } catch (Exception e) {
            Issue.print(e, PhoneFinderService.class.getName());
        }
        recorderThread = new Activator.RecorderThread();
        recorderThread.startRecording();
        detectorThread = new Activator(recorderThread);
        detectorThread.setWhistler(this);
        detectorThread.start();

    }

    private void stopWhistleDetection() {
        if (detectorThread != null) {
            detectorThread.stopDetection();
            detectorThread.setWhistler(null);
            detectorThread = null;
        }

        if (recorderThread != null) {
            recorderThread.stopRecording();
            recorderThread = null;
        }
    }

    @Override
    public void onDestroy() {
        if (ProcessApp.getPref().getBoolean(Params.PHONE_FINDER, false))
            startWhistleDetection();
        else
            stopWhistleDetection();
        super.onDestroy();
    }
}
