package com.wasitech.permissioncenter.java.com.wasitech.music.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wasitech.music.OnlinePlayer;
import com.wasitech.music.service.SongService;

public class SongReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String s=intent.getStringExtra("action");
        if(s!=null&&s.startsWith("online")){
            switch (s){
                case "online_play":{
                    OnlinePlayer.playSong();
                    break;
                }
                case "online_pause":{
                    OnlinePlayer.pauseSong();
                    break;
                }
                case "online_close":{
                    OnlinePlayer.closeSong();
                    break;
                }
            }

        }else
        context.startService(
                new Intent(context, SongService.class)
                        .putExtras(intent)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }

}
