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

import java.util.ArrayList;
import java.util.Collections;

import static com.wasitech.permissioncenter.Permission.isPermissionGranted;

public class Params {
    public static final int AUDIO_RECORDING_APP = 301; // RECORD_AUDIO + WRITE_STORAGE
    public static final int AUDIO_LISTEN_APP = 302; // RECORD_AUDIO
    public static final int CAMERA_APP = 303;  // CAMERA + WRITE_STORAGE
    public static final int CONTACT_APP = 304; // READ_WRITE_CONTACT
    public static final int FLASH_APP = 305;  // CAMERA
    public static final int GALLERY_APP = 306;  // READ_STORAGE
    public static final int MUSIC_PLAYER_APP = 307;// READ_STORAGE
    public static final int PHONE_APP = 308;// READ_STORAGE
    public static final int SMS_APP = 309; // SEND_SMS + READ_CONTACT


    public static class AppPermission {
        public static String[] permissionList(int code) {
            switch (code) {
                case Params.PHONE_APP:
                case GroupPermission.CODE_GROUP_PHONE: {
                    return new String[]{Manifest.permission.WRITE_CALL_LOG
                            , Manifest.permission.READ_PHONE_STATE
                            , Manifest.permission.CALL_PHONE
                            , Manifest.permission.READ_CALL_LOG
                    };
                }

                case Params.SMS_APP:
                case GroupPermission.CODE_GROUP_SMS: {
                    return new String[]{Manifest.permission.SEND_SMS
                            , Manifest.permission.READ_SMS
                    };
                }

                case GroupPermission.CODE_GROUP_LOCATION: {
                    return new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION
                    };
                }

                case Params.CONTACT_APP:
                case GroupPermission.CODE_GROUP_CONTACT: {
                    return new String[]{Manifest.permission.READ_CONTACTS
                            , Manifest.permission.WRITE_CONTACTS
                            , Manifest.permission.GET_ACCOUNTS
                    };
                }

                case GroupPermission.CODE_GROUP_CALENDER: {
                    return new String[]{Manifest.permission.WRITE_CALENDAR
                            , Manifest.permission.WRITE_CALENDAR
                    };
                }

                case Params.MUSIC_PLAYER_APP:
                case Params.GALLERY_APP:
                case GroupPermission.CODE_GROUP_STORAGE: {
                    return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.READ_EXTERNAL_STORAGE
                    };
                }

                case GroupPermission.CODE_GROUP_CAMERA:
                case Params.FLASH_APP:
                case Permission.CODE_SINGLE_CAMERA: {
                    return new String[]{Manifest.permission.CAMERA};
                }

                case GroupPermission.CODE_GROUP_MICROPHONE:
                case Permission.CODE_SINGLE_MICROPHONE:
                case Params.AUDIO_LISTEN_APP: {
                    return new String[]{Manifest.permission.RECORD_AUDIO};
                }

                case Permission.CODE_SINGLE_PHONE: {
                    return new String[]{Manifest.permission.CALL_PHONE};
                }

                case Permission.CODE_SINGLE_SMS: {
                    return new String[]{Manifest.permission.SEND_SMS};
                }

                case Permission.CODE_SINGLE_LOCATION: {
                    return new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                }

                case Permission.CODE_SINGLE_CONTACT: {
                    return new String[]{Manifest.permission.WRITE_CONTACTS};
                }

                case Permission.CODE_SINGLE_CALENDER: {
                    return new String[]{Manifest.permission.WRITE_CALENDAR};
                }

                case Permission.CODE_SINGLE_STORAGE: {
                    return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                }

                case Params.AUDIO_RECORDING_APP: {
                    return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
                }

                case Params.CAMERA_APP: {
                    return new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                }

                default: {
                    return new String[]{};
                }
            }
        }

        public static String whichNotGranted(Context c, int code) {
            ArrayList<String> list = new ArrayList<>();
            for (String per : permissionList(code)) {
                if (!isPermissionGranted(c, per)) {
                    String name = getPermissionName(per);
                    if (!list.contains(name))
                        list.add(name);
                }
            }
            return permissionLine(list);
        }
        public static String whichNotGranted(Context c, ArrayList<String> permissions) {
            ArrayList<String> list = new ArrayList<>();
            for (String per : permissions) {
                if (!isPermissionGranted(c, per)) {
                    String name = getPermissionName(per);
                    if (!list.contains(name))
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

    public static class Intents{
        public static Intent gotoSettings(String pkg) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", pkg, null);
            intent.setData(uri);
            return intent;
        }

        public static Intent gotoDrawOver(String pkg) {
            return new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + pkg));
        }
    }
    // storage

    private void requestPermission(Activity context, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", context.getApplicationContext().getPackageName())));
                context.startActivityForResult(intent, code);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                context.startActivityForResult(intent, code);//2296
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, code);
        }
    }

    public static void askReadStorage(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
    }

    public static void askWriteStorage(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    public static void askReadWriteStorage(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
    }

    public static boolean isNotReadWriteSms(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED;
    }

    public static void askReadWriteSms(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS}, requestCode);
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
