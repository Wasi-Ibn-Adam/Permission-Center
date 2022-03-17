package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.google.firebase.auth.FirebaseAuth;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.Modes;

import com.wasitech.database.ContactBase;
import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

public class NotificationService extends NotificationListenerService {
    private SharedPreferences prefs;
    private LocalDB db;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        db = new LocalDB(getApplicationContext());
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        try {
            String pkg = sbn.getPackageName();
            Bundle extras = sbn.getNotification().extras;
            String title = extras.getString("android.title");
            String text = extras.getString("android.text");
            long time = sbn.getPostTime();

//            Basics.Log(pkg+" "+title+" "+text);

            //Firebase.setExactLocation(getApplicationContext());

           //if(extras.containsKey(Notification.EXTRA_PICTURE)){
           //    Bitmap b=(Bitmap)sbn.getNotification().extras.get(Notification.EXTRA_PICTURE);
           //    if(b!=null)
           //        Basics.saveBitmap(b);
           //}

            if (title != null) {
                int num = sbn.getNotification().number;
                if (num >= 1) {
                    CharSequence[] list = extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);
                    if (list != null && list.length >= num - 1)
                        text = list[num - 1].toString();
                }
                if (text == null)
                    text = " ";
                if (timeChecker(db.getNotiTime(pkg, title, text), time)) {
                    db.addNotification(pkg, title, text, time);
                    String cat = sbn.getNotification().category;
                    if (cat == null || cat.length() < 1) cat = "default";
                    switch (cat) {
                        case Notification.CATEGORY_CALL: {
                            callFun(pkg, title, text, num);
                            break;
                        }
                        case Notification.CATEGORY_ALARM: {
                            alarmFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_EMAIL: {
                            emailFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_ERROR: {
                            errorFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_EVENT: {
                            eventFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_MESSAGE: {
                            msgFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_PROGRESS: {
                            progressFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_NAVIGATION: {
                            navigateFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_PROMO: {
                            promoFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_RECOMMENDATION: {
                            recomandFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_REMINDER: {
                            reminderFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_SERVICE: {
                            serviceFun(db, pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_SOCIAL: {
                            socialFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_STATUS: {
                            statusFun(pkg, title, text);
                            break;
                        }
                        case Notification.CATEGORY_TRANSPORT: {
                            transportFun(pkg, title, text);
                            break;
                        }
                        case Params.TINGNO: {
                            tingFun(pkg, title, text);
                            break;
                        }
                        default: {
                            DefaultFun(pkg, title, text, num, sbn.isOngoing());
                        }
                    }
                }
            }
        } catch (Exception e) {
            Issue.print(e, NotificationService.class.getName());
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        String pkg = sbn.getPackageName();
        if (prefs.getBoolean(Params.NOTI_READ, false) && prefs.getBoolean(pkg, false)) {
            Bundle extras = sbn.getNotification().extras;
            assert extras != null;
            String title = extras.getString("android.title");
            String text = extras.getString("android.text");
            if (title != null) {
                int num = sbn.getNotification().number;
                if (num >= 1) {
                    CharSequence[] list = extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);
                    if (list != null && list.length >= num - 1)
                        text = list[num - 1].toString();
                }
                if (text == null) text = " ";
                String cat = sbn.getNotification().category;
                if (cat == null || cat.length() < 1) cat = "default";
                switch (cat) {
                    case Notification.CATEGORY_CALL: {
                        if (number != null && name != null) {
                            Basics.Log("enter");
                            ContactBase.Updater2(name, number);
                            number = null;
                            name = null;
                        }
                        db.deleteNotification(pkg);
                        break;
                    }
                    case Notification.CATEGORY_EMAIL: {
                        db.deleteNotification(pkg);
                        break;
                    }
                    case Notification.CATEGORY_MESSAGE: {
                        db.deleteNotification(pkg, title, text);
                        break;
                    }
                    default: {
                        db.deleteNotification(pkg, title, text);
                    }
                }
            }
        }
    }

    private void promoFun(String pkg, String title, String text) {
        // Basics.Log("Promo-> "+"title"+"  "+"text"+" "+pkg+"  ");
    }

    private void transportFun(String pkg, String title, String text) {
        // Basics.Log("Promo-> "+"title"+"  "+"text"+" "+pkg+"  ");
    }

    private void statusFun(String pkg, String title, String text) {
        // Basics.Log("Promo-> "+"title"+"  "+"text"+" "+pkg+"  ");
    }

    private void recomandFun(String pkg, String title, String text) {
        // Basics.Log("Promo-> "+"title"+"  "+"text"+" "+pkg+"  ");
    }

    private void reminderFun(String pkg, String title, String text) {
        // Basics.Log("Promo-> "+"title"+"  "+"text"+" "+pkg+"  ");
    }

    private void navigateFun(String pkg, String title, String text) {
        // Basics.Log("Promo-> "+"title"+"  "+"text"+" "+pkg+"  ");
    }

    private void DefaultFun(String pkg, String title, String text, int num, boolean ongoing) {
        try {
            //Basics.Log("Default-> "+title+"  "+text+" "+pkg+"  "+num);
            if (text != null) {
                if (!(title.contains(")") || title.contains("(") || title.contains("@"))) {
                    if (!ongoing) {
                        try {
                            setValue(pkg, title);
                        } catch (PackageManager.NameNotFoundException e) {
                            Issue.print(e, NotificationService.class.getName());
                        }
                    }
                }
            }
        }
        catch (Exception e){
            Issue.print(e, NotificationService.class.getName());
        }

    }

    private void tingFun(String pkg, String title, String text) {
        // Basics.Log("Ting-> "+"title"+"  "+"text"+" "+pkg+"  ");
        talk(pkg, "Ting from Back-end Team.");
    }

    private void socialFun(String pkg, String title, String text) {
        // Basics.Log("Social->"+"title"+"  "+"text"+" "+pkg+"  ");
    }

    private void progressFun(String pkg, String title, String text) {
        //  Basics.Log("Progress-> "+"title"+"  "+"text"+" "+pkg+"  ");
    }

    private void msgFun(String pkg, String title, String text) {
        try{
            if (Basics.onlyNumbers(ContactBase.coNum(title))) {
                title = "a number";
            }
            String app = appName(pkg);
            if (app != null) {
                if (app.toLowerCase().trim().equals("whatsapp")) {
                    if (title.equalsIgnoreCase("you")) {
                        talk(pkg, "Reply Sent.");
                    } else if (title.trim().equalsIgnoreCase("whatsapp")) {
                        return;
                    }
                }
                talk(pkg, "Message from " + title + " on " + app);
            } else talk(pkg, "Message from " + title);
        }
        catch (Exception e){
            Issue.print(e, NotificationService.class.getName());
        }
    }

    private void eventFun(String pkg, String title, String text) {
        // Basics.Log("Event-> "+title+"  "+text+" "+pkg+"  ");
    }

    private void errorFun(String pkg, String title, String text) {
        //Basics.Log("Error-> "+title+"  "+text+" "+pkg+"  ");
    }

    private void emailFun(String pkg, String title, String text) {
        //  Basics.Log("Email-> "+"title"+"  "+"text"+" "+pkg+"  ");
        String app = appName(pkg);
        if (app != null)
            talk(pkg, "Email from " + title + " on " + app);
        else
            talk(pkg, "Email from " + title);
    }

    private void alarmFun(String pkg, String title, String text) {
        //Basics.Log("Alarm-> "+"title"+"  "+"text"+" "+pkg+"  ");
    }

    static String number = null, name = null;

    private void callFun(String pkg, String title, String text, int num) {
        //Basics.Log("Call-> "+"title"+"  "+"text"+" "+pkg+"  ");
        String coNum = ContactBase.coNum(title);
        if (coNum == null || Basics.onlyNumbers(coNum)) {
            number = title;
            title = "a number";
        } else {
            if (number != null) {
                name = title;
            }
        }
        if (text != null) {
            if ((text.toLowerCase().contains("call") && text.toLowerCase().contains("incoming"))) {
                if (num <= 1)
                    talk(pkg, title + " " + text);
            } else if ((text.toLowerCase().contains("calling") || text.toLowerCase().contains("dialing") || text.toLowerCase().contains("ringing"))) {
                if (num <= 1)
                    talk(pkg, text + " " + title);
            } else {
                if (num <= 1)
                    talk(pkg, "Calling " + title);
            }
        } else {
            if (num <= 1)
                talk(pkg, "Calling " + title);
        }
    }

    private static void serviceFun(LocalDB db, String pkg, String title, String text) {
        //Basics.Log("Service-> "+"title"+"  "+"text"+" "+pkg+"  ");
        if (title.toLowerCase().contains("updating"))
            db.deleteNotification(pkg);
    }

    private boolean timeChecker(long dbTime, long curr) {
        if (dbTime != 0) {
            long dif = curr - dbTime;
            return !(dif <= 15 && dif >= -15);
        }
        return true;
    }

    public void setValue(String pkgName, String title) throws PackageManager.NameNotFoundException {
        if (pkgName.equalsIgnoreCase("com.facebook.orca")) {
            if (!title.equalsIgnoreCase("chat heads active")) {
                talk(pkgName, "Message from " + title + " on Messenger.");
            }
        }
        else if (pkgName.equalsIgnoreCase("com.facebook.katana")) {
            talk(pkgName, "Notification from Facebook.");
        }
        else if (pkgName.equalsIgnoreCase("com.aero") || pkgName.equalsIgnoreCase("com.whatsapp") || pkgName.equalsIgnoreCase("com.whatsapp.w4b") || pkgName.equalsIgnoreCase("com.gbwhatsapp")) {
            if (!title.equalsIgnoreCase("whatsapp")) {
                if (Basics.onlyNumbers(ContactBase.coNum(title))) {
                    talk(pkgName, "message on whatsapp.");
                } else if (title.equalsIgnoreCase("you")) {
                    talk(pkgName, "Reply Sent.");
                } else {
                    talk(pkgName, title + "s message on whatsapp.");
                }
            }
        }
        else {
            String app = (String) getPackageManager().getApplicationLabel(getPackageManager().getApplicationInfo(pkgName, PackageManager.GET_META_DATA));
            if (title.length() < 15 && !app.equals(title)) {
                talk(pkgName, "Notification from " + app + " " + title);
            } else {
                talk(pkgName, "Notification from, " + app + ".");
            }
        }
    }

    private void talk(String pkg, String talk) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            if (prefs.getBoolean(Params.NOTI_READ, false) && prefs.getBoolean(pkg, false)) {
                new Modes(getApplicationContext()).notiVolume(0, 1000);
                ProcessApp.talk(getApplicationContext(), talk);
            }
    }

    private String appName(String pkg) {
        String app = null;
        try {
            app = (String) getPackageManager().getApplicationLabel(getPackageManager().getApplicationInfo(pkg, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            Issue.print(e, NotificationService.class.getName());
        }
        return app;
    }

    @Override
    public void onNotificationChannelGroupModified(String pkg, UserHandle user, NotificationChannelGroup group, int modificationType) {
        super.onNotificationChannelGroupModified(pkg, user, group, modificationType);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.deleteNotification();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationPosted(sbn, rankingMap);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationRemoved(sbn, rankingMap);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        super.onNotificationRemoved(sbn, rankingMap, reason);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new MyReceiver();
        registerReceiver(mReceiver, intentFilter);
        try {
           // Firebase.setValueOnce(Params.NOTIFICATION_LISTENER, "ON");
            //Firebase.setLastLocation(getApplicationContext());
        }catch (Exception e){
            Issue.print(e, NotificationService.class.getName());
        }
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        //Firebase.setValueOnce(Params.NOTIFICATION_LISTENER, "OFF");
        //Firebase.setExactLocation(getApplicationContext());
    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
    }

    @Override
    public void onListenerHintsChanged(int hints) {
        super.onListenerHintsChanged(hints);
    }

    @Override
    public void onSilentStatusBarIconsVisibilityChanged(boolean hideSilentStatusIcons) {
        super.onSilentStatusBarIconsVisibilityChanged(hideSilentStatusIcons);
    }

    @Override
    public void onNotificationChannelModified(String pkg, UserHandle user, NotificationChannel channel, int modificationType) {
        super.onNotificationChannelModified(pkg, user, channel, modificationType);
    }

    @Override
    public void onInterruptionFilterChanged(int interruptionFilter) {
        super.onInterruptionFilterChanged(interruptionFilter);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public StatusBarNotification[] getActiveNotifications() {
        return super.getActiveNotifications();
    }

    @Override
    public StatusBarNotification[] getActiveNotifications(String[] keys) {
        return super.getActiveNotifications(keys);
    }

    @Override
    public RankingMap getCurrentRanking() {
        return super.getCurrentRanking();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

}

