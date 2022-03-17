package com.wasitech.permissioncenter.java.com.wasitech.download;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wasitech.assist.R;
import com.wasitech.assist.command.family.QA;
import com.wasitech.assist.popups.WaitingPopUp;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.Storage;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.permission.Permission;
import com.wasitech.permission.Permissions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class MediaReceiverActivity extends BaseCompatActivity {
    private WaitingPopUp popUp;
    private boolean close;

    public MediaReceiverActivity() {
        super(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Transparent);
        super.onCreate(savedInstanceState);
        if (!Permission.Check.storage(getApplicationContext()))
            Permissions.askWriteStorage(MediaReceiverActivity.this, Permissions.W_STORAGE);
        else {
            popUp = setPopUp();
            next();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionAccepted(permissions[0], "Permission Denied", "Grant Storage Permission.", true)) {
            next();
        }
    }

    private void next() {
        new Handler().postDelayed(() -> popUp.show(), 200L);
        String receivedAction = getIntent().getAction();
        if (receivedAction != null)
            switch (receivedAction) {
                case Intent.ACTION_SEND: {
                    close = true;
                    Uri uri = (Uri) getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
                    if (uri != null) actionFun(uri, getMimeType(uri));
                    else {
                        String t = getIntent().getStringExtra(Intent.EXTRA_TEXT);
                        if (t != null) {
                            if (getIntent().getData() != null) {
                                Basics.Log(getIntent().getData().getHost() + " host");
                                Basics.Log(getIntent().getData().getPath() + " path");
                            }
                            Basics.Log(t + " text");
                            linkSave(t);
                        }
                        closing();
                    }
                    break;
                }
                case Intent.ACTION_SEND_MULTIPLE: {
                    close = false;
                    try {
                        ArrayList<Uri> uris = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                        if (uris != null)
                            for (int i = 0; i < uris.size(); i++) {
                                if (i == uris.size() - 1)
                                    close = true;
                                Uri uriM = uris.get(i);
                                if (uriM != null) {
                                    actionFun(uriM, getMimeType(uriM));
                                }
                            }
                    } catch (Exception e) {
                        closing();
                        Issue.print(e, MediaReceiverActivity.class.getName());
                    }
                    break;
                }
                default: {
                    closing();
                    break;
                }
            }
        else
            closing();
    }

    private String getMimeType(Uri url) {
        return getContentResolver().getType(url);
    }

    private void actAccording() {
        String action = getIntent().getAction();
        if (action == null) closing();
        else {
            switch (action) {
                case Intent.ACTION_SEND: {
                    String type = getIntent().getType();
                    if (type == null) closing();
                    else {
                        if (type.startsWith("text")) {
                            textChecker();
                        } else {
                            Uri uri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
                            mediaChecker(uri, type);
                        }
                    }
                    break;
                }
                case Intent.ACTION_SEND_MULTIPLE:
                    ArrayList<Uri> list = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                    if (list != null && list.size() > 0)
                        for (Uri uri : list)
                            mediaChecker(uri, getMimeType(uri));
                    else
                        closing();
                    break;
                default:
                    closing();
            }
        }
    }

    private void mediaChecker(Uri uri, String mimeType) {
        if (mimeType.startsWith("image")) imgSave(getApplicationContext(), uri);
        else if (mimeType.startsWith("video")) vidSave(uri);
        else if (mimeType.startsWith("audio")) audSave(uri);
    }

    private void textChecker() {
        String t = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (null == t) {
            closing();
        } else {
            if (t.startsWith("https://youtu.be") || t.startsWith("http://youtu.be")) {
                youtube(getApplicationContext(), t);
            } else if (isInstaLink(t)) {
                downloadInsta(t);
                //InstaVideo.downloadVideo(getApplicationContext(), t);
            } else {
                Toast.makeText(getApplicationContext(), "Can't Save this, Right Now!", Toast.LENGTH_SHORT).show();
            }
            closing();
        }
    }


    private void actionFun(Uri uri, String mime) {
        if (mime.startsWith("audio/")) audSave(uri);
        else if (mime.startsWith("video/")) vidSave(uri);
        else if (mime.startsWith("image/")) imgSave(getApplicationContext(), uri);
        else closing();
    }

    private void closing() {
        if (close) {
            try {
                popUp.dismiss();
            } catch (Exception e) {
                Issue.print(e, MediaReceiverActivity.class.getName());
            }
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        popUp.dismiss();
    }

    private static String fileName(Context context, Uri uri) {
        if (uri != null) {
            @SuppressLint("Recycle") Cursor c1 = context.getContentResolver().query(uri, null, null, null, null);
            if (c1 != null && c1.moveToFirst()) {
                int id = c1.getColumnIndex(MediaStore.Images.Media.DATA);
                if (id != -1) {
                    return c1.getString(id);
                }
            }
            if (uri.toString().startsWith("file:")) {
                return uri.getPath();
            } else if (uri.toString().startsWith("content:")) {
                @SuppressLint("Recycle") Cursor c = context.getContentResolver().query(uri, null, null, null, null);
                if (c != null && c.moveToFirst()) {
                    int id = c.getColumnIndex(MediaStore.Images.Media.DATA);
                    if (id != -1) {
                        return c.getString(id);
                    }
                }
            }
            return uri.getPath();
        }
        return QA.cTime().replace(":", "_");
    }

    private static void saveFileRun(Context context, String type, Uri uri, String post) {
        String name = fileName(context, uri);
        String path = Storage.CreateDataFile(type, post).getPath();
        new Thread(() -> {
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(name));
                bos = new BufferedOutputStream(new FileOutputStream(path, false));
                byte[] buf = new byte[1024];
                bis.read(buf);
                do {
                    bos.write(buf);
                } while (bis.read(buf) != -1);
                Basics.Img.mediaScanner(context, path, "Saved.", "Failed.");
            } catch (IOException e) {
                Issue.print(e, MediaReceiverActivity.class.getName());
            } finally {
                try {
                    if (bis != null) bis.close();
                    if (bos != null) bos.close();
                } catch (IOException e) {
                    Issue.print(e, MediaReceiverActivity.class.getName());
                }
            }
        }).start();
    }

    private boolean saveFile(String type, Uri uri, String post) {
        String name = fileName(getApplicationContext(), uri);
        String path = Storage.CreateDataFile(type, post).getPath();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(name));
            bos = new BufferedOutputStream(new FileOutputStream(path, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while (bis.read(buf) != -1);
            Basics.Img.mediaScanner(getApplicationContext(), path);
            return true;
        } catch (IOException e) {
            Issue.print(e, MediaReceiverActivity.class.getName());
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                Issue.print(e, MediaReceiverActivity.class.getName());
            }
        }
        return false;
    }



    private static void youtube(final Context context, final String youtubeLink) {
        //Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        //calendar.setTime(new Date());               //Set the Calendar to now
        //int hour = calendar.get(Calendar.HOUR_OF_DAY); //Get the hour from the calendar

        //String url = "http://";
        //// We're running on a free heroku instance. THey need to sleep for atleast 6 hrs in a day
        //// SO lets just run two free instances and swap between them depending on teh time of the day, giving each instance a chance to sleep for 12hrs
        //if(hour >= 0 && hour <= 12)
        //{
        //    url += "youtube-dl55.";
        //} else {
        //    url += "youtube-dl99.";
        //}
        //url += "herokuapp.com/api/info?url=" + youtubeLink;
        //Temp asyncTask =new Temp(this);
        //asyncTask.delegate = this;
        //asyncTask.execute(url);
        new YouTubeExtractor(context) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    int tag = 22;
                    String downloadUrl = ytFiles.get(tag).getUrl();
                    Utils.Download(downloadUrl, fileNameExtractor(vMeta.getTitle()) + ".mp4", context);
                }
            }
        }.extract(youtubeLink);
    }

    private WaitingPopUp setPopUp() {
        return new WaitingPopUp(MediaReceiverActivity.this, "Saving...", false) {
            @Override
            protected void runner() {

            }

            @Override
            public void onClose() {

            }
        };
    }

    private static void imgSave(Context context, Uri uri) {
        Glide.with(context).asBitmap().load(uri).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Bitmap> transition) {
                Basics.Img.saveBitmap(resource, Storage.fileName());
                Basics.toasting(context, "Image Saved");
            }

            @Override
            public void onLoadCleared(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {
            }

            @Override
            public void onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                Basics.toasting(context, "Image not Saved");
            }
        });
    }

    private void vidSave(Uri uri) {
        saveFileRun(getApplicationContext(), Storage.VID, uri, ".mp4");
        //if (saveFile(Storage.VID, uri, ".mp4"))
        //    Toast.makeText(MediaReceiverActivity.this, "Video Saved", Toast.LENGTH_SHORT).show();
        //else
        //    Toast.makeText(MediaReceiverActivity.this, "Video not Saved", Toast.LENGTH_SHORT).show();
        closing();
    }

    private void audSave(Uri uri) {
        saveFileRun(getApplicationContext(), Storage.REC, uri, ".mp3");
        //if (saveFile(Storage.REC, uri, ".mp3"))
        //    Toast.makeText(MediaReceiverActivity.this, "Audio Saved", Toast.LENGTH_SHORT).show();
        //else
        //    Toast.makeText(MediaReceiverActivity.this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show();
        closing();
    }

    private void linkSave(String t) {
        if (t.startsWith("https://youtu.be") || t.startsWith("http://youtu.be")) {
            youtube(getApplicationContext(), t);
        } else if (isInstaLink(t)) {
            downloadInsta(t);
        } else {
            try {
                new IGDownloaderService(getApplicationContext()).process(t);
            } catch (Exception e) {
                Issue.print(e, MediaReceiverActivity.class.getName());
            }
            Toast.makeText(getApplicationContext(), "Can't Save this, Right Now!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isInstaLink(String str) { return str.contains("instagram"); }
    private void downloadInsta(String link) {
        link = link.substring(0, link.indexOf("?utm"));
        Utils.Download(link, Storage.fileName(), MediaReceiverActivity.this);
/**
        ApiUtilities.getInterface().getInfo(link+"?__a=1").enqueue(new Callback<InstaModel>() {
            @Override
            public void onResponse(@NonNull Call<InstaModel> call, @NonNull Response<InstaModel> response) {
                if (response.body() != null) {
                    String url = response.body().getInfo().get(0).getVideo_url();
                    Utils.Download(url, Storage.fileName(), MediaReceiverActivity.this);
                } else
                    Basics.toasting(MediaReceiverActivity.this, "Unknown Error Occurred"+response.message());
            }

            @Override
            public void onFailure(@NonNull Call<InstaModel> call, @NonNull Throwable t) {
                Basics.toasting(MediaReceiverActivity.this, "Unknown Error Occurred"+t.getMessage());
            }
        });
    */
    }
    private static String fileNameExtractor(String name) {
        name = name.replace(":", "_");
        if (name.contains(".")) {
            name = name.substring(0, name.indexOf('.'));
        }
        if (name.contains("/")) {
            name = name.substring(name.lastIndexOf('/') + 1);
        }
        return "Saved " + name;
    }
}