package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.assist.R;
import com.wasitech.ting.Chat.Adapater.TingGoChatAdapter;
import com.wasitech.ting.Chat.Module.Ting;
import com.wasitech.ting.Chat.Module.UserDetailPopUp;
import com.wasitech.ting.Chat.Notification.MessageCenter;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.assist.classes.TingUser;

import java.util.ArrayList;

import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;

public class TingGoChatActivity extends BaseCompatActivity {
    private static TingUser user;
    private RecyclerView recyclerView;
    private LocalDB db;
    private ArrayList<Ting> list;
    private DatabaseReference get;
    private MessageCenter center;
    private EditText msg;
    private ImageButton cam, send;
    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    Ting ting = shot.getValue(Ting.class);
                    if (ting == null)
                        continue;
                    if (ting.getTinggo() != null) {
                        if (db.tinggoNotExist(ting)) {
                            db.addTingGo(ting);
                            setRecyclerView();
                        }
                        shot.getRef().child("tinggo").removeValue();
                    } else {
                        try {
                            String id = snapshot.getKey();
                            String msgId = shot.getKey();
                            db.tingUpdate(id, msgId, ting.getReaction());
                            setRecyclerView();
                        } catch (Exception e) {
                            Issue.print(e,TingGoChatActivity.class.getName());
                        }
                    }

                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public TingGoChatActivity() {
        super(R.layout.activity_ting_go_chat, R.layout.activity_ting_go_chat);
    }

    public static void setUser(TingUser user) {
        TingGoChatActivity.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setTitle(user.getName());

        recyclerView = findViewById(R.id.recyclerView_tinggo_chat);

        msg = findViewById(R.id.ting_text);
        cam = findViewById(R.id.ting_img);
        send = findViewById(R.id.ting_send);

        db = new LocalDB(getApplicationContext());
        center = new MessageCenter(getApplicationContext());
        get = CloudDB.Tinggo.getTingGoReceive().child(user.getUid());

        Bitmap userMap = Basics.Img.parseBitmap(db.getPic(user.getUid()));

        try {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(user.getName());
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                        | ActionBar.DISPLAY_SHOW_CUSTOM);
                ImageView imageView = new ImageView(actionBar.getThemedContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setOnClickListener((v) -> new UserDetailPopUp(TingGoChatActivity.this, user));
                Glide.with(this).load(userMap).circleCrop().into(imageView);
                ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                        75, 75, Gravity.END | Gravity.CENTER_VERTICAL);
                layoutParams.rightMargin = 40;
                imageView.setLayoutParams(layoutParams);
                actionBar.setCustomView(imageView);
            }
        } catch (Exception e) {
            Issue.print(e,TingGoChatActivity.class.getName());
        }

        db.deleteTingCount(user.getUid());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerView();

        send.setOnClickListener((v) -> {
            try {
                if(Basics.Internet.isInternetJson(this)){
                    String text = msg.getText().toString();
                    if (!text.trim().equals("")) {
                        center.TingGo(user.getUid(), text);
                        setRecyclerView();
                        msg.setText("");
                    }
                }
            } catch (Exception e) {
                Issue.print(e,TingGoChatActivity.class.getName());
            }
        });
        get.addValueEventListener(listener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setRecyclerView() {
        db.deleteTingCount(user.getUid());
        list = db.getTingGoList(user.getUid());
        recyclerView.setAdapter(new TingGoChatAdapter(TingGoChatActivity.this, list,user) {
            @Override
            protected void onReaction(String uid, String msgId, int i) {
                center.TingGoReact(uid, msgId, i);
            }

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        recyclerView.scrollToPosition(list.size() - 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        get.removeEventListener(listener);
        finishAffinity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        get.removeEventListener(listener);
    }
}