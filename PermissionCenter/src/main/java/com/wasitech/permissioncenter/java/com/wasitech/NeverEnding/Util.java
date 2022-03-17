package com.wasitech.permissioncenter.java.com.wasitech.NeverEnding;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.wasitech.basics.classes.Basics;

import com.wasitech.basics.classes.Issue;

public class Util {

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        job(context);
    }

    private static void job(Context context) {
        try {
            ComponentName serviceComponent = new ComponentName(context, AlwaysOnJob.class);
            JobInfo.Builder builder = new JobInfo.Builder(1, serviceComponent);
            builder.setMinimumLatency(1000); // wait at least
            builder.setOverrideDeadline(5* 1000); // maximum delay
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //    builder.setPeriodic(2 * 1000,500);
            //}
            //else{
            //    builder.setPeriodic(2*1000);
            //}
            // builder.setBackoffCriteria(100, JobInfo.BACKOFF_POLICY_LINEAR);
            builder.setPersisted(true);
           // builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            //jobScheduler.schedule(builder.build());
            if( jobScheduler.schedule( builder.build() ) <= 0 ) {
                Basics.Log("Job Error");
            }
        } catch (Exception e) {
            Issue.print(e, Util.class.getName());
        }
    }

}
