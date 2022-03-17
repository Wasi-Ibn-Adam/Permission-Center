package com.wasitech.permissioncenter.java.com.wasitech.download;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.wasitech.assist.command.family.Answers;
import com.wasitech.basics.classes.Issue;

public class VideoDownloadService extends AsyncTask<String, String, Boolean> {

    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public VideoDownloadService(Context context) {
            this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String name = params[1];

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(params[0]));
            request.setDescription(Answers.ASSISTANT.NAME_ONLY() + " is downloading your stuff...");
            request.setTitle(Answers.ASSISTANT.NAME_ONLY());
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Assist/" + name);

            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

            return true;
        } catch (Exception e) {
            Issue.print(e,MediaReceiverActivity.class.getName());
        }
        return false;
    }
}