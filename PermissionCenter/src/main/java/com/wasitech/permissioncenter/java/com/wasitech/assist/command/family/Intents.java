package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;

import com.wasitech.NeverEnding.AssistAlwaysOn;
import com.wasitech.ting.Chat.Activity.TingGoMenuActivity;
import com.wasitech.ting.Chat.Activity.TingGoUsersList;
import com.wasitech.ting.Noti.DeveloperTingsChatActivity;
import com.wasitech.assist.activities.AlarmActivity;
import com.wasitech.assist.activities.MainActivity;
import com.wasitech.assist.activities.NotificationAppList;
import com.wasitech.assist.activities.NumberFinderActivity;
import com.wasitech.camera.activities.ImageListAct;
import com.wasitech.assist.activities.Settings;
import com.wasitech.assist.activities.TingNoActivity;
import com.wasitech.assist.classes.Alarm;
import com.wasitech.assist.services_receivers.AlarmReceiver;
import com.wasitech.assist.services_receivers.AudioRecordingHeadService;
import com.wasitech.assist.services_receivers.BackgroundService;
import com.wasitech.assist.services_receivers.CameraHeadService;
import com.wasitech.assist.services_receivers.CommandHeadService;
import com.wasitech.database.Params;
import com.wasitech.permission.PermissionChecker;
import com.wasitech.register.activity.AppStart;
import com.wasitech.register.activity.SignInUp;

public class Intents {

    // PERMISSIONS ONLY

    public static Intent permission() {
        return null;
    }


    // PERMISSIONS WITH WORK

    public static Intent picShowPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.PIC_SHOW);
        return intent;
    }
    public static Intent smsSendPermission(Context context,String msg,String data) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.SMS_SEND);
        intent.putExtra(Params.MESSAGE,msg);
        intent.putExtra(Params.NUM, data);
        return intent;
    }

    public static Intent videoShowPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.VIDEO_SHOW);
        return intent;
    }

    public static Intent passwordPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.PASSWORD);
        return intent;
    }

    public static Intent musicShowPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.MUSIC_SHOW);
        return intent;
    }

    public static Intent musicFindPermission(Context context, String name) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.MUSIC_FIND);
        intent.putExtra(PermissionChecker.MUSIC_FIND, name);
        return intent;
    }

    public static Intent musicPlayPermission(Context context, String name) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.MUSIC_PLAY);
        intent.putExtra(PermissionChecker.MUSIC_PLAY, name);
        return intent;
    }

    public static Intent screenShotPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.SCREENSHOT);
        return intent;
    }

    public static Intent flashPermission(Context context, boolean state) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.FLASH);
        intent.putExtra(PermissionChecker.FLASH, state);
        return intent;
    }

    public static Intent lockPermission(Context context, boolean state) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.LOCK);
        intent.putExtra(PermissionChecker.LOCK, state);
        return intent;
    }

    public static Intent picPermission(Context context, int cam) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.CAMERA);
        intent.putExtra(PermissionChecker.CAMERA, cam);
        return intent;
    }

    public static Intent dialerPermission(Context context, String num) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.DIAL);
        intent.putExtra(PermissionChecker.DIAL, num);
        return intent;
    }

    public static Intent contactShowPermission(Context context, String name) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.CONTACT_SHOW);
        intent.putExtra(PermissionChecker.CONTACT_SHOW, name);
        return intent;
    }

    public static Intent backgroundPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.BG_HEAD);
        return intent;
    }

    public static Intent backgroundPermission(Context context, boolean start) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.BG_HEAD);
        intent.putExtra(PermissionChecker.BG_HEAD, start);
        return intent;
    }

    public static Intent audioHeadPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.AUDIO_HEAD);
        return intent;
    }

    public static Intent audioHeadPermission(Context context, boolean start) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.AUDIO_HEAD);
        intent.putExtra(PermissionChecker.AUDIO_HEAD, start);
        return intent;
    }

    public static Intent cameraHeadPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.CAMERA_HEAD);
        return intent;
    }

    public static Intent cameraHeadPermission(Context context, boolean start) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.CAMERA_HEAD);
        intent.putExtra(PermissionChecker.CAMERA_HEAD, start);
        return intent;
    }

    public static Intent commandHeadPermission(Context context) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.COMMAND_HEAD);
        return intent;
    }

    public static Intent commandHeadPermission(Context context, boolean start) {
        Intent intent = new Intent(context, PermissionChecker.class);
        intent.putExtra(Params.DATA_TRANS, PermissionChecker.COMMAND_HEAD);
        intent.putExtra(PermissionChecker.COMMAND_HEAD, start);
        return intent;
    }

    // ACTIVITIES

    public static Intent NotiAppActivity(Context context) {
        return new Intent(context, NotificationAppList.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent MainActivity(Context context) {
        return new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent TingGoMenuActivity(Context context) {
        return new Intent(context, TingGoMenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent TingNoActivity(Context context) {
        return new Intent(context, TingNoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent LogInSignIn(Context context, boolean fromIn) {
        if (fromIn)
            return new Intent(context, SignInUp.class);
        else
            return new Intent(context, SignInUp.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public static Intent MainActivity(Context context, boolean fromIn) {
        if (fromIn)
            return new Intent(context, MainActivity.class);
        else
            return new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }



    public static Intent SettingsActivity(Context context) {
        return new Intent(context, Settings.class);
    }

    public static Intent BeganActivity(Context context) {
        return new Intent(context, AppStart.class);
    }

    public static Intent Recognizer(String language, int results) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, results);
        return intent;
    }

    public static Intent UpdateIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(Params.APP_STORE_LINK)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent policyIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(Params.APP_STORE_PRIVACY_LINK)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    // FUNCTIONS


    public static Intent NumberFind(Context context) {
        return new Intent(context.getApplicationContext(), NumberFinderActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent PictureListActivity(Context context) {
        return new Intent(context, ImageListAct.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent alarmShow(Context context) {
        return new Intent(context, AlarmActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent imageIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        return intent;
    }

    public static PendingIntent AlarmIntent(Context context, Alarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("time", alarm.getTime());
        intent.putExtra("talk", alarm.getTalk());
        intent.putExtra("h", alarm.getH());
        intent.putExtra("m", alarm.getM());
        return PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    // SERVICES

    public static Intent AudioHead(Context context) {
        return new Intent(context, AudioRecordingHeadService.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent CamHead(Context context) {
        return new Intent(context, CameraHeadService.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent ComHead(Context context) {
        return new Intent(context, CommandHeadService.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent Background(Context context) {
        return new Intent(context, BackgroundService.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent AlwaysOn(Context context) {
        return new Intent(context, AssistAlwaysOn.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent DeveloperTingNo(Context context,String id) {
        return new Intent(context, DeveloperTingsChatActivity.class).putExtra(Params.ID,id).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static Intent TingUser(Context context) {
        return new Intent(context, TingGoUsersList.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
