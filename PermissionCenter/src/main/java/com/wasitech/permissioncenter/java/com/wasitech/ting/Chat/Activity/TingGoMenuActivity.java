package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.assist.PhoneRegisterPopUp;
import com.wasitech.assist.R;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.assist.classes.UserLastTing;
import com.wasitech.assist.runnables.PicDownload;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import com.wasitech.permission.Permission;
import com.wasitech.permission.Permissions;
import com.wasitech.ting.Chat.Adapater.TingGoMenuAdapter;
import com.wasitech.ting.Chat.Module.Ting;
import com.wasitech.ting.Chat.Module.UserDetailPopUp;

import java.util.ArrayList;
import java.util.Collections;

public class TingGoMenuActivity extends BaseCompatActivity {
    private RecyclerView recyclerView;
    private LocalDB db;
    private ArrayList<UserLastTing> list;
    private DatabaseReference ref;
    private final ValueEventListener chatListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    Ting ting=shot.getValue(Ting.class);
                    if(ting==null)
                        continue;
                    if(ting.getTinggo()!=null){
                        if(db.tinggoNotExist(ting)){
                            db.addTingGo(ting);
                            db.addTingCount(ting.getTinggo().getUid(),1);
                            update();
                        }
                        shot.child("tinggo").getRef().removeValue();
                    }
                    else {
                        try {
                            String id = snapshot.getKey();
                            String msgId = shot.getKey();
                            ting.setMsgId(msgId);
                            if(!db.tinggoNotExist(ting)) {
                                if(db.tingUpdate(id, msgId, ting.getReaction())) {
                                    db.addTingCount(id, 1);
                                    update();
                                }
                            }
                        } catch (Exception e) {
                            Issue.print(e,TingGoMenuActivity.class.getName());
                        }
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    private final ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    TingUser user = shot.getValue(TingUser.class);
                    if(user!=null)
                    if (db.userNotExist(user.getUid())) {
                        db.addTingUser(user);
                        new Thread(new PicDownload(getApplicationContext(), Uri.parse(user.getImagePath()),user.getUid()) {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };

    public TingGoMenuActivity() {
        super(R.layout.activity_tinggo_menu_dark, R.layout.activity_tinggo_menu_dark);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setTitle("TingGo");
        if (ProcessApp.getCurUser().getPhoneNumber() == null) {
            PhoneRegisterPopUp pop = new PhoneRegisterPopUp(TingGoMenuActivity.this) {
                @Override
                public void onDismiss(boolean flag) {
                    if (!flag)
                        finish();
                }
            };
            new Handler().postDelayed(pop::show, 1000L);
        }
        db = new LocalDB(getApplicationContext());
        ref = CloudDB.Tinggo.tingGo().child(ProcessApp.getUser().getUid());

        recyclerView = findViewById(R.id.tinggo_user_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.add_chat).setOnClickListener(v->contactList());

        CloudDB.Tinggo.tingUser().addValueEventListener(userListener);
        ref.addValueEventListener(chatListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
    }

    private void update() {
        list=db.getUserWithChatList();
        Collections.sort(list,UserLastTing.Comparator);

        recyclerView.setAdapter(new TingGoMenuAdapter(TingGoMenuActivity.this, list) {
            @Override
            public boolean onLongClick(View v) {
                int n=recyclerView.getChildAdapterPosition(v);
                new UserDetailPopUp(TingGoMenuActivity.this, list.get(n));
            return true;
            }
        });
    }

    private void contactList(){
        if (!Permission.Check.contacts(getApplicationContext())) {
            Permissions.askReadContact(TingGoMenuActivity.this, Permissions.R_CONTACT);
        } else {
            startActivity(new Intent(TingGoMenuActivity.this, TingGoUsersList.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ref.removeEventListener(chatListener);
    }
}