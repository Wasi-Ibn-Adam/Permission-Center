package com.wasitech.permissioncenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public abstract class PermissionGroup extends Permission {

    public static final int AUD_HEAD=201;
    public static final int BACKGROUND=202;
    public static final int CAM_HEAD=203;
    public static final int COM_HEAD=204;
    public static final int CONTACT_DIAL=205;
    public static final int CONTACT_GET=206;
    public static final int CONTACT_SHOW=207;
    public static final int FLASH=208;
    public static final int FIND_PHONE=209;

    public static final int MUSIC_FIND=210;
    public static final int MUSIC_PLAY=211;
    public static final int MUSIC_SHOW=212;
    public static final int PIC_SHOW=213;
    public static final int TAKE_PIC=214;
    public static final int VIDEO_SHOW=215;
    public static final int SMS_SEND=216;

    public static int ACTION;
    private final GroupRequest rq;
    public PermissionGroup(Activity ac){
        super(ac);
        rq=new GroupRequest();
    }
    public GroupRequest groupRequest(){return rq;}

    @Override
    public void onResult(int requestCode){
        super.onResult(requestCode);
        switch (requestCode){
            case CODE_IGNORE:{break;}
            case TAKE_PIC:
            case CAM_HEAD:{ checkMul(ac,Talking.permissionList(CAM_HEAD),requestCode);break;}
            case COM_HEAD:{ checkOne(ac,Manifest.permission.RECORD_AUDIO,requestCode);break;}
            case AUD_HEAD:{ checkMul(ac,Talking.permissionList(AUD_HEAD),requestCode);break;}
            case SMS_SEND:{ checkMul(ac,Talking.permissionList(SMS_SEND),SMS_SEND);break;}
            case FIND_PHONE:{ checkMul(ac,Talking.permissionList(FIND_PHONE),FIND_PHONE);break;}
        }
    }

    private void checkMul(Activity ac, String[] permissions, int code){
        int prev = GRANTED;
        for(String permission:permissions){
            if(prev!=GRANTED)
                break;
            int t = ac.checkSelfPermission(permission);
            if (t != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ac, permission)) {
                    setShouldShowStatus(permission,TRUE);
                    prev=ASKED;
                } else {
                    if(getRationaleStatus(permission))
                        prev=DENIED;
                    else
                        prev= NEVER_AGAIN;
                    setShouldShowStatus(permission,FALSE);
                }
            }
            else
               prev=GRANTED;
        }
        switch (prev){
            //case ASKED:{ requireRationaleAsk(code);break;}
            case DENIED:{onDenied(code);break;}
            case GRANTED:{onGranted(code);break;}
            case NEVER_AGAIN:{neverAskAgain(code);break;}
        }
        onComplete(code);
    }

    public class GroupRequest{
        private void defined(int basic,int code){
            ac.requestPermissions(Talking.permissionList(basic),code);
        }
        public void defined(int code){
            ac.requestPermissions(Talking.permissionList(code),code);
        }
        public void storage(){
            defined(CODE_STORAGE);
        }
        public void calender(){
            defined(CODE_CALENDER);
        }
        public void sms(){
            defined(SMS_SEND);
        }
        public void findPhone(){
            defined(FIND_PHONE);
        }
        public void audioHead(){
            defined(AUD_HEAD);
        }
        public void cameraHead(){
            defined(CAM_HEAD);
        }
        public void cameraHead(int code){
            defined(CAM_HEAD,code);
        }
    }

    public static class FragmentRequest{
        public static int Storage(){
            return PermissionGroup.CODE_STORAGE;
        }
    }

    public static class GroupCheck{
        public boolean audioHead(Context c){
            return singlePermissionCheck(c,Manifest.permission.RECORD_AUDIO)&&singlePermissionCheck(c,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        public boolean cameraHead(Context c){
            return singlePermissionCheck(c,Manifest.permission.CAMERA)&&singlePermissionCheck(c,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }



}
