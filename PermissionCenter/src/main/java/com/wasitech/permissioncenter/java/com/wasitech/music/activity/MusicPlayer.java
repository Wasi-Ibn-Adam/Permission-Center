package com.wasitech.permissioncenter.java.com.wasitech.music.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.triggertrap.seekarc.SeekArc;
import com.wasitech.assist.R;
import com.wasitech.assist.services_receivers.MyAdmin;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.OnlinePlayer;
import com.wasitech.music.classes.Song;
import com.wasitech.music.interfaces.PlayerActions;
import com.wasitech.music.pop.UiChangePop;
import com.wasitech.music.service.SongService;
import com.wasitech.theme.Theme;

import de.hdodenhof.circleimageview.CircleImageView;
import me.tankery.lib.circularseekbar.CircularSeekBar;

@SuppressLint("SetTextI18n")
public class MusicPlayer extends AssistCompatActivity implements PlayerActions {

    // IN ALL UI's
    private CircleImageView songImg;
    private ImageView visualizer;
    private ImageButton play, prev, next, mode, more, allSongBtn;
    private TextView song, artist, curTime, totTime, allSongTxt;

    private UiChangePop pop;

    // IN Different UI's
    private SeekBar seekBarLine;
    private SeekArc seekArc;
    private CircularSeekBar seekBarCircle;

    private ImageButton lock;
    private boolean locked = false;
    private LinearLayout hideLay;

    private long count = 0;
    private int maxDuration = 0;

    public static final String MUSIC_PLAYER = "player_ui";

    private static int getUi() {
        switch (ProcessApp.getPref().getInt(MUSIC_PLAYER, 0)) {
            default:
            case 0:
                return R.layout.act_mp_0;
            case 1:
                return R.layout.act_mp_1;
            case 2:
                return R.layout.act_mp_2;
        }
    }

    public MusicPlayer() {
        super(MusicPlayer.getUi());
    }

    @Override
    public void setViews() {
        try {
            SongService.actions = this;
            allSongBtn = findViewById(R.id.all_song_btn);
            allSongTxt = findViewById(R.id.all_song_txt);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        try {
            songImg = findViewById(R.id.song_img);
            totTime = findViewById(R.id.total_time);
            curTime = findViewById(R.id.cur_time);
            play = findViewById(R.id.play_btn);
            prev = findViewById(R.id.prev_btn);
            next = findViewById(R.id.next_btn);
            mode = findViewById(R.id.mode_btn);
            more = findViewById(R.id.more_btn);
            song = findViewById(R.id.curr_song_txt);
            artist = findViewById(R.id.artist_txt);
            visualizer = findViewById(R.id.visual_img);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        try {
            seekBarCircle = findViewById(R.id.seekBarCircle);
            lock = findViewById(R.id.lock_btn);
            hideLay = findViewById(R.id.hide_lay);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        try {
            seekBarLine = findViewById(R.id.seekBar);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        try {
            seekArc = findViewById(R.id.seekArc);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues() {
        ColorFilter cf = new PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        songImg.setColorFilter(cf);

        if (lock != null) {
            if (locked)
                lock.setImageResource(R.drawable.lock_open);
            else
                lock.setImageResource(R.drawable.password);
        }
        if (seekBarCircle != null) {
            seekBarCircle.setCircleProgressColor(Color.WHITE);
            seekBarCircle.setPointerColor(Color.WHITE);
        }
    }

    @Override
    public void setExtras() {
        try {
            if (SongService.player != null) {
                Song s = SongService.currSong();
                if (s != null) songInitialized(s);
                setSeekBarMax(SongService.player.getDuration() / 1000);
                if (SongService.player.isPlaying()) songPlayed();
                else songPaused();
                playerModeChanged(SongService.getImg());
            }
            else {
                String receivedAction = getIntent().getAction();
                if (Intent.ACTION_SEND.equals(receivedAction)) {
                    String t = getIntent().getStringExtra(Intent.EXTRA_TEXT);
                    if (t != null)
                        new OnlinePlayer(MusicPlayer.this).setPlayerOnline(t);
                    else
                        sendBroadcast(SongService.BroadCastIntents.initialize(this, ProcessApp.saList));
                } else
                    sendBroadcast(SongService.BroadCastIntents.initialize(this, ProcessApp.saList));
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setTheme() {
    }

    @Override
    public void setActions() {
        try {
            play.setOnClickListener(v -> {
                if (SongService.player != null) {
                    if (SongService.player.isPlaying())
                        sendBroadcast(SongService.BroadCastIntents.pause(this));
                    else
                        sendBroadcast(SongService.BroadCastIntents.play(this));
                } else {
                    sendBroadcast(SongService.BroadCastIntents.start(this, ProcessApp.saList, 0));
                }
            });
            next.setOnClickListener(v -> {
                if (SongService.player != null)
                    sendBroadcast(SongService.BroadCastIntents.next(this));
                else {
                    sendBroadcast(SongService.BroadCastIntents.start(this, ProcessApp.saList, 0));
                }
            });
            prev.setOnClickListener(v -> {
                if (SongService.player != null)
                    sendBroadcast(SongService.BroadCastIntents.back(this));
                else {
                    sendBroadcast(SongService.BroadCastIntents.start(this, ProcessApp.saList, 0));
                }
            });
            mode.setOnClickListener(v -> sendBroadcast(SongService.BroadCastIntents.nMode(this)));
            more.setOnClickListener(v -> {

                Context wrapper = new ContextThemeWrapper(MusicPlayer.this, Theme.getCurDropDown());
                PopupMenu popupMenu = new PopupMenu(wrapper, more);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.music_more_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.share) {
                        Song s = SongService.currSong();
                        if (s != null)
                            Basics.Send.share(MusicPlayer.this, s.getPath());
                    } else if (menuItem.getItemId() == R.id.change_ui) {
                        pop = new UiChangePop(MusicPlayer.this) {
                            @Override
                            protected void updateNow() {
                                recreate();
                            }
                        };
                    }
                    return true;
                });
                // Showing the popup menu
                popupMenu.show();
            });
            setSeekBarListeners();
            setLockListener();
            setAllLayoutListeners();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setPermission() {

    }

// Lock action function onward

    private void setLockListener() {
        if (lock == null) return;
        lock.setOnClickListener(v -> {
            locked = !locked;
            if (locked) {
                hideLay.setVisibility(View.GONE);
                play.setVisibility(View.GONE);
                lock.setImageResource(R.drawable.lock_open);
                allSongBtn.setVisibility(View.GONE);
                allSongTxt.setVisibility(View.GONE);
                if (lockWithoutPrompt())
                    startLockTask();
            } else {
                hideLay.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                lock.setImageResource(R.drawable.password);
                allSongBtn.setVisibility(View.VISIBLE);
                allSongTxt.setVisibility(View.VISIBLE);
                stopLockTask();
            }

        });
    }

    private boolean lockWithoutPrompt() {
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName admin = new ComponentName(this, MyAdmin.class);

        if (myDevicePolicyManager.isDeviceOwnerApp(this.getPackageName())) {
            String[] packages = {this.getPackageName()};
            myDevicePolicyManager.setLockTaskPackages(admin, packages);
        } else
            return false;

        return myDevicePolicyManager.isLockTaskPermitted(this.getPackageName());

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locked) {
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_NO_USER_ACTION);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (locked && keyCode == KeyEvent.KEYCODE_HOME) return false;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (locked) return;
        if (pop != null && pop.isShowing()) return;
        super.onBackPressed();
    }

    // Lock action functions ends

    // All songs list functions onward
    private void setAllLayoutListeners() {
        try {
            allSongTxt.setOnClickListener(songListener());
            allSongBtn.setOnClickListener(songListener());
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private View.OnClickListener songListener() {
        return v -> startActivity(MusicListAct.Intents.AudioSelected(MusicPlayer.this));
    }

    // All songs list  functions ends

    // Seek bar functions onward
    private void setSeekBar(int pro) {
        int pos = Math.max(pro, 0);
        if (seekBarCircle != null)
            seekBarCircle.setProgress(pos);
        if (seekBarLine != null)
            seekBarLine.setProgress(pos);
        if (seekArc != null) {
            pro = pos * 100;
            seekArc.setProgress((pro) / maxDuration);
            curTime.setText(Format.secToTime(pos));
        }
    }

    private void setSeekBarMax(int max) {
        try {

            if (seekBarCircle != null)
                seekBarCircle.setMax(max);
            else if (seekBarLine != null)
                seekBarLine.setMax(max);
            if (seekArc != null)
                maxDuration = max;
            totTime.setText(Format.secToTime(max));
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void setSeekBarListeners() {
        if (seekBarCircle != null)
            seekBarCircle.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
                @Override
                public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                    if (SongService.player == null) {
                        seekBarCircle.setProgress(0);
                        curTime.setText(Format.secToTime(0));
                        return;
                    }
                    if (fromUser) {
                        if (!locked) {
                            count = (long) (progress * 1000);
                            SongService.player.seekTo((int) count);
                        } else {
                            progress = (float) count / 1000;
                            seekBarCircle.setProgress(Math.max(progress, 0));
                        }
                    }
                    curTime.setText(Format.secToTime((int) progress));
                }

                @Override
                public void onStopTrackingTouch(CircularSeekBar seekBar) {

                }

                @Override
                public void onStartTrackingTouch(CircularSeekBar seekBar) {

                }
            });
        if (seekBarLine != null)
            seekBarLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (SongService.player == null) return;
                    count = progress * 1000;
                    if (fromUser) {
                        SongService.player.seekTo((int) count);
                    }
                    curTime.setText(Format.secToTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        if (seekArc != null)
            seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
                @Override
                public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {
                    if (SongService.player == null) return;
                    if (fromUser) {
                        SongService.player.seekTo(maxDuration * progress * 10);
                        curTime.setText(Format.secToTime((progress * maxDuration) / 100));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekArc seekArc) {

                }

                @Override
                public void onStopTrackingTouch(SeekArc seekArc) {

                }
            });
    }

    // Seek bar functions ends

    private void setVisualizer(boolean t) {
        if (t) {
            Glide.with(this).asGif().load(R.drawable.loading_3).into(visualizer);
        } else {
            visualizer.setImageResource(0);
        }
    }

// Player functions onward

    @Override
    public void playerModeChanged(int res) {
        mode.setForeground(ContextCompat.getDrawable(MusicPlayer.this, res));
    }

    @Override
    public void songPlayed() {
        try {
            play.setForeground(ContextCompat.getDrawable(this, R.drawable.pause));
            setVisualizer(true);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void songPaused() {
        try {
            play.setForeground(ContextCompat.getDrawable(this, R.drawable.play));
            setVisualizer(false);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void songStop() {
        try {
            play.setForeground(ContextCompat.getDrawable(this, R.drawable.play));
            setVisualizer(false);
            //setSeekBarMax(0);
            setSeekBar(0);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void songCompleted() {
        play.setForeground(ContextCompat.getDrawable(this, R.drawable.play));
        setVisualizer(false);
    }

    @Override
    public void songChanged(Song song) {
        this.song.setText(song.getTitle().trim());
        this.artist.setText(song.getArtist().trim());
        setSeekBarMax((int) song.getDuration() / 1000);
        setVisualizer(true);
        play.setForeground(ContextCompat.getDrawable(this, R.drawable.pause));
    }

    @Override
    public void songInitialized(Song song) {
        this.song.setText(song.getTitle().trim());
        this.artist.setText(song.getArtist().trim());
        setSeekBarMax((int) song.getDuration() / 1000);
        setVisualizer(false);
        play.setForeground(ContextCompat.getDrawable(this, R.drawable.play));
    }

    @Override
    public void progress(int max, int cur) {
        setSeekBarMax(max);
        setSeekBar(cur);
    }

    public static class Intents {
        public static Intent openPlayer(Context context) {
            return new Intent(context, MusicPlayer.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
    }

}

