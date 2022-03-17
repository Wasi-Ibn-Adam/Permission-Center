package com.wasitech.permissioncenter.java.com.wasitech.NeverEnding;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.wasitech.basics.classes.Basics;

import com.wasitech.basics.classes.Issue;

public class AlwaysOnWork extends Worker {
    public AlwaysOnWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            if (!Basics.isMyServiceRunning(getApplicationContext(), AssistAlwaysOn.class)) {
                getApplicationContext().sendBroadcast(new Intent(getApplicationContext(), RestartReceiver.class));
            }
        } catch (Exception e) {
            Issue.set(e, AlwaysOnWork.class.getName());
            return Result.retry();
        }
        return Result.success();
    }

}
