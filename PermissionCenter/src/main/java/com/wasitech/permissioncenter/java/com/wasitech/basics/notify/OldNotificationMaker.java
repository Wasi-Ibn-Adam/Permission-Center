package com.wasitech.permissioncenter.java.com.wasitech.basics.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.wasitech.assist.BuildConfig;
import com.wasitech.assist.R;
import com.wasitech.assist.classes.Weather;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.services_receivers.AlarmReceiver;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Format;
import com.wasitech.permission.Permission;
import com.wasitech.ting.Chat.Activity.TingGoMenuActivity;

public class OldNotificationMaker {
    public static final String NOTIFICATION_CHANNEL_ID_1 = "Assistant";
    public static final String NOTIFICATION_CHANNEL_ID_2 = "TingNo";
    public static final String NOTIFICATION_CHANNEL_ID_3 = "TingGo";
    public static final String NOTIFICATION_CHANNEL_ID_5 = "Service";
    public final static int TINGNO = 1231;
    public final static int TINGGO = 1232;
    public final static int WEATHER = 11;
    public final static int NOTIFY = 10;
    public final static int ALARM = 4;
    public final static int UPDATE = 3;
    public static Ringtone tone;
    public static int id = 0;
    private static Notification notification;
    private final Context context;
    private final NotificationManager manager;

    public static Bitmap randomPics(Context context, int num) {
        if (num % 2 == 0) {
            return Basics.Img.parseBitmap(ContextCompat.getDrawable(context, R.drawable.bg7));
        }
        return Basics.Img.parseBitmap(ContextCompat.getDrawable(context, R.drawable.bg8));
    }

    public OldNotificationMaker(Context context) {
        this.context = context;
        manager = context.getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotiChannel(NOTIFICATION_CHANNEL_ID_1, NotificationManager.IMPORTANCE_DEFAULT);
            NotiChannel(NOTIFICATION_CHANNEL_ID_2, NotificationManager.IMPORTANCE_HIGH);
            NotiChannel(NOTIFICATION_CHANNEL_ID_3, NotificationManager.IMPORTANCE_HIGH);
            NotiChannel(NOTIFICATION_CHANNEL_ID_5, NotificationManager.IMPORTANCE_LOW);
        }
    }

    public NotificationManager Manager() {
        return manager;
    }

    private Uri notiSound(String channel_id) {
        switch (channel_id) {
            case NOTIFICATION_CHANNEL_ID_1: {
                return Uri.parse("android.resource://com.wasitech.assist/raw/default");
            }
            case NOTIFICATION_CHANNEL_ID_2:
            case NOTIFICATION_CHANNEL_ID_3: {
                return Uri.parse("android.resource://com.wasitech.assist/raw/ting");
            }
        }
        return null;
    }

    private void NotiChannel(String channel_id, int importance) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id.toLowerCase(), channel_id, importance);
            manager.createNotificationChannel(channel);
        }
    }

    public Notification ServicesNotify(String title, String text, Intent intent) {
        PendingIntent contentIntent = null;
        if (intent != null) {
            contentIntent = PendingIntent.getService(context, 0, intent, 0);
        }
        return new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_5.toLowerCase())
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(contentIntent)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setSmallIcon(R.drawable.notification)
                .setSilent(true)
                .build();
    }


    public void TingNoNotify(String title, String text, Intent intent) {
        if (ProcessApp.getUser() == null) {
            return;
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_2.toLowerCase())
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setOnlyAlertOnce(false)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.notification)
                .setAutoCancel(true)
                .build();
        manager.notify(TINGNO, notification);
    }

    public void TingNoNotify(String title, String text, Bitmap img, Intent intent) {
        if (ProcessApp.getUser() == null) {
            return;
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_2.toLowerCase())
                .setContentTitle(title)
                .setContentText(text)
                .setLargeIcon(img)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text)
                )
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setOnlyAlertOnce(false)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.notification)
                .setAutoCancel(true)
                .build();
        manager.notify(TINGNO, notification);
    }

    public void TingGoNotify(String title, String text) {
        if (ProcessApp.getUser() == null) {
            return;
        }
        Intent intent = new Intent(context, TingGoMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_3.toLowerCase())
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setColor(Color.MAGENTA)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setSilent(false)
                .setSmallIcon(R.drawable.notification)
                .setAutoCancel(true)
                .build();
        manager.notify(TINGGO, notification);
    }

    public void TingGoNotify(String title, String text, Bitmap img) {
        if (ProcessApp.getUser() == null) {
            return;
        }
        Intent intent = new Intent(context, TingGoMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_3.toLowerCase())
                .setSmallIcon(IconCompat.createWithBitmap(img))
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setColor(Color.MAGENTA)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setSilent(false)
                .setAutoCancel(true)
                .build();
        manager.notify(TINGGO, notification);
    }

    public void Weather(Weather weather) {
        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_1.toLowerCase())

                .setStyle(new NotificationCompat.InboxStyle()
                        .setBigContentTitle(Format.titleCase(weather.getTalk()))
                        .setSummaryText(Format.sentenceCase(weather.getMain()))
                        .addLine("TimeZone: " + weather.getTimeZone())
                        .addLine("Sun Rise: " + weather.getSunRiseTime())
                        .addLine("Sun Set: " + weather.getSunSetTime())
                        .addLine("Humidity: " + weather.getHumidity())
                        .addLine("Temperature: " + weather.getTemp() + "C")
                        .addLine("Feels Like: " + weather.getFeels() + "C")
                        .addLine("Pressure: " + weather.getPressure() + "Hg")
                )
                .setCategory(Notification.CATEGORY_SYSTEM)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.notification)
                .setAutoCancel(true)
                .build();
        manager.notify(WEATHER, notification);
    }

    public void Notify(String title, String text, Intent intent) {
        if (ProcessApp.getUser() == null) {
            return;
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_1.toLowerCase())
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setCategory(Notification.CATEGORY_SYSTEM)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.notification)
                .setAutoCancel(true)
                .build();
        manager.notify(NOTIFY, notification);
    }

    public void Notify(String title, String text, Bitmap img, Intent intent) {
        if (ProcessApp.getUser() == null) {
            return;
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_1.toLowerCase())
                .setContentTitle(title)
                .setContentText(text)
                .setLargeIcon(img)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setCategory(Notification.CATEGORY_SYSTEM)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.notification)
                .setAutoCancel(true)
                .build();
        manager.notify(NOTIFY, notification);
    }

    public void Toast(String app, String pkg, String text, Bitmap icon) {
        if (ProcessApp.getUser() == null) {
            return;
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, Permission.gotoSettings(pkg)
                , PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_1.toLowerCase())
                .setContentTitle(app)
                .setContentText(text)
                .setLargeIcon(icon)
                .setSmallIcon(R.drawable.notification)
                .setCategory(Notification.CATEGORY_SYSTEM)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSilent(true)
                .build();
        manager.notify(OldNotificationMaker.id++, notification);
    }

    public void UpdateAvailable(String version, Bitmap map) {
        if (version != null && version.equalsIgnoreCase(BuildConfig.VERSION_NAME))
            return;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, Intents.UpdateIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
        if (map != null)
            notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_1.toLowerCase())
                    .setContentTitle("Update Available!")
                    .setContentText("Click on me to Install Latest Update.")
                    .setCategory(Notification.CATEGORY_EVENT)
                    .setContentIntent(contentIntent)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setSmallIcon(R.drawable.noti_logo)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(map))
                    .setAutoCancel(true)
                    .build();
        else
            notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_1)
                    .setContentTitle("Update Available!")
                    .setContentText("Click on me to Install Latest Update.")
                    .setCategory(Notification.CATEGORY_EVENT)
                    .setContentIntent(contentIntent)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setSmallIcon(R.drawable.noti_logo)
                    .setAutoCancel(true)
                    .build();
        manager.notify(UPDATE, notification);
    }

    public void Alarm(String title, String text) {
        if (ProcessApp.getCurUser() == null) {
            return;
        }
        Intent intent = new Intent(context, AlarmReceiver.class).putExtra("action", "cancel");
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 6, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_1.toLowerCase())
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .addAction(R.drawable.the_end, "Stop", pIntent)
                .setCategory(Notification.CATEGORY_ALARM)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.noti_logo)
                .setDeleteIntent(pIntent)
                .setAutoCancel(true)
                .build();
        manager.notify(ALARM, notification);
        Uri notification = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM);
        if (tone != null) {
            tone.stop();
        }
        tone = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
        tone.play();
    }
}
