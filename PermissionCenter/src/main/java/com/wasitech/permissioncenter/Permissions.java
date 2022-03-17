package com.wasitech.permissioncenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Permissions {
    public static final int R_STORAGE = 100;
    public static final int W_STORAGE = 101;
    public static final int RW_STORAGE = 102;
    public static final int R_CONTACT = 103;
    public static final int W_CONTACT =104;
    public static final int RW_CONTACT =105;
    public static final int RECORD = 106;
    public static final int LOCATION = 107;
    public static final int CAMERA = 108;
    public static final int SMS = 110;
    public static final int PHONE_CALL = 111;

    // storage

    private void requestPermission(Activity context,int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",context.getApplicationContext().getPackageName())));
                context.startActivityForResult(intent, code);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                context.startActivityForResult(intent, code);//2296
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(context, new String[]{WRITE_EXTERNAL_STORAGE}, code);
        }
    }

    public static void askReadStorage(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
    }
    public static void askWriteStorage(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    public static void askReadWriteStorage(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
    }

    public static boolean isNotReadWriteSms(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED;
    }

    public static void askReadWriteSms(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{SEND_SMS, READ_CONTACTS}, requestCode);
    }

    public static void askRecord(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, requestCode);
    }

    public static void askCamera(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, requestCode);
    }

    public static void askReadContact(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, requestCode);
    }

    public static void askWriteContact(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CONTACTS}, requestCode);
    }

    public static void askReadWriteContact(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, requestCode);
    }

    public static boolean isNotLocation(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    public static void askLocation(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
    }

    public static void askPhoneCall(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, requestCode);
    }

}
