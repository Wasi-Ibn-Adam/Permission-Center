package com.wasitech.permissioncenter.java.com.wasitech.assist.runnables;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;

import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;

public abstract class UserPicUpdateRunnable implements Runnable {
    private final byte[] bytes;

    public UserPicUpdateRunnable(Bitmap map) {
        this.bytes = Basics.Img.parseBytes(map);
    }

    public UserPicUpdateRunnable(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void run() {
        if (bytes != null)
            ByteSend();
        else
            onComplete();
    }

    private void ByteSend() {
        try {
            CloudDB.PicCenter.getProfilePicStorage(ProcessApp.getCurUser().getUid()).putBytes(bytes)
                    .addOnSuccessListener(taskSnapshot -> {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(this::updatePhotoUrl);
                    })
                    .addOnFailureListener(e -> {
                        Issue.set(e, UserPicUpdateRunnable.class.getName());
                        onError(e);
                        onComplete();
                    });
        } catch (Exception e) {
            onError(e);
            onComplete();
        }
    }

    private void updatePhotoUrl(Uri path) {
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(path)
                .build();
        try {
            ProcessApp.getCurUser().updateProfile(request).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ProcessApp.bytes = bytes;
                    onSuccess();
                } else
                    onError(task.getException());
                onComplete();
            });
        } catch (Exception e) {
            onError(e);
            onComplete();
        }
    }

    public abstract void onSuccess();

    public abstract void onError(Exception e);

    public void onComplete() {

    }
}

