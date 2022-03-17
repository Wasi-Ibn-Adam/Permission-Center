package com.wasitech.permissioncenter.java.com.wasitech.camera.cam;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;

import com.wasitech.basics.classes.Issue;
import com.wasitech.permission.Permission;

public class Cam {

    public static boolean allowCamera2Support(Context context, int id) {

        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(manager.getCameraIdList()[id]);
            int support = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                return support == CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED ||
                        support == CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_FULL ||
                        support == CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_3;
            else
                return support == CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED ||
                        support == CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_FULL;
        }
        catch (CameraAccessException e) {
            Issue.print(e, CamApi1.class.getName());

        }
        return false;
    }
    public static void TakePic(Context context, int camId){
        if (Permission.Check.camera(context)) {
            if (Cam.allowCamera2Support(context, camId)) {
               context.startActivity(CamApi2.Intents.takePic(context,camId));
            } else {
                new CamApi1(context,camId,true);
            }
        }
    }

}
