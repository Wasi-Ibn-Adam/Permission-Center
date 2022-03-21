package com.wasitech.permissioncenter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import com.wasitech.permissioncenter.Params.AppPermission;

public abstract class GroupPermission extends Permission {
    public static final int CODE_GROUP_CAMERA = 201;
    public static final int CODE_GROUP_CONTACT = 202;
    public static final int CODE_GROUP_CALENDER = 203;
    public static final int CODE_GROUP_LOCATION = 204;
    public static final int CODE_GROUP_MICROPHONE = 205;
    public static final int CODE_GROUP_PHONE = 206;
    public static final int CODE_GROUP_SMS = 207;
    public static final int CODE_GROUP_STORAGE = 208;

    private final GroupRequest rq;

    public GroupPermission(Activity ac){
        super(ac);
        rq=new GroupRequest();
    }

    /**
     on your requestPermissionResult call onResult(int) method and pass the the requestCode
     */
    public GroupRequest groupRequest(){return rq;}

    public static boolean isPermissionSetGranted(Context c,String[] arr){
        boolean check=true;
        for(String per:arr){
            check=isPermissionGranted(c,per);
            if(!check)
                break;
        }
        return check;
    }
    protected void multiPermissionCheck(Activity ac, String[] permissions, int code){
        int prev = GRANTED;
        for(String permission:permissions){
            if(prev!=GRANTED)
                break;
            int t = ac.checkSelfPermission(permission);
            if (t != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ac, permission)) {
                    setShouldShowStatus(new String[]{permission},TRUE);
                    prev=ASKED;
                } else {
                    if(getRationaleStatus(permission))
                        prev=DENIED;
                    else
                        prev= NEVER_AGAIN;
                    setShouldShowStatus(new String[]{permission},FALSE);
                }
            }
            else
                prev=GRANTED;
        }
        switch (prev){
            //case ASKED:{ requireRationaleAsk(permissions,code);break;}
            case DENIED:{onDenied(code);break;}
            case GRANTED:{onGranted(code);break;}
            case NEVER_AGAIN:{neverAskAgain(code);break;}
        }
        onComplete(code);
    }

    @Override
    public void onResult(int requestCode){
        super.onResult(requestCode);
        switch (requestCode){
            case Params.CAMERA_APP:{ multiPermissionCheck(ac, AppPermission.permissionList(Params.CAMERA_APP),requestCode);break;}
            case Params.AUDIO_LISTEN_APP:{ onePermissionCheck(ac,Manifest.permission.RECORD_AUDIO,requestCode);break;}
            case Params.AUDIO_RECORDING_APP:{ multiPermissionCheck(ac, AppPermission.permissionList(Params.AUDIO_RECORDING_APP),requestCode);break;}
            case Params.SMS_APP:{ multiPermissionCheck(ac, AppPermission.permissionList(Params.SMS_APP), Params.SMS_APP);break;}
        }
    }

    public class GroupRequest{
        public void defined(int code){
            ac.requestPermissions(AppPermission.permissionList(code),code);
        }
        public void camera(){
            defined(CODE_GROUP_CAMERA);
        }
        public void contact(){defined(CODE_GROUP_CONTACT);}
        public void calender(){
            defined(CODE_GROUP_CALENDER);
        }
        public void location(){
            defined(CODE_GROUP_LOCATION);
        }
        public void microphone(){
            defined(CODE_GROUP_MICROPHONE);
        }
        public void phone(){defined(CODE_GROUP_PHONE);}
        public void storage(){
            defined(CODE_GROUP_STORAGE);
        }
        public void sms(){
            defined(CODE_GROUP_SMS);
        }
    }

    public static class GroupCheck{
        private boolean isPermissionSetGranted(Context c,int code){
            String []arr=Params.AppPermission.permissionList(code);
            return GroupPermission.isPermissionSetGranted(c,arr);
        }
        public boolean camera(Context c){return isPermissionSetGranted(c,GroupPermission.CODE_GROUP_CAMERA);}
        public boolean contact(Context c){return isPermissionSetGranted(c,GroupPermission.CODE_GROUP_CONTACT);}
        public boolean calender(Context c){return isPermissionSetGranted(c,GroupPermission.CODE_GROUP_CALENDER);}
        public boolean location(Context c){return isPermissionSetGranted(c,GroupPermission.CODE_GROUP_LOCATION);}
        public boolean microphone(Context c){return isPermissionSetGranted(c,GroupPermission.CODE_GROUP_MICROPHONE);}
        public boolean phone(Context c){return isPermissionSetGranted(c,GroupPermission.CODE_GROUP_PHONE);}
        public boolean sms(Context c){return isPermissionSetGranted(c,GroupPermission.CODE_GROUP_SMS);}
        public boolean storage(Context c){return isPermissionSetGranted(c,GroupPermission.CODE_GROUP_STORAGE);}
    }

}
