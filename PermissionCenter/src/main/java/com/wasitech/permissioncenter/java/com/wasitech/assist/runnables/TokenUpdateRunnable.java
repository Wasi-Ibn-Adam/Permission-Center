package com.wasitech.permissioncenter.java.com.wasitech.assist.runnables;

import android.content.Context;
import android.net.Uri;

import com.wasitech.NeverEnding.AssistAlwaysOn;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.CloudDB;
import com.wasitech.database.Params;

public class TokenUpdateRunnable implements Runnable{
    private String token;
    private final Context context;

    public TokenUpdateRunnable(Context context){
        this.context=context;
    }
    public TokenUpdateRunnable(Context context,String token){
        this.context=context;
        this.token=token;
    }

    @Override
    public void run() {
        try{
            if(token==null)
            tokenGetter();
            else
                nextStep();
           // Firebase.USER_INFO().child(Params.TOKEN).setValue(token);
        }
        catch (Exception e){
            Issue.print(e, TokenUpdateRunnable.class.getName());
        }
    }

    private void nextStep() {
        try{
            ProcessApp.getPref().edit().putString(Params.TOKEN,token).apply();

            String uid=ProcessApp.getUser().getUid();
            String name=ProcessApp.getUser().getDisplayName();
            Uri uri=ProcessApp.getUser().getPhotoUrl();

            String path="null";
            if(uri!=null)
                path=uri.toString();

            CloudDB.Tinggo.tingUser().child(uid).child(Params.NAME).setValue(name);
            CloudDB.Tinggo.tingUser().child(uid).child(Params.TOKEN).setValue(token);
            CloudDB.Tinggo.tingUser().child(uid).child(Params.ICON).setValue(path);

            CloudDB.TingNo.tingNo().child(uid).child(Params.NAME).setValue(name);
            CloudDB.TingNo.tingNo().child(uid).child(Params.TOKEN).setValue(token);

            if(AssistAlwaysOn.isDeveloper(ProcessApp.getUser().getEmail())){
                CloudDB.TingNo.setDeveloperToken(token);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void tokenGetter(){
        if(ProcessApp.getCurUser()!=null){
            ProcessApp.getUser().getIdToken(false).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    token=task.getResult().getToken();
                    nextStep();
                }
            });
        }
    }

}
