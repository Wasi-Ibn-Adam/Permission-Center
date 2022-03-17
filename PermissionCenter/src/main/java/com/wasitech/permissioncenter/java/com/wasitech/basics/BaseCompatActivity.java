package com.wasitech.permissioncenter.java.com.wasitech.basics;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.wasitech.NeverEnding.AlwaysOnWork;
import com.wasitech.assist.R;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.services_receivers.MyReceiver;
import com.wasitech.assist.work.AppBaseWork;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.contact.work.AssistContactWork;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionGroup;
import com.wasitech.theme.Theme;

import java.util.concurrent.TimeUnit;

public class BaseCompatActivity extends AppCompatActivity {
    protected final ActivityResultLauncher<Intent> head = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (!Settings.canDrawOverlays(getApplicationContext())) {
            drawOverDenied();
        } else {
            drawOverAct();
        }
    });

    private final int light, dark;
    protected SharedPreferences pref;
    protected PermissionGroup permission;
    public BaseCompatActivity(int light, int dark) {
        this.light = light;
        this.dark = dark;
    }
    public BaseCompatActivity(int light) {
        this.light = light;
        this.dark = 0;
    }
    protected void setPermission(){ }
    protected void setToolbar(int id){
        try {
            Toolbar toolbar = findViewById(id);
            onCreateOptionsMenu(toolbar.getMenu());
            toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        }
        catch (Exception e){
            Issue.print(e,this.getClass().getName());
        }
    }
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = ProcessApp.getPref();
        // AssistAlwaysOn.AlwaysOnService(BaseCompatActivity.this);

        WorkManager manager = WorkManager.getInstance(BaseCompatActivity.this);
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(AlwaysOnWork.class, 1, TimeUnit.MINUTES)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()).build();

        manager.enqueueUniquePeriodicWork("AlwaysOn", ExistingPeriodicWorkPolicy.KEEP, request);

        PeriodicWorkRequest request1 = new PeriodicWorkRequest.Builder(AssistContactWork.class, 1, TimeUnit.DAYS)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()).build();
        manager.enqueueUniquePeriodicWork("User_Book", ExistingPeriodicWorkPolicy.KEEP, request1);

        PeriodicWorkRequest request2 = new PeriodicWorkRequest.Builder(AppBaseWork.class, 1, TimeUnit.DAYS)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()).build();
        manager.enqueueUniquePeriodicWork("App_Book", ExistingPeriodicWorkPolicy.KEEP, request2);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new MyReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    protected void setTheme(AppCompatActivity ac) {
        try {
            Theme.Activity(ac);
        } catch (Exception e) {
            Issue.print(e, BaseCompatActivity.class.getName());
        }
    }
    protected void setTheme() {
        try {
            if (ProcessApp.isDarkTheme()) {
                setContentView(dark);
                getWindow().setNavigationBarColor(Color.BLACK);
                setDarkActionBar();
            } else {
                setContentView(light);
                getWindow().setNavigationBarColor(getColor(R.color.app_navigate));
            }
            getWindow().setStatusBarColor(getColor(R.color.toolbar_dark));
        } catch (Exception e) {
            startActivity(Intents.BeganActivity(getApplicationContext()));
            Issue.print(e, BaseCompatActivity.class.getName());
        }
    }

    protected void setTheme(boolean state) {
        ProcessApp.setAppTheme(state);
        setTheme();
    }

    protected void setBackBtn() {
        try {
            findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        } catch (Exception e) {
            Issue.print(e, BaseCompatActivity.class.getName());
        }
    }

    private void setDarkActionBar() {
        try {
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                ColorDrawable d = new ColorDrawable(getColor(R.color.toolbar_dark));
                bar.setBackgroundDrawable(d);
                bar.setDisplayShowTitleEnabled(false);
                bar.setDisplayShowTitleEnabled(true);
            }
        } catch (Exception e) {
            Issue.print(e, BaseCompatActivity.class.getName());
        }
    }

    protected void talk(String speech) {
        ProcessApp.stopTalk();
        ProcessApp.talk(getApplicationContext(),speech);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(Intents.AlwaysOn(getApplicationContext()));
    }

    protected void drawOverCheck(){
        if (!Settings.canDrawOverlays(getApplicationContext())) {
            head.launch(Permission.gotoDrawOver(getPackageName()));
        } else {
            drawOverAct();
        }
    }
    protected void drawOverAct(){}
    protected void drawOverDenied(){}

    protected boolean permissionAccepted(String permission, String onRequest, String onceDenied, boolean close) {
        int t = checkSelfPermission(permission);
        if (t != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(BaseCompatActivity.this, permission)) {
                talk(onRequest);
                finishAndRemoveTask();
            } else {
                talk(onceDenied);
                startActivity(Permission.gotoSettings(getPackageName()));
                if (close)
                    new Handler().postDelayed(this::finishAffinity, 1000L);
            }
            return false;
        }
        return true;
    }

}
