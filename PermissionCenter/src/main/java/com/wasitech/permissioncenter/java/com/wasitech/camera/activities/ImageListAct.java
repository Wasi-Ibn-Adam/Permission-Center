package com.wasitech.permissioncenter.java.com.wasitech.camera.activities;

import android.media.MediaScannerConnection;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wasitech.assist.R;
import com.wasitech.assist.command.family.Answers;
import com.wasitech.assist.popups.ThemedPopUps;
import com.wasitech.basics.ItemsLongClickPopUp;
import com.wasitech.basics.Storage;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.camera.adapter.ImagesAdapter;
import com.wasitech.camera.classes.Images;
import com.wasitech.theme.Theme;

import java.util.ArrayList;

public class ImageListAct extends AssistCompatActivity {
    public static ArrayList<Images> list;
    private RecyclerView view;
    private FloatingActionButton refresh;

    public ImageListAct() {
        super(R.layout.act_general_list_refresh);
    }

    @Override
    protected String titleBarText() {
        return "Assist Images";
    }

    @Override
    public void setViews() {
        try{
            setBack();
            view = findViewById(R.id.recyclerView);
            refresh=findViewById(R.id.refresh);
        }catch (Exception e){
            Issue.print(e,getClass().getName());
        }
    }

    @Override
    public void setTheme() {
        try{
            Theme.Activity(this);
        }catch (Exception e){
            Issue.print(e,getClass().getName());
        }
    }

    @Override
    public void setValues() {
        try{
            view.setHasFixedSize(true);
            view.setLayoutManager(new GridLayoutManager(this,3, RecyclerView.VERTICAL,false));
            list = Storage.getImgList();
            refresh.setImageResource(R.drawable.refresh);
            setAdapter();
        }catch (Exception e){
            Issue.print(e,getClass().getName());
        }
    }

    @Override
    public void setActions() {
        try{
            refresh.setOnClickListener(v->{
                ArrayList<Images> temp = Storage.getImgList();
                if (temp != null && temp.size() != list.size()) {
                    list = temp;
                    setAdapter();
                }
            });
        }catch (Exception e){
            Issue.print(e,getClass().getName());
        }
    }

    @Override
    public void setExtras() {
    }

    @Override
    public void setPermission() {

    }


    private void setAdapter() {
        ImagesAdapter adapter=new ImagesAdapter(list) {
            @Override
            protected boolean OnLongClickListener(Images img,int pos) {
                new ItemsLongClickPopUp(ImageListAct.this) {
                    @Override
                    protected int setActionImg() { return R.drawable.img; }

                    @Override
                    protected void OnDetail() {
                        try {
                            StringBuilder b = new StringBuilder();
                            {
                                b.append("Name: ").append(img.getName());
                                b.append("\nLast Modified: ").append(img.getDate());
                                b.append("\nPath: ").append(img.getPath());
                            }
                            ThemedPopUps.BackgroundPopup(ImageListAct.this, b.toString());
                        } catch (Exception e) {
                            Issue.print(e, getClass().getName());
                        }
                        dismiss();
                    }

                    @Override
                    protected void OnAction() {
                        onClick(pos);
                        dismiss();
                    }

                    @Override
                    protected void OnDelete() {
                        try {
                            MediaScannerConnection.scanFile(getApplicationContext(),
                                    new String[]{img.getPath()},
                                    null,
                                    (p, uri) -> {
                                        try {
                                            list.remove(img);
                                            getContentResolver().delete(uri, null, null);
                                            new Handler(Looper.getMainLooper()).post(ImageListAct.this::setAdapter);
                                        } catch (Exception e) {
                                            Basics.toasting(
                                                    ImageListAct.this,
                                                    Answers.ASSISTANT.NAME_ONLY() + " has No Access to Delete it.");
                                        }
                                    });
                        } catch (Exception e) {
                            Issue.print(e, ImageListAct.class.getName());
                        }
                        dismiss();
                    }

                    @Override
                    protected void OnShare() {
                        Basics.Send.share(ImageListAct.this,img.getPath());
                        dismiss();
                    }
                };
                return true;
            }

            @Override
            protected void listSize(int size) {
                EmptyLay( size == 0);
            }

            @Override
            protected void onClick(int pos) {
                startActivity(ImageViewerAct.Intents.showPic(getApplicationContext(),pos));
            }
        };
        view.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }
}
