package com.wasitech.permissioncenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

public abstract class CustomGroupedPermission extends GroupPermission{
    private final CustomRequest rq;

    public CustomGroupedPermission(Activity ac){
        super(ac);
        rq=new CustomRequest();
    }
    /**
     on your requestPermissionResult call onResult(int) method and pass the the requestCode
     */
    public CustomRequest customRequest(){return rq;}

    /**
     result of custom permission or permission set can be checked by calling <br>
     "multiPermissionCheck(ActivityCompat,String[],int)"
     */
    @Override
    public void onResult(int requestCode){
        super.onResult(requestCode);
        switch (requestCode){
            case Params.CAMERA_APP:{ multiPermissionCheck(ac, Params.AppPermission.permissionList(Params.CAMERA_APP),requestCode);break;}
            case Params.AUDIO_LISTEN_APP:{ multiPermissionCheck(ac, Params.AppPermission.permissionList(Params.AUDIO_LISTEN_APP),requestCode);break;}
            case Params.AUDIO_RECORDING_APP:{ multiPermissionCheck(ac, Params.AppPermission.permissionList(Params.AUDIO_RECORDING_APP),requestCode);break;}
            case Params.SMS_APP:{ multiPermissionCheck(ac, Params.AppPermission.permissionList(Params.SMS_APP), Params.SMS_APP);break;}
        }
    }

    public class CustomRequest{
        /**
         any set of permissions can be request and result can be find in "onResult(int)"
         */
        public void request(String[]permissions,int code){
            ac.requestPermissions(permissions,code);
        }

        public void audioRecordingApp(int code){
            request(Params.AppPermission.permissionList(Params.AUDIO_RECORDING_APP),code);
        }
        public void audioListenApp(int code){
            request(Params.AppPermission.permissionList(Params.AUDIO_LISTEN_APP),code);
        }
        public void cameraApp(int code){
            request(Params.AppPermission.permissionList(Params.CAMERA_APP),code);
        }
        public void contactApp(int code){
            request(Params.AppPermission.permissionList(Params.CONTACT_APP),code);
        }
        public void galleryApp(int code){
            request(Params.AppPermission.permissionList(Params.GALLERY_APP),code);
        }
        public void musicPlayerApp(int code){
            request(Params.AppPermission.permissionList(Params.MUSIC_PLAYER_APP),code);
        }
        public void flashApp(int code){
            request(Params.AppPermission.permissionList(Params.FLASH_APP),code);
        }
        public void phoneApp(int code){
            request(Params.AppPermission.permissionList(Params.PHONE_APP),code);
        }
        public void smsApp(int code){
            request(Params.AppPermission.permissionList(Params.SMS_APP),code);
        }
    }

    public static class CustomCheck{
        /**
         any set of permissions can be check
         */
        public boolean check(Context c,String[]permissions){
            return isPermissionSetGranted(c,permissions);
        }


        public boolean audioRecordingApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.AUDIO_RECORDING_APP));
        }
        public boolean audioListenApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.AUDIO_LISTEN_APP));
        }
        public boolean cameraApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.CAMERA_APP));
        }
        public boolean contactApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.CONTACT_APP));
        }
        public boolean galleryApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.GALLERY_APP));
        }
        public boolean musicPlayerApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.MUSIC_PLAYER_APP));
        }
        public boolean flashApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.FLASH_APP));
        }
        public boolean phoneApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.PHONE_APP));
        }
        public boolean smsApp(Context c){
            return check(c,Params.AppPermission.permissionList(Params.SMS_APP));
        }

    }

}
