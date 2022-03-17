package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.music.service.SongService;

public class BluetoothReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String intentAction = intent.getAction();
        if (intentAction != null) {
            Basics.Log("bl-"+intentAction);
            switch (intentAction){
                case BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED:{break;}
                case BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED:{break;}
                case BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED:{break;}
                case BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE:{break;}
                case BluetoothAdapter.EXTRA_PREVIOUS_STATE:{break;}
                case BluetoothAdapter.EXTRA_CONNECTION_STATE:{break;}
                case BluetoothAdapter.EXTRA_STATE:{break;}
                case BluetoothAdapter.ACTION_STATE_CHANGED:{break;}
                case Intent.ACTION_MEDIA_BUTTON:{
                    if(SongService.player==null)
                        context.startService(SongService.BroadCastIntents.initialize(context, ProcessApp.saList));
                    break;
                }
            }
        }
    }
}
