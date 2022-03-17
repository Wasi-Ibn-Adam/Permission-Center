package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wasitech.assist.R;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.adapter_listeners.NotiRecyclerViewAdapter;

import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

public class NotificationAppList extends BaseCompatActivity {
    private LinearLayout empty;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (Basics.isNLServiceRunning(getApplicationContext())) {
            pref.edit().putBoolean(Params.NOTI_READ, true).apply();
            implementation();
        } else {
            finishAndRemoveTask();
        }
    });

     public NotificationAppList() {
        super(R.layout.activity_noti_apps_light, R.layout.activity_noti_apps_dark);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setBackBtn();
        empty = findViewById(R.id.empty_layout);
        fab = findViewById(R.id.noti_fab);
        if (!Basics.isNLServiceRunning(getApplicationContext())) {
            launcher.launch(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            implementation();
        }
    }

    private void implementation() {
        recyclerView = findViewById(R.id.app_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NotiRecyclerViewAdapter(NotificationAppList.this));
        setVisibility(pref.getBoolean(Params.NOTI_READ, false));
        fab.setOnClickListener(v -> setVisibility(recyclerView.getVisibility() != View.VISIBLE));
    }

    private void setVisibility(boolean s) {
        try {
            if (s) {
                recyclerView.setVisibility(View.VISIBLE);
                empty.setVisibility(View.INVISIBLE);
                fab.setImageResource(R.drawable.invisible);
                recyclerView.scrollToPosition(View.SCROLL_INDICATOR_TOP);
                pref.edit().putBoolean(Params.NOTI_READ, true).apply();
            } else {
                empty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                fab.setImageResource(R.drawable.visible);
                pref.edit().putBoolean(Params.NOTI_READ, false).apply();
            }
        } catch (Exception e) {
            Issue.print(e, NotificationAppList.class.getName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && Basics.isNLServiceRunning(getApplicationContext())) {
            pref.edit().putBoolean(Params.NOTI_READ, true).apply();
            implementation();
        } else {
            finishAndRemoveTask();
        }
    }

}