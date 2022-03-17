package com.wasitech.permissioncenter.java.com.wasitech.assist.runnables;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.Profile;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;

import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;

public abstract class PicDownload implements Runnable {
    protected final Uri uri;
    private final Context context;
    private String uid;


    /** ONLY FOR USER HIMSELF<br>
     * it downloads and store the img in Local db with UID <br>
     * also it sets the img to App ProcessApp.bytes */
    public PicDownload(Context context, Uri path) {
        this.context = context;
        uri = path;

    }
    /** FOR ALL USER'S<br>
     * it downloads and store the img in Local db with UID  */
    public PicDownload(Context context, Uri path, String uid) {
        this.context = context;
        uri = path;
        this.uid=uid;
    }

    @Override
    public void run() {
        download();
    }

    private void download() {
        try {
            Glide.with(context).asBitmap().load(uri).addListener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    onError(e);
                    onComplete();
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    try {
                        if(uid==null) {
                            ProcessApp.bytes = Basics.Img.parseBytes(resource);
                            new LocalDB(context.getApplicationContext()).savePic(ProcessApp.getCurUser().getUid(), ProcessApp.bytes);
                        }
                        else{
                            new LocalDB(context.getApplicationContext()).updatePic(uid, Basics.Img.parseBytes(resource));
                        }
                    }
                    catch (Exception e){
                        Issue.print(e, PicDownload.class.getName());
                    }
                    onSuccess();
                    onComplete();
                    return true;
                }
            }).submit();
        }
        catch (Exception e){
            onError(e);
            onComplete();
        }
    }

    public static Uri getFbPath(){
        return Profile.getCurrentProfile().getProfilePictureUri(250, 250);
    }
    public static Uri getAssistPath(){
        return ProcessApp.getCurUser().getPhotoUrl();
    }
    public abstract void onSuccess();

    public abstract void onError(Exception e);

    public abstract void onComplete();

}

