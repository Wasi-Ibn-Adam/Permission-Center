package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;

import com.wasitech.database.Params;

public class Modes  {
    private final AudioManager audioManager;
    private String line;
    private final SharedPreferences pref;
    public Modes(Context context,String command){
        pref=context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        audioManager =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if(command!=null){
            command=cropper(command.toLowerCase()).toLowerCase();
            if(command.contains("normal")||command.contains("deactivate")){
                normalMode();
            }
            if(command.contains("dead")||command.contains("sleep")){
                sleepMode();
            }
            if(command.contains("silent")){
                silentMode("Silent Mode");
            }
            if(command.contains("namaz")||command.contains("namaj")||command.contains("namaaz")){
                silentMode("Namaz Mode");
            }
            if(command.contains("mute")||command.contains("unmute")){
               if(command.contains("noti")){
                   notiMute(!command.contains("unmute"));
               }
               if(command.contains("music")){
                   mediaMute(!command.contains("unmute"));
               }
               if(command.contains("ring")){
                   ringTuneMute(!command.contains("unmute"));
               }
            }

        }
    }
    public Modes(Context context){
        pref=context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        audioManager =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    }
    private String cropper(String str){
        str=str.replace("mode"," ").trim();
        str=str.replace("mod"," ").trim();
        if(!str.contains("deactivate"))
            str=str.replace("activate"," ").trim();
        str=str.replace("apply"," ").trim();
        str=str.replace("active"," ").trim();
        return str;
    }
    public String whichApply(){
        if(line!=null)
            return line;
        else
            return "";
    }
    public void normalMode(){
        int noti=audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        int ring=audioManager.getStreamVolume(AudioManager.STREAM_RING);
        int system=audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        int music=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int alarm=audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        int call=audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);

        if(pref.getInt(Params.CALL_VOLUME,call)==call&&pref.getInt(Params.RING_VOLUME,ring)==ring&&
                pref.getInt(Params.ALARM_VOLUME,alarm)==alarm&&pref.getInt(Params.MUSIC_VOLUME,music)==music &&
                pref.getInt(Params.SYSTEM_VOLUME,system)==system&& pref.getInt(Params.NOTI_VOLUME,noti)==noti){
            line="Already in Normal Mode.";
            return;
        }

        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, pref.getInt(Params.NOTI_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)), AudioManager.MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, pref.getInt(Params.MUSIC_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)), AudioManager.MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, pref.getInt(Params.ALARM_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)), AudioManager.MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, pref.getInt(Params.RING_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)), AudioManager.MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, pref.getInt(Params.SYSTEM_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)), AudioManager.MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, pref.getInt(Params.CALL_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL)), AudioManager.MODE_NORMAL);

        audioManager.setStreamVolume(AudioManager.STREAM_DTMF, audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF), AudioManager.MODE_NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.setStreamVolume(AudioManager.STREAM_ACCESSIBILITY, audioManager.getStreamMaxVolume(AudioManager.STREAM_ACCESSIBILITY), AudioManager.MODE_NORMAL);
        }
        line= " Applied Normal Mode.";
    }
    public void sleepMode(){
        int noti=audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        int ring=audioManager.getStreamVolume(AudioManager.STREAM_RING);
        int system=audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        int music=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int alarm=audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        int call=audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);

        pref.edit().putInt(Params.RING_VOLUME,ring).putInt(Params.SYSTEM_VOLUME,system)
                .putInt(Params.NOTI_VOLUME,noti).putInt(Params.MUSIC_VOLUME,music)
                .putInt(Params.ALARM_VOLUME,alarm).putInt(Params.CALL_VOLUME,call).apply();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, audioManager.getStreamMinVolume(AudioManager.STREAM_NOTIFICATION), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, audioManager.getStreamMinVolume(AudioManager.STREAM_ALARM), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMinVolume(AudioManager.STREAM_RING), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, audioManager.getStreamMinVolume(AudioManager.STREAM_SYSTEM), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, audioManager.getStreamMinVolume(AudioManager.STREAM_VOICE_CALL), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_DTMF, audioManager.getStreamMinVolume(AudioManager.STREAM_DTMF), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_ACCESSIBILITY, audioManager.getStreamMinVolume(AudioManager.STREAM_ACCESSIBILITY), AudioManager.MODE_NORMAL);

        }else{
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 0,AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,0, AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_DTMF,0, AudioManager.MODE_NORMAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                audioManager.setStreamVolume(AudioManager.STREAM_ACCESSIBILITY,0, AudioManager.MODE_NORMAL);
            }
        }
        line= " Applied Dead Mode.";
    }
    public void silentMode(String mode){
        int noti=audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        int ring=audioManager.getStreamVolume(AudioManager.STREAM_RING);
        int system=audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if(noti==audioManager.getStreamMinVolume(AudioManager.STREAM_NOTIFICATION)
                    &&ring== audioManager.getStreamMinVolume(AudioManager.STREAM_RING)
                    &&system==audioManager.getStreamMinVolume(AudioManager.STREAM_SYSTEM)){
                line=mode+" Already Activated.";
                return;
            }
            pref.edit().putInt(Params.NOTI_VOLUME,noti).putInt(Params.RING_VOLUME,ring).putInt(Params.SYSTEM_VOLUME,system).apply();

            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, audioManager.getStreamMinVolume(AudioManager.STREAM_NOTIFICATION), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMinVolume(AudioManager.STREAM_RING), AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, audioManager.getStreamMinVolume(AudioManager.STREAM_SYSTEM), AudioManager.MODE_NORMAL);

        }else{
            if(noti==0&&ring==0&&system==0){
                line=mode+" Already Activated.";
                return;
            }
            pref.edit().putInt(Params.NOTI_VOLUME,noti).putInt(Params.RING_VOLUME,ring).putInt(Params.SYSTEM_VOLUME,system).apply();

            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 0,AudioManager.MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.MODE_NORMAL);
        }
        line= " Applied "+mode+".";
    }
    public void notiMute(boolean state){
        int noti;
        if(state){
            noti=audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            if(noti==0){
                line="Notification Sound is Already Muted.";
                return;
            }
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,0, AudioManager.MODE_NORMAL);
            line="Notification Sound is Muted.";
        }
        else{
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,pref.getInt(Params.NOTI_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)), AudioManager.MODE_NORMAL);
            noti=audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            line="Notification Sound is UnMuted.";
        }
        pref.edit().putInt(Params.NOTI_VOLUME,noti).apply();
    }
    public void ringTuneMute(boolean state){
        int ring;
        if(state){
            ring=audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            if(ring==0){
                line="RingTune Sound is Already Muted.";
                return;
            }
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.MODE_NORMAL);
            line="RingTune Sound is Muted.";
        }
        else{
            audioManager.setStreamVolume(AudioManager.STREAM_RING, pref.getInt(Params.RING_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)), AudioManager.MODE_NORMAL);
            ring=audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            line="RingTune Sound is UnMuted.";
        }
        pref.edit().putInt(Params.RING_VOLUME,ring).apply();
    }
    public void mediaMute(boolean state){
        int music;
        if(state){
            music=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if(music==0){
                line="Music Sound is Already Muted.";
                return;
            }
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.MODE_NORMAL);
            line="Music Sound is Muted.";
        }
        else{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, pref.getInt(Params.MUSIC_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)), AudioManager.MODE_NORMAL);
            music=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            line="Music Sound is UnMuted.";
        }
        pref.edit().putInt(Params.MUSIC_VOLUME,music).apply();
    }
    public void notiVolume(int volume,long time){
        if(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)!=volume){
            pref.edit().putInt(Params.NOTI_VOLUME,audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)).apply();
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, volume, AudioManager.MODE_NORMAL);
            new Handler().postDelayed(() -> audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, pref.getInt(Params.NOTI_VOLUME,audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)/2), AudioManager.MODE_NORMAL),time);
        }
    }

}
