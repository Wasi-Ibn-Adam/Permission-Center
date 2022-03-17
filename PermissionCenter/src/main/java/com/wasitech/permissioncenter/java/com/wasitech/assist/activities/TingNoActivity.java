package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.assist.R;
import com.wasitech.ting.Chat.Notification.MessageCenter;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.adapter_listeners.TingnoAdapter;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.assist.classes.Tingno;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.command.family.QA;
import com.wasitech.assist.popups.ThemedPopUps;

import java.util.ArrayList;

import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

public class TingNoActivity extends BaseCompatActivity {
    private RecyclerView recyclerView;
    public static String active="active-tings";
    private EditText editText;
    private LocalDB db;
    private boolean pause;
    private MessageCenter center;
    private DatabaseReference receive;

    public TingNoActivity() {
        super(R.layout.activity_tingno_light, R.layout.activity_tingno_dark);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        db = new LocalDB(getApplicationContext());
        center = new MessageCenter(getApplicationContext());
        receive = CloudDB.TingNo.tingNoUserReceive();

        editText = findViewById(R.id.tingno_input);
        recyclerView = findViewById(R.id.tingno_recycler_view);
        setList();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextInputLayout lay = findViewById(R.id.textInputLayout);
        lay.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        lay.setEndIconOnClickListener(v -> {
            if (Basics.Internet.isInternetJson(this)) {
                if (!editText.getText().toString().isEmpty()) {
                    String text = editText.getText().toString();
                    editText.setText("");
                    if (ProcessApp.getUser() != null) {
                        Tingno ting = new Tingno(Tingno.DEVELOPER, text, Params.YOU, QA.tingTime());
                        center.userTingNo(ting);
                        db.addTingNoti(ting);
                        setList();
                    } else {
                        Toast.makeText(TingNoActivity.this, "Error Occurred Please Re-Login.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        receive.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        Tingno tingno = new Tingno(Tingno.DEVELOPER, s.getValue(String.class), Params.TING, s.getKey());
                        if (db.tingNotExist(tingno)) {
                            db.addTingNoti(tingno);
                        }
                        setList();
                        if (pause) {
                            new OldNotificationMaker(TingNoActivity.this).TingNoNotify("TingNo", tingno.getText(), Intents.TingNoActivity(getApplicationContext()));
                        }
                        s.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            new OldNotificationMaker(getApplicationContext()).Manager().cancel(OldNotificationMaker.TINGNO);
        }
        catch (Exception e){
            Issue.print(e,TingNoActivity.class.getName());
        }
        ProcessApp.getPref().edit().putBoolean(active,true).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause = false;
    }

    private void setList() {
        ArrayList<Tingno> list = db.getUsersTingNotiList();
        recyclerView.scrollToPosition(list.size() - 1);
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(new TingnoAdapter(list));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tingno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_tingno) {
            new LocalDB(getApplicationContext()).deleteTingNo(Tingno.DEVELOPER);
            receive.setValue("");
            receive.child(QA.tingTime()).setValue("Welcome to TingNo!");
            new Handler().postDelayed(this::setList, 500);
            return true;
        }
        if (item.getItemId() == R.id.delete_tingno_info) {
            ThemedPopUps.BackgroundPopup(TingNoActivity.this, getString(R.string.tingno_info));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProcessApp.getPref().edit().putBoolean(active,false).apply();
    }
}