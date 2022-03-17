package com.wasitech.permissioncenter.java.com.wasitech.assist.actions;

import android.content.Context;
import android.content.Intent;

import com.wasitech.assist.classes.Speak;
import com.wasitech.assist.command.family.Coder;
import com.wasitech.permission.PermissionChecker;

import com.wasitech.database.Params;

public class ListenTalk extends Speak {

    public ListenTalk(Context context) {
        super(context, Params.LISTEN_TALK);
        StartSpeak();
    }

    @Override
    public void ByeBye() { }

    @Override
    public void PermissionRequire(int code) {
        switch (code) {
            case Coder.PERMISSION_R_CONTACT: {
                Talking("Permission to Read Contacts required.");
                context.startActivity(new Intent(context, PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"con_r"));
                break;
            }
            case Coder.PERMISSION_W_CONTACT: {
                Talking("Permission to Write Contacts required.");
                context.startActivity(new Intent(context,PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"con_w"));
                break;
            }
            case Coder.PERMISSION_RW_CONTACT: {
                Talking("Permission to Read and Write Contacts required.");
                context.startActivity(new Intent(context,PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"con_rw"));
                break;
            }
            case Coder.PERMISSION_R_STORAGE: {
                Talking("Permission to Read Storage required.");
                context.startActivity(new Intent(context,PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"sto_r"));
                break;
            }
            case Coder.PERMISSION_W_STORAGE: {
                Talking("Permission to Write Storage required.");
                context.startActivity(new Intent(context,PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"sto_w"));
                break;
            }
            case Coder.PERMISSION_RW_STORAGE: {
                Talking("Permission to Read and Write Storage required.");
                context.startActivity(new Intent(context,PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"sto_rw"));
                break;
            }
            case Coder.PERMISSION_MIC: {
                Talking("Permission required to Access mic.");
                context.startActivity(new Intent(context,PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"mic"));
                break;
            }
            case Coder.PERMISSION_CAMERA: {
                Talking("Permission to Access Camera is required.");
                context.startActivity(new Intent(context,PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"cam"));
                break;
            }
            case Coder.PERMISSION_LOCATION: {
                Talking("Permission to Access Location is required.");
                context.startActivity(new Intent(context,PermissionChecker.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Params.DATA_TRANS,"loc"));
                break;
            }
        }
    }


}
