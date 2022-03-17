package com.wasitech.permissioncenter.java.com.wasitech.ting.Noti;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.assist.R;
import com.wasitech.ting.Chat.Activity.TingGoUsersList;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.assist.classes.Tingno;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.runnables.PicDownload;
import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;

public class DeveloperTingsMenuActivity extends Activity {
    private RecyclerView recyclerView;
    private LocalDB db;
    private boolean pause;

    private DatabaseReference receive;

    private final ValueEventListener tingsListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            try {
                if (snapshot.exists()) {
                    for (DataSnapshot user : snapshot.getChildren()) {
                        final String id = user.getKey();
                        if (id == null || id.equals(Params.TING_BACK))
                            continue;
                        String name = user.child(Params.NAME).getValue(String.class);
                        if (user.hasChild(Params.TING)) {
                            for (DataSnapshot tings : user.child(Params.TING).getChildren()) {
                                String time = tings.getKey() + "";
                                String text = tings.getValue() + "";
                                Tingno ting = new Tingno(id, text, name, time);
                                if (db.tingNotExist(ting)) {
                                    db.addTingNoti(ting);
                                    db.addDeveloperTingCount(id, 1);
                                }
                                if (pause) {
                                    new OldNotificationMaker(DeveloperTingsMenuActivity.this).TingNoNotify(name, text, Intents.DeveloperTingNo(getApplicationContext(),ting.getUid()));
                                }
                                tings.getRef().removeValue();
                            }
                        }
                        update();
                    }
                }
            } catch (Exception e) {
                Issue.set(e, DeveloperTingsMenuActivity.class.getName());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    private final ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            try {
                if (snapshot.exists()) {
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        TingUser user = shot.getValue(TingUser.class);
                        if (user == null)
                            continue;
                        db.addTingUser(user);
                        new Thread(new PicDownload(getApplicationContext(), Uri.parse(user.getImagePath()), user.getUid()) {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        }).start();
                        update();
                    }
                }
            } catch (Exception e) {
                Issue.set(e, DeveloperTingsMenuActivity.class.getName());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinggo_menu_dark);
        setTitle("Developer TingNo");
        db = new LocalDB(getApplicationContext());

        recyclerView = findViewById(R.id.tinggo_user_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CloudDB.Tinggo.tingUser().addValueEventListener(userListener);

        receive = CloudDB.TingNo.getTingNoDeveloperReceive();
        receive.addValueEventListener(tingsListener);

        findViewById(R.id.add_chat).setOnClickListener(v -> {
            Basics.toasting(getApplicationContext(), "Not yet Working...");
            startActivity(new Intent(getApplicationContext(), TingGoUsersList.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause = true;
    }

    private void update() {
        recyclerView.setAdapter(new DeveloperTingNoMenuAdapter(db) {
            @Override
            protected boolean onLongClicked(String id) {
                db.deleteUser(id);
                db.deleteDeveloperTingCount(id);
                update();
                return true;
            }

            @Override
            public void onClick(String id) {
                startActivity(new Intent(getApplicationContext(), DeveloperTingsChatActivity.class).putExtra(Params.ID,id));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receive.removeEventListener(tingsListener);
    }
}
