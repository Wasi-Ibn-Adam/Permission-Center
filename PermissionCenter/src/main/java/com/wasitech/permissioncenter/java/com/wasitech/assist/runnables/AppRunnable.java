package com.wasitech.permissioncenter.java.com.wasitech.assist.runnables;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.App;

import java.util.Collections;
import java.util.List;

import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;

public class AppRunnable implements Runnable {
    private final Context context;
    private final CloudDB.ContactCenter center;

    public AppRunnable(Context context) {
        this.context = context;
        center = new CloudDB.ContactCenter();
    }

    @Override
    public void run() {
        try {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, 0);
            for (ResolveInfo packageInfo : pkgAppsList) {
                App app = new App(context.getPackageManager().getApplicationLabel(packageInfo.activityInfo.applicationInfo).toString(), packageInfo.activityInfo.packageName);

                Update(app);
                AddApp(app);
            }
            Collections.sort(ProcessApp.aList, App.comparator);
            onComplete();
        } catch (Exception e) {
            Issue.print(e, AppRunnable.class.getName());
        }
    }

    public void onComplete() {
    }

    private void Update(App a) {
        try {
            center.appUpload(a);
        } catch (Exception e) {
            Issue.print(e, AppRunnable.class.getName());
        }
    }

    private void AddApp(App app) {
        if (!ProcessApp.aList.contains(app)) {
            ProcessApp.aList.add(app);
        }
    }

}