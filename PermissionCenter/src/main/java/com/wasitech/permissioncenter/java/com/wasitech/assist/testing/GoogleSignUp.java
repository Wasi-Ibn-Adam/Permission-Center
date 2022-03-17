package com.wasitech.permissioncenter.java.com.wasitech.assist.testing;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.wasitech.assist.R;

import com.wasitech.database.Params;

public class GoogleSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_up);

        FirebaseAuth auth=FirebaseAuth.getInstance(FirebaseApp.getInstance(Params.TINGGO));
        if(auth!=null){
           // startFun();
            Log.i("founder","auth exist");
        }
        else{
            auth.signInAnonymously().addOnCompleteListener(task->{
                if(task.isSuccessful()){
                    Log.i("founder","reg success");
                   // startFun();
                }else{
                    Log.i("founder","reg failed");
                }
            });
        }
    }
}