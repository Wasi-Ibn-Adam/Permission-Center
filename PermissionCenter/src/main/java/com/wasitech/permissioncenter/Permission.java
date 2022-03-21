package com.wasitech.permissioncenter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

/**
 * All Critical Permission sooner will be handled by
 * this well maintained Assist permission Class
 */
public abstract class Permission {
    public static final int CODE_IGNORE = 666;
    public static final int CODE_SINGLE_CAMERA = 101;
    public static final int CODE_SINGLE_CONTACT = 102;
    public static final int CODE_SINGLE_CALENDER = 103;
    public static final int CODE_SINGLE_LOCATION = 104;
    public static final int CODE_SINGLE_MICROPHONE = 105;
    public static final int CODE_SINGLE_PHONE = 106;
    public static final int CODE_SINGLE_SMS = 107;
    public static final int CODE_SINGLE_STORAGE = 108;

    public static final int ASKED = -2;
    public static final int DENIED = -1;
    public static final int GRANTED = 0;
    public static final int NEVER_AGAIN = 1;

    protected final int TRUE = 1, FALSE = 0;

    protected final Activity ac;
    private final Request rq;

    public Permission(Activity ac) {
        this.ac = ac;
        rq = new Request();
    }
    /**
     on your requestPermissionResult call onResult(int) method and pass the the requestCode
     */
    public Request request() {
        return rq;
    }

    protected static boolean isPermissionGranted(Context c, String per) {
        return ActivityCompat.checkSelfPermission(c, per) == PackageManager.PERMISSION_GRANTED;
    }
    protected void onePermissionCheck(Activity ac, String permission, int code) {
        int t = ac.checkSelfPermission(permission);
        if (t != PackageManager.PERMISSION_GRANTED) {
            if (getRationaleStatus(permission))
                onDenied(code);
            else
                neverAskAgain(code);
        } else
            onGranted(code);
        onComplete(code);
    }

    public void onResult(int requestCode) {
        switch (requestCode) {
            case CODE_IGNORE: {
                break;
            }
            case CODE_SINGLE_CAMERA: {
                onePermissionCheck(ac, Manifest.permission.CAMERA, requestCode);
                break;
            }
            case CODE_SINGLE_CONTACT: {
                onePermissionCheck(ac, Manifest.permission.WRITE_CONTACTS, requestCode);
                break;
            }
            case CODE_SINGLE_LOCATION: {
                onePermissionCheck(ac, Manifest.permission.ACCESS_FINE_LOCATION, requestCode);
                break;
            }
            case CODE_SINGLE_MICROPHONE: {
                onePermissionCheck(ac, Manifest.permission.RECORD_AUDIO, requestCode);
                break;
            }
            case CODE_SINGLE_PHONE: {
                onePermissionCheck(ac, Manifest.permission.CALL_PHONE, requestCode);
                break;
            }
            case CODE_SINGLE_SMS: {
                onePermissionCheck(ac, Manifest.permission.SEND_SMS, requestCode);
                break;
            }
            case CODE_SINGLE_STORAGE: {
                onePermissionCheck(ac, Manifest.permission.WRITE_EXTERNAL_STORAGE, requestCode);
                break;
            }
        }
    }



    public class Request {
        public void camera() {
            checkBeforeRequesting(Manifest.permission.CAMERA, CODE_SINGLE_CAMERA);
        }

        public void camera(int code) {
            checkBeforeRequesting(Manifest.permission.CAMERA, code);
        }

        public void contacts() {
            checkBeforeRequesting(Manifest.permission.WRITE_CONTACTS, CODE_SINGLE_CONTACT);
        }

        public void contacts(int code) {
            checkBeforeRequesting(Manifest.permission.WRITE_CONTACTS, code);
        }

        public void location() {
            checkBeforeRequesting(Manifest.permission.ACCESS_FINE_LOCATION, CODE_SINGLE_LOCATION);
        }

        public void location(int code) {
            checkBeforeRequesting(Manifest.permission.ACCESS_FINE_LOCATION, code);
        }

        public void microphone() {
            checkBeforeRequesting(Manifest.permission.RECORD_AUDIO, CODE_SINGLE_MICROPHONE);
        }

        public void microphone(int code) {
            checkBeforeRequesting(Manifest.permission.RECORD_AUDIO, code);
        }

        public void phone() {
            readPhoneStatePermission();
            checkBeforeRequesting(Manifest.permission.CALL_PHONE, CODE_SINGLE_PHONE);
        }

        public void phone(int code) {
            readPhoneStatePermission();
            checkBeforeRequesting(Manifest.permission.CALL_PHONE, code);
        }

        public void sms() {
            checkBeforeRequesting(Manifest.permission.SEND_SMS, CODE_SINGLE_SMS);
        }

        public void sms(int code) {
            checkBeforeRequesting(Manifest.permission.SEND_SMS, code);
        }

        public void storage() {
            checkBeforeRequesting(Manifest.permission.WRITE_EXTERNAL_STORAGE, CODE_SINGLE_STORAGE);
        }

        public void storage(int code) {
            checkBeforeRequesting(Manifest.permission.WRITE_EXTERNAL_STORAGE, code);
        }
    }

    public static class Check {
        public static boolean camera(Context c) {
            return isPermissionGranted(c, Manifest.permission.CAMERA);
        }

        public static boolean contacts(Context c) {
            return isPermissionGranted(c, Manifest.permission.WRITE_CONTACTS);
        }

        public static boolean location(Context c) {
            return isPermissionGranted(c, Manifest.permission.ACCESS_FINE_LOCATION);
        }

        public static boolean microphone(Context c) {
            return isPermissionGranted(c, Manifest.permission.RECORD_AUDIO);
        }

        public static boolean sms(Context c) {
            return isPermissionGranted(c, Manifest.permission.SEND_SMS);
        }

        public static boolean phone(Context c) {
            return isPermissionGranted(c, Manifest.permission.CALL_PHONE);
        }

        public static boolean sensors(Context c) {
            return isPermissionGranted(c, Manifest.permission.BODY_SENSORS);
        }

        public static boolean storage(Context c) {
            return isPermissionGranted(c, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    public void requireRationaleAsk(String[] permissions, int code) {
        setShouldShowStatus(permissions, TRUE);
    }

    public void requireSimpleAsk(String[] permissions, int code) {
        setShouldShowStatus(permissions, FALSE);
    }

    public abstract void onDenied(int code);

    public abstract void onGranted(int code);

    public abstract void neverAskAgain(int code);

    public void onComplete(int code) {
    }

    private void checkBeforeRequesting(String permission, int code) {
        if (isPermissionGranted(ac, permission))
            onGranted(code);
        else {
            if (isRationaleStatus(permission)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ac, permission)) {
                    requireRationaleAsk(new String[]{permission}, code);
                } else {
                    requireSimpleAsk(new String[]{permission}, code);
                }
            } else {
                requireRationaleAsk(new String[]{permission}, code);
            }
        }
    }

    private void readPhoneStatePermission() {
        if (!isPermissionGranted(ac, Manifest.permission.READ_PHONE_STATE)) {
            ac.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, CODE_IGNORE);
        }
    }


    protected void setShouldShowStatus(String[] permissions, int set) {
        SharedPreferences pref = ac.getSharedPreferences(ac.getPackageName(), Context.MODE_PRIVATE);
        for (String per : permissions)
            pref.edit().putInt(per, set).apply();
    }

    protected boolean isRationaleStatus(String permission) {
        return (ac.getSharedPreferences(ac.getPackageName(), Context.MODE_PRIVATE)
                .getInt(permission, -1) != -1);
    }

    protected boolean getRationaleStatus(String permission) {
        return (ac.getSharedPreferences(ac.getPackageName(), Context.MODE_PRIVATE)
                .getInt(permission, 1) == 1);
    }


    public void displayRationale(String text, int code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit", (dialog, which) -> {
            dialog.dismiss();
            ac.requestPermissions(Params.AppPermission.permissionList(code), code);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void displaySimple(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit", (dialog, which) -> {
            dialog.dismiss();
            ac.startActivity(Params.Intents.gotoSettings(ac.getPackageName()));
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void displayRationale(String text, int code, boolean close) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit", (dialog, which) -> {
            dialog.dismiss();
            ac.requestPermissions(Params.AppPermission.permissionList(code), code);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            if (close)
                ac.finishAndRemoveTask();
        });
        builder.show();
    }

    public void displaySimple(String text, boolean close) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit", (dialog, which) -> {
            dialog.dismiss();
            ac.startActivity(Params.Intents.gotoSettings(ac.getPackageName()));
            if (close)
                ac.finishAndRemoveTask();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            if (close)
                ac.finishAndRemoveTask();
        });
        builder.show();
    }

}
