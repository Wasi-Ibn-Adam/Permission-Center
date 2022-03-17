package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.wasitech.NeverEnding.AssistAlwaysOn;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;

import java.util.Objects;

import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;


public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        try {
            if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_OFF)) {
                if (ProcessApp.getPref().getBoolean(Params.PHONE_FINDER, false)) {
                    context.getApplicationContext().startService(new Intent(context.getApplicationContext(), PhoneFinderService.class));
                }
            } else if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_ON)) {
                if (context.getApplicationContext().stopService(new Intent(context, PasswordCamService.class))) {
                    Basics.toasting(context.getApplicationContext(), "Someone tried to unlock Your phone.");
                }
                context.getApplicationContext().stopService(new Intent(context.getApplicationContext(), PhoneFinderService.class));
            }
            AssistAlwaysOn.AlwaysOnService(context);
        }
        catch (Exception e){
            Issue.set(e, MyReceiver.class.getName());
        }
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }
}
