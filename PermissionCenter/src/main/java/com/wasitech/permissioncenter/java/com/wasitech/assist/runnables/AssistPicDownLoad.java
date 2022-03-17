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
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;

public abstract class AssistPicDownLoad implements Runnable {
    private final Uri uri;
    private final Context context;

    public AssistPicDownLoad(Context context) {
        uri = Profile.getCurrentProfile().getProfilePictureUri(250, 250);
        this.context = context;
    }

    @Override
    public void run() {
        download();
    }

    private void download() {
        Glide.with(context).asBitmap().load(uri).addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                AssistPicDownLoad.this.onSuccess();
                AssistPicDownLoad.this.onComplete();
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                ProcessApp.bytes = Basics.Img.parseBytes(resource);
                new Thread(new UserPicUpdateRunnable(ProcessApp.bytes) {
                    @Override
                    public void onSuccess() {
                        AssistPicDownLoad.this.onSuccess();
                        AssistPicDownLoad.this.onComplete();
                    }

                    @Override
                    public void onError(Exception e) {
                        AssistPicDownLoad.this.onError(e);
                        AssistPicDownLoad.this.onComplete();
                    }
                }).start();
                return true;
            }
        }).submit();
    }

    public void onComplete() {
    }

    public abstract void onSuccess();

    public abstract void onError(Exception e);



}

