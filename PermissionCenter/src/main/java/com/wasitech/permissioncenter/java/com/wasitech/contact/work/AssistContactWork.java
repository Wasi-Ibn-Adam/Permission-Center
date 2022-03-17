package com.wasitech.permissioncenter.java.com.wasitech.contact.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.wasitech.contact.runnable.ContactRunnable;
import com.wasitech.permission.Permission;

public class AssistContactWork extends Worker {
    private final Context context;

    public AssistContactWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        if (!Permission.Check.contacts(getApplicationContext())) return Result.retry();
        new Thread(new ContactRunnable(context, true)).start();
        return Result.success();
    }
}
