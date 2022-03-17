package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.wasitech.assist.actions.Open;
import com.wasitech.assist.activities.AlarmActivity;
import com.wasitech.assist.activities.NumberFinderActivity;
import com.wasitech.assist.command.family.Answers;
import com.wasitech.assist.command.family.Coder;
import com.wasitech.assist.command.family.ComFuns;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.command.family.WFamily;
import com.wasitech.assist.weather.Weathers;
import com.wasitech.assist.web.AssistWeb;
import com.wasitech.basics.Storage;
import com.wasitech.basics.classes.Basics;
import com.wasitech.database.Params;
import com.wasitech.music.activity.MusicListAct;
import com.wasitech.permission.PermissionChecker;
import com.wasitech.permission.Permissions;

public abstract class Speak extends VoiceToText {
    public Speak(Context context, String from) {
        super(context, from);
    }


    protected void FIRST_CHECK() {
        switch (stack.pop()) {
            case Coder.L_MESSAGE: {
                stack.push(Coder.ACTION_MESSAGE);
                Again(1500);
                break;
            }
            case Coder.L_YES: {
                Again(500);
                break;
            }
            case Coder.L_HELP: {
                Again(700);
                break;
            }
            case Coder.L_FUN: {
                stack.push(Coder.ACTION_FUN);
                Again(1100);
                break;
            }
            case Coder.WEATHER: {
                if (!Permissions.isNotLocation(context)) {
                    Weathers weathers = new Weathers(context);
                    if (weathers.isLocationActive()) {
                        weather(weathers);
                    } else {
                        Talking("Turn on Location.");
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                } else
                    PermissionRequire(Coder.PERMISSION_LOCATION);
                break;
            }
            case Coder.CLEAR_CACHE: {
                if (Storage.clearCache(context)) {
                    Talking("Done");
                } else
                    Talking("Error came up");
                break;
            }
            case Coder.SOCIAL: {
                if (!new Open(context).social(ComFuns.socialAppName(stack.pop()))) {
                    Talking("App not found in your phone.");
                }
                break;
            }
            case Coder.EXIT: {
                new Handler().postDelayed(this::ByeBye, 500);
                break;
            }
            case Coder.SET_ALARM:
            case Coder.HINT_SET_ALARM: {
                stack.push(Coder.ACTION_SET_ALARM);
                Again(800);
                break;
            }
            case Coder.HINT_SHOW_ALARM: {
                stack.push(Coder.ACTION_SHOW_ALARM);
                Again(600);
                break;
            }
            case Coder.HINT_CAM: {
                stack.push(Coder.ACTION_CAM);
                Again(1500);
                break;
            }
            case Coder.HINT_MUSIC: {
                stack.push(Coder.ACTION_MUSIC);
                Again(1400);
                break;
            }
            case Coder.HINT_SONG: {
                stack.push(Coder.ACTION_SONG);
                Again(1500);
                break;
            }
            case Coder.HINT_RECORD: {
                stack.push(Coder.ACTION_RECORD);
                Again(2000);
                break;
            }
            case Coder.HINT_FIND_NUMBER: {
                stack.push(Coder.ACTION_FIND_NUMBER);
                Again(1100);
                break;
            }
            case Coder.HINT_WRONG_PASSWORD: {
                stack.push(Coder.ACTION_WRONG_PASSWORD);
                Again(1000);
                break;
            }
            case Coder.HINT_GOOGLE: {
                stack.push(Coder.ACTION_GOOGLE);
                Again(2000);
                break;
            }
        }
    }

    protected void LAST_CHECK(String input) {

        switch (stack.pop()) {
            case Coder.ACTION_MESSAGE: {
                context.startActivity(Intents.smsSendPermission(context,input,data));
                //if (!Permissions.isNotReadContact(context))
                //    Basics.Send.sendMessage(context, input, data);
                //else
                //    PermissionRequire(Coder.PERMISSION_R_CONTACT);
                break;
            }
            case Coder.ACTION_RECORD: {
                Basics.Log("came");
                if (ComFuns.yes(input)) {
                    if (stack.pop() == Coder.R_RECORD_START)
                        context.startActivity(Intents.audioHeadPermission(context,true));
                    else {
                        boolean result = context.stopService(Intents.AudioHead(context.getApplicationContext()));
                        if (result) {
                            AppCommand("Service Stopped.");
                            Talking("Service Stopped.");
                        } else {
                            AppCommand("Service not active.");
                            Talking("Service not active.");
                        }
                    }
                } else {
                    stack.pop();
                    Talking(Answers.OK_INFORMAL());
                }
                break;
            }
            case Coder.ACTION_SONG: {
                if (ComFuns.yes(input)) {
                    switch (stack.pop()) {
                        case Coder.R_SONG_NEXT: {
                            Answers.NEXT_SONG(context);
                            break;
                        }
                        case Coder.R_SONG_PLAY: {
                            context.startActivity(MusicListAct.Intents.Audio(context, MusicListAct.Intents.PLAY));
                            break;
                        }
                        case Coder.R_SONG_PAUSE: {
                            Answers.PAUSE_SONG(context);
                            break;
                        }
                        case Coder.R_SONG_PREV: {
                            Answers.PREV_SONG(context);
                            break;
                        }
                        case Coder.R_SONG_STOP: {
                            Answers.STOP_SONG(context);
                            break;
                        }
                    }
                }
                else {
                    stack.pop();
                    Talking(Answers.OK_INFORMAL());
                }
                break;
            }
            case Coder.ACTION_CAM: {
                if (ComFuns.yes(input)) {
                    if (stack.pop() == Coder.R_CAM_FRONT)
                        context.startActivity(Intents.picPermission(context, PermissionChecker.FRONT_CAM));
                    else
                        context.startActivity(Intents.picPermission(context,PermissionChecker.RARE_CAM));
                } else {
                    stack.pop();
                    Talking(Answers.OK_INFORMAL());
                }
                break;
            }
            case Coder.ACTION_FUN: {
                if (ComFuns.yes(input))
                    Talking("Good.");
                else
                    Talking(Answers.OK_INFORMAL());
                break;
            }
            case Coder.ACTION_GOOGLE: {
                if (ComFuns.yes(input))
                    context.startActivity(new Intent(context, AssistWeb.class).putExtra("data", data).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                else
                    Talking(Answers.OK_INFORMAL());
                break;
            }
            case Coder.ACTION_WRONG_PASSWORD: {
                if (ComFuns.yes(input)) {
                    Talking("Its in settings, Select gear on right upper corner and its 4th option!");
                }
                else
                    Talking(Answers.OK_INFORMAL());
                break;
            }
            case Coder.ACTION_FIND_NUMBER: {
                data = data.replace("Do you want to Find Owners name of ", "");
                context.startActivity(new Intent(context.getApplicationContext(), NumberFinderActivity.class).putExtra(Params.DATA_TRANS, data).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            }
            case Coder.ACTION_SHOW_ALARM: {
                if (ComFuns.yes(input)) {
                    context.startActivity(new Intent(context.getApplicationContext(), AlarmActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                else
                    Talking(Answers.OK_INFORMAL());
                break;
            }
            case Coder.ACTION_SET_ALARM: {
                String res = AlarmActivity.setAlarm(context, input,"");
                if (res != null) {
                    Talking("Alarm is set " + res);
                } else {
                    Talking("Unable to set Alarm! you can manually set it!");
                    context.startActivity(new Intent(context.getApplicationContext(), AlarmActivity.class).putExtra(Params.DATA_TRANS, "set").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                break;
            }
            case Coder.ACTION_MUSIC: {
                if (ComFuns.yes(input)) {
                    if (stack.pop() == Coder.R_MUSIC_STOP)
                        Talking(Basics.AudioVideo.stopAudio(context));
                } else {
                    stack.pop();
                    Talking(Answers.OK_INFORMAL());
                }
                break;
            }
            case Coder.PERMISSION_CAMERA:{
                this.PermissionRequire(Coder.PERMISSION_CAMERA);
                break;
            }
            case Coder.PERMISSION_LOCATION:{
                this.PermissionRequire(Coder.PERMISSION_LOCATION);
                break;
            }
            case Coder.PERMISSION_MIC:{
                this.PermissionRequire(Coder.PERMISSION_MIC);
                break;
            }
            case Coder.PERMISSION_R_STORAGE:{
                this.PermissionRequire(Coder.PERMISSION_R_STORAGE);
                break;
            }
            case Coder.PERMISSION_W_STORAGE:{
                this.PermissionRequire(Coder.PERMISSION_W_STORAGE);
                break;
            }
            case Coder.PERMISSION_RW_STORAGE:{
                this.PermissionRequire(Coder.PERMISSION_RW_STORAGE);
                break;
            }
            case Coder.PERMISSION_R_CONTACT:{
                this.PermissionRequire(Coder.PERMISSION_R_CONTACT);
                break;
            }
            case Coder.PERMISSION_W_CONTACT:{
                this.PermissionRequire(Coder.PERMISSION_W_CONTACT);
                break;
            }
            case Coder.PERMISSION_RW_CONTACT:{
                this.PermissionRequire(Coder.PERMISSION_RW_CONTACT);
                break;
            }
        }

    }

    private void weather(Weathers weathers) {
        switch (stack.pop()) {
            case Coder.R_WEATHER_N: {
                Talking(Answers.WAIT());
                weathers.getCurr(true,Coder.R_WEATHER_N);
                break;
            }
            case Coder.R_WEATHER_T: {
                Talking(Answers.WAIT());
                weathers.getCurr(true,Coder.R_WEATHER_T);
                break;
            }
            case Coder.R_WEATHER_L_N: {
                Talking(Answers.WAIT());
                weathers.getCurr(true,Coder.R_WEATHER_L_N, WFamily.city);
                break;
            }
            case Coder.R_WEATHER_L_T: {
                Talking(Answers.WAIT());
                weathers.getCurr(true,Coder.R_WEATHER_L_T, WFamily.city);
                break;
            }
            case Coder.R_WEATHER_F: {
                Talking(Answers.FORECAST());
                break;
            }
            default: {
                weathers.getCurr(true,Coder.R_WEATHER_N);
            }
        }
    }

    private void Again(int millis) {
        new Handler().postDelayed(this::StartSpeak, millis);
    }
}
