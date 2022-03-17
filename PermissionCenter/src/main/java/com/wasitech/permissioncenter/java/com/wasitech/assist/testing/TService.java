package com.wasitech.permissioncenter.java.com.wasitech.assist.testing;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.basics.Storage;

import java.io.File;

public class TService extends Service {
    MediaRecorder recorder;
    File audiofile;
    //String name, phonenumber;
    //String audio_format;
    //public String Audio_Type;
    //int audioSource;
    Context context;
    //private Handler handler;
    //Timer timer;
    //Boolean offHook = false, ringing = false;
    //Toast toast;
    //Boolean isOffHook = false;
    CallBr br_call;
    private boolean recordstarted = false;

    private static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    private static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        Basics.Log( "call recording destroy");

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // final String terminate =(String)
        // intent.getExtras().get("terminate");//
        // intent.getStringExtra("terminate");
        // Log.d("TAG", "service started");
        //
        // TelephonyManager telephony = (TelephonyManager)
        // getSystemService(Context.TELEPHONY_SERVICE); // TelephonyManager
        // // object
        // CustomPhoneStateListener customPhoneListener = new
        // CustomPhoneStateListener();
        // telephony.listen(customPhoneListener,
        // PhoneStateListener.LISTEN_CALL_STATE);
        // context = getApplicationContext();

        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_OUT);
        filter.addAction(ACTION_IN);

        br_call = new CallBr();
        this.registerReceiver(br_call, filter);

        // if(terminate != null) {
        // stopSelf();
        // }
        startForeground(1211,new OldNotificationMaker(context).ServicesNotify("Auto Call Reording","Service is Active",null));

        return START_NOT_STICKY;
    }

    public class CallBr extends BroadcastReceiver {
        Bundle bundle;
        String state;
        String inCall, outCall;
        public boolean wasRinging = false;

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==null){
                Basics.Log("ACTION Empty");
                Toast.makeText(context, "ACTION Empty", Toast.LENGTH_LONG).show();
                return;
            }
            if (intent.getAction().equals(ACTION_IN)) {
                if ((bundle = intent.getExtras()) != null) {
                    state = bundle.getString(TelephonyManager.EXTRA_STATE);
                    if(state==null) {
                        Basics.Log("state Empty");

                        Toast.makeText(context, "STATE Empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        inCall = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        wasRinging = true;
                        Basics.Log("in "+inCall);
                        Toast.makeText(context, "IN : " + inCall, Toast.LENGTH_LONG).show();
                    }
                    else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                        if (wasRinging) {
                            Basics.Log("Answered");

                            Toast.makeText(context, "ANSWERED", Toast.LENGTH_LONG).show();
                            audiofile = Storage.CreateDataFile(Storage.REC,".amr");

                            recorder = new MediaRecorder();
//                          recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);

                            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                            recorder.setOutputFile(audiofile.getAbsolutePath());
                            try {
                                recorder.prepare();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            recorder.start();
                            recordstarted = true;
                        }
                    }
                    else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        wasRinging = false;
                        Basics.Log("REJECT || DISCO");
                        Toast.makeText(context, "REJECT || DISCO", Toast.LENGTH_LONG).show();
                        if (recordstarted) {
                            recorder.stop();
                            recordstarted = false;
                        }
                    }
                }
            } else if (intent.getAction().equals(ACTION_OUT)) {
                if ((bundle = intent.getExtras()) != null) {
                    outCall = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    Toast.makeText(context, "OUT : " + outCall, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
