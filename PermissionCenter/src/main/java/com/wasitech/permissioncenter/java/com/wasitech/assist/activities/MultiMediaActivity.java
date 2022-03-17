package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.app.ActivityOptions;
import android.content.Intent;

import androidx.cardview.widget.CardView;

import com.wasitech.assist.R;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.activity.MusicListAct;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionChecker;
import com.wasitech.permission.PermissionGroup;
import com.wasitech.theme.Theme;

public class MultiMediaActivity extends AssistCompatActivity {
    private static final int REC = 0, AUD = 1, VID = 2, PIC = 3;
    private static int ACT;
    private CardView pic, rec, audio, video, alarm;

    public MultiMediaActivity() {
        super(R.layout.activity_multi_media);
    }

    @Override
    protected String titleBarText() {
        return "Assist Multi-Media";
    }


    @Override
    public void setViews() {
        try {
            setBack();
            pic = findViewById(R.id.pics);
            rec = findViewById(R.id.recording);
            audio = findViewById(R.id.audio);
            video = findViewById(R.id.video);
            alarm = findViewById(R.id.all_alarm);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setTheme() {
        try {
            Theme.Activity(this);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues() {
    }

    @Override
    public void setActions() {
        try {
            pic.setOnClickListener(v -> {
                MultiMediaActivity.ACT = PIC;
                permission.request().storage();
            });
            rec.setOnClickListener(v -> {
                MultiMediaActivity.ACT = REC;
                permission.request().storage();
            });
            audio.setOnClickListener(v -> {
                MultiMediaActivity.ACT = AUD;
                permission.request().storage();
            });
            video.setOnClickListener(v -> {
                MultiMediaActivity.ACT = VID;
                permission.request().storage();
            });
            alarm.setOnClickListener(v -> startActivity(Intents.alarmShow(MultiMediaActivity.this), ActivityOptions.makeSceneTransitionAnimation(MultiMediaActivity.this).toBundle()));

        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtras() {

    }

    @Override
    public void setPermission() {
        permission = new PermissionGroup(MultiMediaActivity.this) {
            @Override
            public void requireRationaleAsk(String per, int code) {
                super.requireRationaleAsk(per, code);
                permission.displayRationale(getString(R.string.s_storage) + " " + Permission.Talking.whichNotGranted(MultiMediaActivity.this, code), code);
            }

            @Override
            public void requireSimpleAsk(String per, int code) {
                super.requireSimpleAsk(per, code);
                permission.displaySimple(getString(R.string.s_m_storage) + " " + Permission.Talking.whichNotGranted(MultiMediaActivity.this, code));
            }

            @Override
            public void onDenied(int code) {
                talk("Permission denied can't open it.");
            }

            @Override
            public void onGranted(int code) {
                switch (MultiMediaActivity.ACT) {
                    case REC: {
                        startActivity(MusicListAct.Intents.Recording(MultiMediaActivity.this, MusicListAct.Intents.SHOW)
                                , ActivityOptions.makeSceneTransitionAnimation(MultiMediaActivity.this).toBundle());
                        break;
                    }
                    case AUD: {
                        startActivity(MusicListAct.Intents.Audio(MultiMediaActivity.this, MusicListAct.Intents.SHOW)
                                , ActivityOptions.makeSceneTransitionAnimation(MultiMediaActivity.this).toBundle());
                        break;
                    }
                    case VID: {
                        startActivity(MusicListAct.Intents.Video(MultiMediaActivity.this, MusicListAct.Intents.SHOW)
                                , ActivityOptions.makeSceneTransitionAnimation(MultiMediaActivity.this).toBundle());
                        break;
                    }
                    case PIC: {
                        startActivity(Intents.PictureListActivity(MultiMediaActivity.this),
                                ActivityOptions.makeSceneTransitionAnimation(MultiMediaActivity.this).toBundle());
                        break;
                    }
                }
            }

            @Override
            public void neverAskAgain(int code) {
                try {
                    talk("Grant " + Permission.Talking.whichNotGranted(MultiMediaActivity.this, code) + " permission.");
                    startActivity(Permission.gotoSettings(getPackageName()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } catch (Exception e) {
                    Issue.print(e, PermissionChecker.class.getName());
                }
            }
        };
    }
}
