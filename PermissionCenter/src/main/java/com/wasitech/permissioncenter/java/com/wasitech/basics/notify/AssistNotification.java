package com.wasitech.permissioncenter.java.com.wasitech.basics.notify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.core.content.ContextCompat;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;

public abstract class AssistNotification {

    protected final Context context;
    protected final NotificationManager manager;
    public AssistNotification(Context context){
        this.context = context;
        manager = context.getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotiChannel(channelId(), importance());
        }
    }

    public NotificationManager getManager() {
        return manager;
    }

    protected abstract int importance();
    protected abstract String channelId();
    private void NotiChannel(String channel_id, int importance) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id.toLowerCase(), channel_id, importance);
            manager.createNotificationChannel(channel);
        }
    }
    public Bitmap randomPics(int num) {
        if (num % 2 == 0) {
            return Basics.Img.parseBitmap(ContextCompat.getDrawable(context, R.drawable.bg7));
        }
        return Basics.Img.parseBitmap(ContextCompat.getDrawable(context, R.drawable.bg8));
    }

}
