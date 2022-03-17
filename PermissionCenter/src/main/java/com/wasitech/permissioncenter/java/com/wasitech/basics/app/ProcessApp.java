package com.wasitech.permissioncenter.java.com.wasitech.basics.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wasitech.assist.R;
import com.wasitech.assist.classes.App;
import com.wasitech.assist.runnables.AppRunnable;
import com.wasitech.assist.services_receivers.MyReceiver;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.contact.classes.Contact;
import com.wasitech.contact.runnable.ContactRunnable;
import com.wasitech.database.BaseMaker;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;
import com.wasitech.music.classes.Song;
import com.wasitech.music.runnable.AudioSongRunnable;
import com.wasitech.music.runnable.VideoSongRunnable;
import com.wasitech.permission.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class ProcessApp extends Application {
    private static FirebaseUser user;
    private static String uid;
    public static ArrayList<Contact> cList;
    public static ArrayList<Song> saList;
    public static ArrayList<Song> svList;
    public static ArrayList<App> aList;

    private static SharedPreferences pref;
    public static byte[] bytes;
    private static TextToSpeech text;
    private static int status;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessApp.pref = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            ProcessApp.user = user;
            ProcessApp.uid = user.getUid();
            ProcessApp.bytes = new LocalDB(getApplicationContext()).getPic(uid);
        }
    }

    public static void init(Context context) {
        if (ProcessApp.cList == null)
            ProcessApp.cList = new ArrayList<>();
        if (ProcessApp.aList == null)
            ProcessApp.aList = new ArrayList<>();
        if (ProcessApp.saList == null)
            ProcessApp.saList = new ArrayList<>();
        if (ProcessApp.svList == null)
            ProcessApp.svList = new ArrayList<>();
        new BaseMaker(context);

        new Thread(new AppRunnable(context), "Assist-App").start();
        if (Permission.Check.contacts(context)) {
            new Thread(new ContactRunnable(context, false), "Assist-CON").start();
        }
        if (Permission.Check.storage(context)) {
            new Thread(new AudioSongRunnable(context) {
                @Override
                public void onComplete() {

                }
            }, "Assist-AUD").start();
            new Thread(new VideoSongRunnable(context) {
                @Override
                public void onComplete() {

                }
            }, "Assist-VID").start();
        }
        try {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            BroadcastReceiver mReceiver = new MyReceiver();
            context.registerReceiver(mReceiver, intentFilter);
        } catch (Exception e) {
            Issue.print(e, ProcessApp.class.getName());
        }
    }

    private static void setVoice(Context context) {
        ProcessApp.text = new TextToSpeech(context, (status) -> {
            ProcessApp.status = status;
            ProcessApp.text.setLanguage(Locale.ENGLISH);
        }, "");
    }

    private static void setVoice(Context context, String s) {
        ProcessApp.text = new TextToSpeech(context, (status) -> {
            ProcessApp.status = status;
            ProcessApp.text.setLanguage(Locale.UK);
            ProcessApp.text.speak(s, TextToSpeech.QUEUE_ADD, null, null);
        }, "");
    }

    public static void talkAfterDelay(Context c, int delay, String t) {
        if (ProcessApp.text == null)
            setVoice(c);
        if (ProcessApp.status == TextToSpeech.SUCCESS) {
            ProcessApp.text.playSilentUtterance(delay, TextToSpeech.QUEUE_ADD, null);
            ProcessApp.text.speak(t, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            Basics.Log("err::" + t);
            Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public static void talkWithDelay(Context c, String t1, long delay, String t2) {
        if (ProcessApp.text == null)
            setVoice(c);
        if (ProcessApp.status == TextToSpeech.SUCCESS) {
            ProcessApp.text.speak(t1, TextToSpeech.QUEUE_ADD, null, null);
            ProcessApp.text.playSilentUtterance(delay, TextToSpeech.QUEUE_ADD, null);
            ProcessApp.text.speak(t2, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            Basics.Log("3-err::" + t1 + " " + t2);
            Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public static void talk(Context c, String t) {
        if (ProcessApp.text == null)
            setVoice(c);
        if (ProcessApp.status == TextToSpeech.SUCCESS) {
            ProcessApp.text.speak(t, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            setVoice(c, t);
            //Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public static void stopTalk() {
        if (ProcessApp.text != null && ProcessApp.text.isSpeaking())
            ProcessApp.text.stop();
    }

    public static boolean isFirstInstall(Context context) {
        try {
            long firstInstallTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
            return firstInstallTime == lastUpdateTime;
        } catch (Exception e) {
            Issue.print(e, ProcessApp.class.getName());
            return true;
        }
    }

    public static boolean isInstallFromUpdate(Context context) {
        try {
            long firstInstallTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
            return firstInstallTime != lastUpdateTime;
        } catch (Exception e) {
            Issue.print(e, ProcessApp.class.getName());
            return false;
        }
    }

    public static FirebaseUser getUser() {
        if (ProcessApp.user == null) {
            ProcessApp.user = FirebaseAuth.getInstance().getCurrentUser();
        }
        return ProcessApp.user;
    }

    public static FirebaseUser getCurUser() {
        ProcessApp.user = FirebaseAuth.getInstance().getCurrentUser();
        return ProcessApp.user;
    }

    public static void signOut() {
        ProcessApp.user = null;
        ProcessApp.bytes = null;
        ProcessApp.cList = null;
        ProcessApp.saList = null;
        ProcessApp.svList = null;
        ProcessApp.aList = null;
        FirebaseAuth.getInstance().signOut();
    }

    public static SharedPreferences getPref() {
        return ProcessApp.pref;
    }

    public static int checkContact(String str) {
        if (ProcessApp.cList != null)
            for (Contact c : ProcessApp.cList)
                if (c.getName().equalsIgnoreCase(str))
                    return ProcessApp.cList.indexOf(c);
        return -1;
    }

    public static void setUid() {
        if (ProcessApp.user != null)
            ProcessApp.uid = ProcessApp.user.getUid();
        else {
            ProcessApp.user = ProcessApp.getCurUser();
            if (ProcessApp.user != null) {
                ProcessApp.uid = ProcessApp.user.getUid();
            }
        }
    }

    public static String getUid() {
        if (ProcessApp.uid != null)
            return ProcessApp.uid;
        else {
            setUid();
            if (ProcessApp.uid == null) {
                return "UNKNOWN";
            } else {
                return ProcessApp.uid;
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            ProcessApp.cList = null;
            ProcessApp.aList = null;
            ProcessApp.saList = null;
            ProcessApp.svList = null;
            ProcessApp.pref = null;
            ProcessApp.user = null;
            deleteDir(getCacheDir());
        } catch (Exception e) {
            Issue.print(e, ProcessApp.class.getName());
        }
    }

    public static boolean deleteDir(File dir) {
        try {
            if (dir != null && dir.isDirectory()) {
                String[] children = dir.list();
                if (children != null) {
                    for (String child : children) {
                        if (!deleteDir(new File(dir, child))) {
                            return false;
                        }
                    }
                }
                return dir.delete();
            } else if (dir != null && dir.isFile()) {
                return dir.delete();
            } else {
                return false;
            }
        } catch (Exception e) {
            Issue.print(e, ProcessApp.class.getName());
            return false;
        }
    }

    public static boolean isDarkTheme() {
        return ProcessApp.pref.getBoolean(Params.THEME, false);
    }

    public static void setAppTheme(boolean state) {
        ProcessApp.pref.edit().putBoolean(Params.THEME, state).apply();
    }

    public static int getPic() {
        if (isDarkTheme())
            switch (ProcessApp.pref.getInt(Params.ICON, 1)) {
                default:
                case 1: {
                    return R.drawable.logo_text_l_assist;
                }
                case 2: {
                    return R.drawable.logo_text_l_device;
                }
                case 3: {
                    return R.drawable.logo_text_l_nasreen;
                }
                case 4: {
                    return R.drawable.logo_text_l_masha;
                }
                case 5: {
                    return R.drawable.logo_text_l_zayan;
                }
                case 6: {
                    return R.drawable.logo_text_l_daisy;
                }
                case 7: {
                    return R.drawable.logo_text_l_kaali;
                }
            }
        else
            switch (ProcessApp.pref.getInt(Params.ICON, 1)) {
                default:
                case 1: {
                    return R.drawable.logo_text_d_assist;
                }
                case 2: {
                    return R.drawable.logo_text_d_device;
                }
                case 3: {
                    return R.drawable.logo_text_d_nasreen;
                }
                case 4: {
                    return R.drawable.logo_text_d_masha;
                }
                case 5: {
                    return R.drawable.logo_text_d_zayan;
                }
                case 6: {
                    return R.drawable.logo_text_d_daisy;
                }
                case 7: {
                    return R.drawable.logo_text_d_kaali;
                }
            }

    }

}
