package com.wasitech.permissioncenter.java.com.wasitech.assist.testing;

import android.app.role.RoleManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.wasitech.assist.R;

public class DialerActivity extends AppCompatActivity {
    StringBuilder builder;
    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer_light);
        builder=new StringBuilder();
        input=findViewById(R.id.number);
        findViewById(R.id.btn_0).setOnClickListener(v -> add(0));
        findViewById(R.id.btn_1).setOnClickListener(v -> add(1));
        findViewById(R.id.btn_2).setOnClickListener(v -> add(2));
        findViewById(R.id.btn_3).setOnClickListener(v -> add(3));
        findViewById(R.id.btn_4).setOnClickListener(v -> add(4));
        findViewById(R.id.btn_5).setOnClickListener(v -> add(5));
        findViewById(R.id.btn_6).setOnClickListener(v -> add(6));
        findViewById(R.id.btn_7).setOnClickListener(v -> add(7));
        findViewById(R.id.btn_8).setOnClickListener(v -> add(8));
        findViewById(R.id.btn_9).setOnClickListener(v -> add(9));
        findViewById(R.id.btn_hash).setOnClickListener(v -> add("#"));
        findViewById(R.id.btn_star).setOnClickListener(v -> add("*"));
        findViewById(R.id.cross).setOnClickListener(v -> {
            builder.deleteCharAt(builder.length()-1);
            input.setText(builder.toString());
        });

        //Dexter.withContext(this).withPermission(Manifest.permission.CALL_PHONE).withListener(new PermissionListener() {
        //    @Override
        //    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//
        //    }
//
        //    @Override
        //    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
        //    }
//
        //    @Override
        //    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//
        //    }
        //}).check();


    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void requestRole() {
        RoleManager roleManager = (RoleManager) getSystemService(ROLE_SERVICE);
        Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1) {
            if (resultCode == android.app.Activity.RESULT_OK) {
                // Your app is now the default dialer app
            } else {
                // Your app is not the default dialer app
            }
        }
    }

    private void add(Object i) {
        builder.append(i);
        input.setText(builder.toString());
    }



}