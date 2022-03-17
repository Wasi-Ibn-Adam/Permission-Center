package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageException;
import com.wasitech.assist.R;
import com.wasitech.ting.Chat.Adapater.TingGoUserListAdapter;
import com.wasitech.ting.Noti.DeveloperTingsChatActivity;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.assist.runnables.PicDownload;
import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;

import java.util.ArrayList;
import java.util.Collections;

public class TingGoUsersList extends BaseCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<TingUser> list;
    private LocalDB db;

    public TingGoUsersList() {
        super(R.layout.activity_tinggo_user_dark, R.layout.activity_tinggo_user_dark);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();

        db = new LocalDB(getApplicationContext());
        recyclerView = findViewById(R.id.tinggo_user_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerView();
    }

    private void setRecyclerView() {
        list = db.getTingUserList();
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            findViewById(R.id.empty_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.empty_layout).setVisibility(View.GONE);
            recyclerView.setAdapter(null);
            Collections.sort(list, TingUser.StuNameComparator);
            recyclerView.setAdapter(new TingGoUserListAdapter(getApplicationContext(), list) {
                @Override
                public void onClick(View v) {
                    int itemPosition = recyclerView.getChildLayoutPosition(v);
                    TingUser user=list.get(itemPosition);
                    startActivity(new Intent(getApplicationContext(), DeveloperTingsChatActivity.class).putExtra(Params.ID,user.getUid()));

                    //TingGoChatActivity.setUser(list.get(itemPosition));
                    //startActivity(new Intent(TingGoUsersList.this, TingGoChatActivity.class));
                    finish();
                }
            });
        }
    }

    private void refreshList() {

        CloudDB.Tinggo.tingUser()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            if (snapshot.exists()) {
                                for (DataSnapshot shot : snapshot.getChildren()) {
                                    TingUser user=shot.getValue(TingUser.class);
                                    if(user==null)
                                        continue;
                                    if(db.userNotExist(user.getUid())) {
                                        db.addTingUser(user);
                                        downloadPic(user.getUid(),user.getImagePath());
                                        setRecyclerView();
                                    }
                                    else if(!db.getUserImagePath(user.getUid()).equals(user.getImagePath())){
                                        downloadPic(user.getUid(),user.getImagePath());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Issue.print(e,TingGoUsersList.class.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void elsePart(String id) {
        CloudDB.PicCenter.getProfilePicStorage(id).getBytes(5 * 1024 * 104)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        if (db.picExist(id))
                            db.updatePic(id, task.getResult());
                        else
                            db.savePic(id, task.getResult());
                    setRecyclerView();
                })
                .addOnFailureListener(e -> {
                    if (e instanceof StorageException && ((StorageException) e).getErrorCode() == StorageException.ERROR_OBJECT_NOT_FOUND) {
                        db.deletePic(id);
                    }
                    setRecyclerView();
                });
    }
    private void downloadPic(String uid,String path){
        new Thread(new PicDownload(getApplicationContext(), Uri.parse(path),uid) {
            @Override
            public void onSuccess() {
                setRecyclerView();
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Exception e) {

            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ting_user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refresh_user) {
            refreshList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.normalize();
    }
}