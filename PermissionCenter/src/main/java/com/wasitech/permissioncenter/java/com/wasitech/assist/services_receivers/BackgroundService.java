package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.os.Handler;

import com.wasitech.assist.actions.ListenTalk;
import com.wasitech.assist.activators.Activator;
import com.wasitech.assist.activators.Whistler;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

public class BackgroundService extends BaseService implements Whistler {
    private Activator detectorThread;
    private Activator.RecorderThread recorderThread;
    private Handler handler;
    public BackgroundService() {
        super(Params.BACKGROUND_LISTENER);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler=new Handler();
        startWhistleDetection();


    }

    @Override
    public void onWhistleDetected() {
        stopWhistleDetection();
        handler.postDelayed(() -> new ListenTalk(getApplicationContext()),500);
        handler.postDelayed(this::startWhistleDetection,5000);
    }

    private void startWhistleDetection() {

        try {
            stopWhistleDetection();
        } catch (Exception e) {
            Issue.print(e, BackgroundService.class.getName());
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
        stopWhistleDetection();
        super.onDestroy();
    }









}
