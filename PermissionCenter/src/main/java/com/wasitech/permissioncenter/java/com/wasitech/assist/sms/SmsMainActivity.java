package com.wasitech.permissioncenter.java.com.wasitech.assist.sms;

import android.app.role.RoleManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wasitech.assist.R;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.contact.activity.ContactListAct;
import com.wasitech.contact.classes.ContactSms;
import com.wasitech.contact.runnable.ContactRunnable;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;
import com.wasitech.permission.Permissions;

import java.util.ArrayList;

public class SmsMainActivity extends BaseCompatActivity {
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> { });
    private FloatingActionButton fab;
    private RecyclerView view;
    private LocalDB db;
    private ArrayList<ContactSms> list;

    public SmsMainActivity() {
        super(R.layout.activity_sms, R.layout.activity_sms);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        makeMyAppDefaultRequest(getPackageName());
        if (Permissions.isNotReadWriteSms(getApplicationContext())) {
            Permissions.askReadWriteSms(this, Permissions.SMS);
        }

        db = new LocalDB(getApplicationContext());
        view = findViewById(R.id.recycler);
        fab = findViewById(R.id.add);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        update();

        fab.setOnClickListener(v -> {
            launcher.launch(ContactListAct.Intents.smsContacts(SmsMainActivity.this));
        });
    }

    private void update() {
        list = db.getContactSmsList();
        view.setAdapter(new SmsMainAdapter(list) {
            @Override
            public void onClick(ContactSms contactSms) {
                Intent intent = new Intent(getApplicationContext(), SmsSubActivity.class);
                intent.putExtra(Params.NAME, contactSms.getName());
                intent.putExtra(Params.NUM, contactSms.getSender());
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean b = false;
        for (String p : permissions) {
            if (permissionAccepted(p, "Permission Denied.", "Grant Sms and Contact Permission", false)) {
                b = true;
            } else {
                b = false;
                break;
            }
        }
        if (b) {
            new Thread(new ContactRunnable(getApplicationContext(), true)).start();
        }
    }

    public void makeMyAppDefaultRequest(String packageName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            RoleManager roleManager = getSystemService(RoleManager.class);
            if (roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
                if (roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
                    if (Permissions.isNotReadWriteSms(getApplicationContext())) {
                        Permissions.askReadWriteSms(this, Permissions.SMS);
                    }
                } else {
                    Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS);
                    startActivity(intent);
                }
            }
        } else {
            if (!Telephony.Sms.getDefaultSmsPackage(this).equals(packageName)) {
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
                startActivity(intent);
            }
        }

    }
}