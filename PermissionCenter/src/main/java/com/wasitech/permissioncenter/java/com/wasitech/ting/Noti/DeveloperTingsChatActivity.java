package com.wasitech.permissioncenter.java.com.wasitech.ting.Noti;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.assist.R;
import com.wasitech.ting.Chat.Notification.MessageCenter;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.assist.classes.Tingno;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.command.family.QA;

import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

public class DeveloperTingsChatActivity extends Activity {
    private RecyclerView recyclerView;
    private LocalDB db;
    private TingUser user;
    private boolean pause;
    private DatabaseReference receive;
    private MessageCenter center;

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot shot) {
            try {
                if (shot.exists()) {
                    String name = shot.child(Params.NAME).getValue(String.class);
                    if (shot.hasChild(Params.TING)) {
                        for (DataSnapshot tings : shot.child(Params.TING).getChildren()) {
                            String time = tings.getKey() + "";
                            String text = tings.getValue() + "";
                            Tingno ting = new Tingno(user.getUid(), text, name, time);
                            if (db.tingNotExist(ting)) {
                                db.addTingNoti(ting);
                                db.addDeveloperTingCount(user.getUid(), 1);
                            }
                            if (pause) {
                                new OldNotificationMaker(DeveloperTingsChatActivity.this).TingNoNotify(name, text, Intents.DeveloperTingNo(getApplicationContext(),user.getUid()));
                            }
                            tings.getRef().removeValue();
                        }
                    }
                    update();
                }
            } catch (Exception e) {
                Issue.set(e, DeveloperTingsChatActivity.class.getName());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinggo_chat_dark);
        try{
            db = new LocalDB(getApplicationContext());
            String id=getIntent().getStringExtra(Params.ID);
            if(id==null)
                finishAffinity();
            user=db.getAssistUser(id);
            setTitle(user.getName());
            db.deleteDeveloperTingCount(id);

            recyclerView = findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            center=new MessageCenter(getApplicationContext());
            receive =  CloudDB.TingNo.getTingNoDeveloperReceive().child(user.getUid());
            receive.addValueEventListener(listener);
            findViewById(R.id.send).setOnClickListener(v1 -> {
                try {
                    EditText editText=findViewById(R.id.ting_text);
                    String text =editText.getText().toString();
                    if (text.trim().length() > 1) {
                        sendTing(text,user.getUid());
                        editText.setText("");
                        update();
                    }
                } catch (Exception e) {
                    Issue.set(e, DeveloperTingsChatActivity.class.getName());
                }
            });
        }catch (Exception e){
            Issue.print(e,DeveloperTingsChatActivity.class.getName());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
    }

    private void update() {
        recyclerView.setAdapter(new DeveloperTingNoChatAdapter(db.getTingNotiList(user.getUid())) {
            @Override
            protected boolean onLongClicked(Tingno v) {
                new Handler().post(() -> {
                    db.deleteTingNo(v);
                    update();
                });
                return true;
            }
        });
    }

    private void sendTing(String text, String id) {
        Tingno ting = new Tingno(id, text, Tingno.DEVELOPER, QA.tingTime());
        center.developerTingNo(ting);
        db.addTingNoti(ting);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receive.removeEventListener(listener);
    }
}
