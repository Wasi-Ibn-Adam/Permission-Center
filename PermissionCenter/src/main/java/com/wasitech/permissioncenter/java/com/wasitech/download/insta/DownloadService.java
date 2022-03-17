package com.wasitech.permissioncenter.java.com.wasitech.download.insta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.wasitech.basics.classes.Issue;
import com.wasitech.download.MediaReceiverActivity;
import com.wasitech.download.Utils;

public class DownloadService extends AsyncTask<String, String, Boolean> {

    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public DownloadService(Context context) {
            this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            Utils.Download(params[0],params[1],context);
            return true;
        } catch (Exception e) {
            Issue.print(e, MediaReceiverActivity.class.getName());
        }
        return false;
    }
}