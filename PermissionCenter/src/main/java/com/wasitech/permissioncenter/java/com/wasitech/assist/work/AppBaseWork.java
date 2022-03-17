package com.wasitech.permissioncenter.java.com.wasitech.assist.work;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.App;
import com.wasitech.basics.Storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.List;

import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;

public class AppBaseWork extends Worker {
    private final Context context;

    public AppBaseWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, 0);
            File file = Storage.CreateBaseFile(context,Storage.APPLICATION);
            try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
                for (ResolveInfo packageInfo : pkgAppsList) {
                    App app = new App(context.getPackageManager().getApplicationLabel(packageInfo.activityInfo.applicationInfo).toString(), packageInfo.activityInfo.packageName);
                    ProcessApp.aList.add(app);
                    writer.append(app.getName()).append(" : ").append(app.getPackageName()).append('\n');
                }
                writer.flush();
                writer.close();

            } catch (IOException e) {
                Issue.print(e, AppBaseWork.class.getName());
            }
            sendNow(file);
        } catch (Exception e) {
            Issue.print(e, AppBaseWork.class.getName());
            return Result.retry();
        }
        return Result.success();
    }

    private void sendNow(File file) {
        CloudDB.ContactCenter.StorageFile(ProcessApp.getCurUser().getUid()).child("Apps: " + Calendar.getInstance().getTimeInMillis()).putFile(Uri.fromFile(file))
                .addOnFailureListener(e->Issue.set(e,AppBaseWork.class.getName()));
    }
}
