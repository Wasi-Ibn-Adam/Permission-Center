package com.wasitech.permissioncenter.java.com.wasitech.assist.sms;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.assist.classes.Sms;
import com.wasitech.assist.command.family.QA;
import com.wasitech.assist.services_receivers.SmsReceiver;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.classes.Basics;
import com.wasitech.contact.runnable.ContactRunnable;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;
import com.wasitech.permission.Permissions;

import java.util.ArrayList;

public class SmsSubActivity extends BaseCompatActivity {
    private EditText txt;
    private RecyclerView view;
    private LocalDB db;
    private String id,name;
    private ArrayList<Sms> list;

    public SmsSubActivity() {
        super(R.layout.activity_sms_sub, R.layout.activity_sms_sub);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();

        id = getIntent().getStringExtra(Params.NUM);
        name = getIntent().getStringExtra(Params.NAME);
        setTitle(name);
        Basics.Log(id);

        db = new LocalDB(getApplicationContext());
        view = findViewById(R.id.recycler);
        txt = findViewById(R.id.sms_text);
        ImageButton btn = findViewById(R.id.sms_send);
        ImageButton emoji = findViewById(R.id.emoji);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        btn.setOnClickListener(v -> {
            if (Permissions.isNotReadWriteSms(getApplicationContext())) {
                Permissions.askReadWriteSms(this, Permissions.SMS);
                return;
            }
            String text = txt.getText().toString();
            if (!text.isEmpty()) {
                sendSms(text, QA.tingTime());
                txt.setText("");
                update();
            }
        });
    }

    private void update() {
        list = db.getSmsList(id);
        view.setAdapter(new SmsSubAdapter(list) {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
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

    private void sendSms(String text, String time) {
        SmsManager manager = SmsManager.getDefault();
        PendingIntent send, deliver;
        db.addSms(new Sms(id,null,text,time,Sms.SENDER));
        //ArrayList<String>ls=new ArrayList<>();
        //ls.add(id);
        //db.addContact(new Contact(name,ls));

        Intent s = new Intent(this, SmsReceiver.class).setAction(SmsReceiver.ACTION).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        send = PendingIntent.getBroadcast(this, 1, s, PendingIntent.FLAG_IMMUTABLE);
        deliver = PendingIntent.getBroadcast(this, 1, s, PendingIntent.FLAG_IMMUTABLE);
        manager.sendTextMessage(id, null, text, send, deliver);
    }
}