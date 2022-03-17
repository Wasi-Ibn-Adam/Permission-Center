package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.content.Intent;

import com.wasitech.basics.classes.Basics;
import com.wasitech.camera.cam.CamApi1;

import com.wasitech.database.Params;

public class PasswordCamService extends BaseService {
    public PasswordCamService() {
        super("Wrong Password Detected");
    }
    @Override
    protected void startingIntent(Intent intent) {
        super.startingIntent(intent);
        Basics.Log(" service enter");
        int code = intent.getIntExtra(Params.EXTRA_CODE, 1);
        int cam = intent.getIntExtra(Params.EXTRA_CAM, 1);
        new CamApi1(PasswordCamService.this, cam,true);
    }
}
