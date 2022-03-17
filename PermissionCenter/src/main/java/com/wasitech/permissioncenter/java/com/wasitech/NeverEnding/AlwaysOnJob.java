package com.wasitech.permissioncenter.java.com.wasitech.NeverEnding;

import android.app.job.JobParameters;
import android.app.job.JobService;

import androidx.work.Configuration;

import com.wasitech.basics.classes.Issue;


public class AlwaysOnJob extends JobService {
    public AlwaysOnJob() {
        Configuration.Builder builder = new Configuration.Builder();
        builder.setJobSchedulerJobIdRange(0, 1000);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        try {
            AssistAlwaysOn.AlwaysOnService(getApplicationContext());
        } catch (Exception e) {
            Issue.set(e, AlwaysOnJob.class.getName());
        }
        jobFinished(params, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobFinished(params, true);
        return true;
    }
}
