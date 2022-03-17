package com.wasitech.permissioncenter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.wasitech.basics.app.ProcessApp;

import java.util.ArrayList;
import java.util.Collections;

/**
 * All Critical Permission sooner will be handled by
 * this well maintained Assist permission Class
 */
public abstract class Permission {
    public static final int CODE_IGNORE = 666;


    public static final int CODE_CAMERA = 101;
    public static final int CODE_CONTACT = 102;
    public static final int CODE_CALENDER = 103;
    public static final int CODE_LOCATION = 104;
    public static final int CODE_MICROPHONE = 105;
    public static final int CODE_PHONE = 106;
    public static final int CODE_SENSORS = 107;
    public static final int CODE_SMS = 108;
    public static final int CODE_STORAGE = 109;
    public static final int CODE_VPN = 110;

    public static final int ASKED = -2;
    public static final int DENIED = -1;
    public static final int GRANTED = 0;
    public static final int NEVER_AGAIN = 1;

    protected final int TRUE=1,FALSE=0;

    protected final Activity ac;
    private final Request rq;

    public Permission(Activity ac) {
        this.ac = ac;
        rq = new Request();
    }

    public Request request() {
        return rq;
    }

    private static Intent gotoSettingsP(String pkg) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", pkg, null);
        intent.setData(uri);
        return intent;
    }

    protected static boolean singlePermissionCheck(Context c, String per) {
        return ActivityCompat.checkSelfPermission(c, per) == PackageManager.PERMISSION_GRANTED;
    }

    public static Intent gotoSettings(String pkg) {
        return gotoSettingsP(pkg);
    }

    public static Intent gotoDrawOver(String pkg) {
        return new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + pkg));
    }

    public void onResult(int requestCode) {
        switch (requestCode) {
            case Permission.CODE_IGNORE:{break;}
            case com.wasitech.permission.PermissionGroup.FLASH:
            case CODE_CAMERA: {
                checkOne(ac, Manifest.permission.CAMERA, requestCode);
                break;
            }
            case com.wasitech.permission.PermissionGroup.CONTACT_SHOW:
            case com.wasitech.permission.PermissionGroup.CONTACT_DIAL:
            case CODE_CONTACT: {
                checkOne(ac, Manifest.permission.WRITE_CONTACTS, requestCode);
                break;
            }
            case CODE_LOCATION: {
                checkOne(ac, Manifest.permission.ACCESS_FINE_LOCATION, requestCode);
                break;
            }
            case com.wasitech.permission.PermissionGroup.COM_HEAD:
            case CODE_MICROPHONE: {
                checkOne(ac, Manifest.permission.RECORD_AUDIO, requestCode);
                break;
            }
            case CODE_PHONE: {
                checkOne(ac, Manifest.permission.CALL_PHONE, requestCode);
                break;
            }
            case CODE_SENSORS: {
                checkOne(ac, Manifest.permission.BODY_SENSORS, requestCode);
                break;
            }
            case CODE_SMS: {
                checkOne(ac, Manifest.permission.SEND_SMS, requestCode);
                break;
            }
            case com.wasitech.permission.PermissionGroup.MUSIC_FIND:
            case com.wasitech.permission.PermissionGroup.MUSIC_PLAY:
            case com.wasitech.permission.PermissionGroup.MUSIC_SHOW:
            case com.wasitech.permission.PermissionGroup.PIC_SHOW:
            case com.wasitech.permission.PermissionGroup.VIDEO_SHOW:
            case CODE_STORAGE: {
                checkOne(ac, Manifest.permission.WRITE_EXTERNAL_STORAGE, requestCode);
                break;
            }
            case CODE_VPN: {
                checkOne(ac, Manifest.permission.BIND_VPN_SERVICE, requestCode);
                break;
            }
        }
    }

    public void requireRationaleAsk(String permission,int code){
        setShouldShowStatus(permission, TRUE);
    }
    public void requireSimpleAsk(String permission,int code){
        setShouldShowStatus(permission, FALSE);
    }

    public abstract void onDenied(int code);

    public abstract void onGranted(int code);

    public abstract void neverAskAgain(int code);

    public void onComplete(int code){}

    protected void checkOne(Activity ac, String permission, int code) {
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

    private void firstCheck(String permission,int code){
        if (!singlePermissionCheck(ac,permission)) {
            if(isRationaleStatus(permission)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ac, permission)) {
                    requireRationaleAsk(permission, code);
                } else {
                    requireSimpleAsk(permission, code);
                }
            }
            else{
                requireRationaleAsk(permission,code);
            }
        } else
            onGranted(code);
    }

    private void unknownPermissions(String permission){
        if(!singlePermissionCheck(ac,permission)){
            ac.requestPermissions(new String[]{permission},CODE_IGNORE);
        }
    }

    public class Request {

        public void camera() {
            firstCheck(Manifest.permission.CAMERA,CODE_CAMERA);
        }
        public void camera(int code) {
            firstCheck(Manifest.permission.CAMERA,code);
        }

        public void contacts() {
            firstCheck(Manifest.permission.WRITE_CONTACTS,CODE_CONTACT);
        }
        public void contacts(int code) {
            firstCheck(Manifest.permission.WRITE_CONTACTS,code);
        }

        public void location() { firstCheck(Manifest.permission.ACCESS_FINE_LOCATION,CODE_LOCATION); }
        public void location(int code) { firstCheck(Manifest.permission.ACCESS_FINE_LOCATION,code); }

        public void microphone() {
            firstCheck(Manifest.permission.RECORD_AUDIO,CODE_MICROPHONE);
        }
        public void microphone(int code) {
            firstCheck(Manifest.permission.RECORD_AUDIO,code);
        }

        public void phone() {
            unknownPermissions(Manifest.permission.READ_PHONE_STATE);
            firstCheck(Manifest.permission.CALL_PHONE, CODE_PHONE);
        }
        public void phone(int code) {
            unknownPermissions(Manifest.permission.READ_PHONE_STATE);
            firstCheck(Manifest.permission.CALL_PHONE, code);
        }

        public void sms() {
            firstCheck(Manifest.permission.SEND_SMS, CODE_SMS);
        }
        public void sms(int code) {
            firstCheck(Manifest.permission.SEND_SMS, code);
        }

        public void sensors() {
            firstCheck(Manifest.permission.BODY_SENSORS, CODE_SENSORS);
        }
        public void sensors(int code) {
            firstCheck(Manifest.permission.BODY_SENSORS, code);
        }

        public void storage() { firstCheck(Manifest.permission.WRITE_EXTERNAL_STORAGE, CODE_STORAGE); }
        public void storage(int code) { firstCheck(Manifest.permission.WRITE_EXTERNAL_STORAGE, code); }

        public void vpn() {
            firstCheck(Manifest.permission.BIND_VPN_SERVICE, CODE_VPN);
        }
        public void vpn(int code) {
            firstCheck(Manifest.permission.BIND_VPN_SERVICE, code);
        }
    }

    public static class Check {

        public static boolean camera(Context c) {
            return singlePermissionCheck(c, Manifest.permission.CAMERA);
        }

        public static boolean contacts(Context c) {
            return singlePermissionCheck(c, Manifest.permission.WRITE_CONTACTS);
        }

        public static boolean location(Context c) {
            return singlePermissionCheck(c, Manifest.permission.ACCESS_FINE_LOCATION);
        }

        public static boolean microphone(Context c) {
            return singlePermissionCheck(c, Manifest.permission.RECORD_AUDIO);
        }

        public static boolean sms(Context c) {
            return singlePermissionCheck(c, Manifest.permission.SEND_SMS);
        }

        public static boolean phone(Context c) {
            return singlePermissionCheck(c, Manifest.permission.CALL_PHONE);
        }

        public static boolean sensors(Context c) {
            return singlePermissionCheck(c, Manifest.permission.BODY_SENSORS);
        }

        public static boolean storage(Context c) {
            return singlePermissionCheck(c, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        public static boolean vpn(Context c) {
            return singlePermissionCheck(c, Manifest.permission.BIND_VPN_SERVICE);
        }

    }

    protected static void setShouldShowStatus(String permission, int set) {
        ProcessApp.getPref().edit().putInt(permission, set).apply();
    }
    protected static boolean isRationaleStatus(String permission) {
        return (ProcessApp.getPref().getInt(permission, -1)!=-1);
    }
    protected static boolean getRationaleStatus(String permission) {
        return (ProcessApp.getPref().getInt(permission, 1)==1);
    }

    /*

    private static int checkAndAskPermission(Activity ac, String permission){
        int t = ac.checkSelfPermission(permission);
        if (t != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ac, permission)) {
                return ASKED;
            } else {
                return DENIED;
            }
        }
        return GRANTED;
    }

    public static void checkAndAsk(Activity ac, String permission, String onAsked, String onDenied) {
        int t = checkAndAskPermission(ac,permission);
        if (t==ASKED) {
            ProcessApp.talk(ac.getApplicationContext(),onAsked);
        } else if(t==DENIED){
            ProcessApp.talk(ac.getApplicationContext(),onDenied);
            ac.startActivity(gotoSettings(ac.getPackageName()));
        }

    }
    public static void checkAndAsk(Activity ac, String permission, String onAsked, String onDenied, boolean close) {
        int t = checkAndAskPermission(ac,permission);
        if (t==ASKED) {
            ProcessApp.talk(ac.getApplicationContext(),onAsked);
            if (close)
                new Handler().postDelayed(ac::finishAffinity, 1000L);
        }
        else if(t==DENIED){
            ProcessApp.talk(ac.getApplicationContext(),onDenied);
            ac.startActivity(gotoSettings(ac.getPackageName()));
            if (close)
                new Handler().postDelayed(ac::finishAffinity, 1000L);
        }
    }

    public void displayNeverAskAgainDialog(Activity ac,String alert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage("We need to send SMS for performing necessary task. Please permit the permission through "
                + "Settings screen.\n\nSelect Permissions -> Enable permission");
        builder.setMessage(alert);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit Manually", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ac.startActivity(gotoSettings(ac.getPackageName()));
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

*/ // Extra Code for Future

    public static class Talking {

        public static String[] permissionList(int code){
            switch (code){
                case com.wasitech.permission.PermissionGroup.AUD_HEAD:{return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};}
                case com.wasitech.permission.PermissionGroup.BACKGROUND:
                case Permission.CODE_MICROPHONE:
                case com.wasitech.permission.PermissionGroup.COM_HEAD: {return new String[]{Manifest.permission.RECORD_AUDIO};}
                case com.wasitech.permission.PermissionGroup.TAKE_PIC:
                case com.wasitech.permission.PermissionGroup.CAM_HEAD: {return new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};}
                case Permission.CODE_CALENDER:{return new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR};}
                case com.wasitech.permission.PermissionGroup.CODE_CONTACT:
                case com.wasitech.permission.PermissionGroup.CONTACT_DIAL:
                case com.wasitech.permission.PermissionGroup.CONTACT_GET:
                case com.wasitech.permission.PermissionGroup.CONTACT_SHOW: {return new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS};}
                case Permission.CODE_CAMERA:
                case com.wasitech.permission.PermissionGroup.FLASH:{ return new String[]{Manifest.permission.CAMERA};}
                case com.wasitech.permission.PermissionGroup.MUSIC_FIND:
                case com.wasitech.permission.PermissionGroup.MUSIC_PLAY:
                case com.wasitech.permission.PermissionGroup.MUSIC_SHOW:
                case com.wasitech.permission.PermissionGroup.PIC_SHOW:
                case com.wasitech.permission.PermissionGroup.VIDEO_SHOW:
                case Permission.CODE_STORAGE: { return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};}
                case Permission.CODE_LOCATION: { return new String[]{Manifest.permission.ACCESS_FINE_LOCATION};}
                case Permission.CODE_PHONE: { return new String[]{Manifest.permission.CALL_PHONE};}
                case Permission.CODE_SENSORS: { return new String[]{Manifest.permission.BODY_SENSORS};}
                case com.wasitech.permission.PermissionGroup.SMS_SEND:
                case Permission.CODE_SMS: { return new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_CONTACTS};}
                case Permission.CODE_VPN: { return new String[]{Manifest.permission.BIND_VPN_SERVICE};}
                case com.wasitech.permission.PermissionGroup.FIND_PHONE:{return new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA};}
                default:{return new String[]{};}
            }
        }

        public static String whichNotGranted(Context c, int code) {
            ArrayList<String> list = new ArrayList<>();
            for (String per : permissionList(code)) {
                if (!singlePermissionCheck(c, per)) {
                    String name=getPermissionName(per);
                    if(!list.contains(name))
                        list.add(name);
                }
            }
            return permissionLine(list);
        }

        private static String getPermissionName(String per) {
            switch (per) {
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                case Manifest.permission.MANAGE_EXTERNAL_STORAGE: {
                    return "Storage";
                }
                case Manifest.permission.READ_CONTACTS:
                case Manifest.permission.WRITE_CONTACTS:
                case Manifest.permission.GET_ACCOUNTS: {
                    return "Contact";
                }
                case Manifest.permission.RECORD_AUDIO: {
                    return "Microphone";
                }
                case Manifest.permission.CAMERA: {
                    return "Camera";
                }
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                case Manifest.permission.ACCESS_FINE_LOCATION: {
                    return "Location";
                }
                case Manifest.permission.READ_SMS:
                case Manifest.permission.BROADCAST_SMS:
                case Manifest.permission.RECEIVE_SMS:
                case Manifest.permission.SEND_SMS: {
                    return "Sms";
                }
                case Manifest.permission.CALL_PHONE:
                case Manifest.permission.ANSWER_PHONE_CALLS:
                case Manifest.permission.READ_PHONE_NUMBERS:
                case Manifest.permission.READ_PRECISE_PHONE_STATE:
                case Manifest.permission.MODIFY_PHONE_STATE: {
                    return "Phone";
                }
                case Manifest.permission.BIND_VPN_SERVICE: {
                    return "VPN";
                }
                default:
                    return "";
            }
        }

        private static String permissionLine(ArrayList<String> list) {
            Collections.sort(list, String::compareTo);
            switch (list.size()) {
                case 0:
                    return "";
                case 1:
                    return list.get(0);
                case 2:
                    return list.get(0) + ", & " + list.get(1);
                default: {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        if (i == list.size() - 1)
                            builder.append("& ").append(builder.append(list.get(i)));
                        else
                            builder.append(list.get(i)).append(", ");
                    }
                    return builder.toString();
                }
            }
        }

    }
    public void displayRationale(String text,int code){
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit", (dialog, which) -> {
            dialog.dismiss();
            ac.requestPermissions(Talking.permissionList(code),code);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    public void displaySimple(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit", (dialog, which) -> {
            dialog.dismiss();
            ac.startActivity(gotoSettings(ac.getPackageName()));
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    public void displayRationale(String text, int code, boolean close){
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit", (dialog, which) -> {
            dialog.dismiss();
            ac.requestPermissions(Talking.permissionList(code),code);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            if(close)
                ac.finishAndRemoveTask();
        });
        builder.show();
    }
    public void displaySimple(String text,boolean close){
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("Permit", (dialog, which) -> {
            dialog.dismiss();
            ac.startActivity(gotoSettings(ac.getPackageName()));
            if(close)
                ac.finishAndRemoveTask();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            if(close)
                ac.finishAndRemoveTask();
        });
        builder.show();
    }
}
