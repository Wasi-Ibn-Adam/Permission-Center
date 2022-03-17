package com.wasitech.permissioncenter.java.com.wasitech.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.wasitech.assist.command.family.Answers;
import com.wasitech.basics.classes.Issue;

public class Utils {
    public static void Download(String url, String name, Context context) {
        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            request.setDescription(Answers.ASSISTANT.NAME_ONLY() + " is downloading your stuff...");
            request.setTitle(Answers.ASSISTANT.NAME_ONLY());
            request.allowScanningByMediaScanner();
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Assist/" + name);

            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

        } catch (Exception e) {
            Issue.print(e, Utils.class.getName());
        }
    }
}
