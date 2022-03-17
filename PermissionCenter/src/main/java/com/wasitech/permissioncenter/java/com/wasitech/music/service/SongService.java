package com.wasitech.permissioncenter.java.com.wasitech.music.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.media.session.MediaButtonReceiver;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.OnlinePlayer;
import com.wasitech.music.classes.Song;
import com.wasitech.music.interfaces.PlayerActions;
import com.wasitech.music.notify.MusicNotification;
import com.wasitech.music.reciever.SongReceiver;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SongService extends Service implements
        AudioManager.OnAudioFocusChangeListener
        , MediaPlayer.OnPreparedListener
        , MediaPlayer.OnCompletionListener {
    private MusicNotification maker;
    public static MediaPlayer player;
    public static int index = -1;
    public static String name;
    public static MediaSessionCompat.Callback callback;
    public static ArrayList<Song> list;
    public static int looping = SongService.ONCE;
    public static PlayerActions actions;
    private boolean init = false, isLastInit = false, wasPlaying = false;

    public static final int NEXT = 0;
    public static final int PREV = 1;
    public static final int PAUSE = 2;
    public static final int PLAY = 3;
    public static final int CLOSE = 4;

    public static final int LOOP = 5;
    public static final int ONCE = 6;
    public static final int RAND = 7;
    public static final int ONLY_THIS = 8;
    public static final int ALL_ONCE = 9;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // AUDIO STOP WHEN HAND-FREE REMOVE OR ANY NOISE OCCUR
    private final BroadcastReceiver noiseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SongService.player != null && SongService.player.isPlaying()) {
                songPause();
            }
        }
    };

    private boolean getAudioFocus() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_GAIN;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        try {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: {
                    if (SongService.player != null && SongService.player.isPlaying()) {
                        wasPlaying = true;
                        songPause();
                    }
                    break;
                }
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
                    if (SongService.player != null) {
                        SongService.player.setVolume(0.3f, 0.3f);
                    }
                    break;
                }
                case AudioManager.AUDIOFOCUS_GAIN: {
                    if (SongService.player != null) {
                        if (!SongService.player.isPlaying() && wasPlaying) {
                            wasPlaying = false;
                            songPlay();
                        }
                        SongService.player.setVolume(1.0f, 1.0f);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        Basics.Log("focus: " + focusChange);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (maker == null)
            maker = new MusicNotification(getApplicationContext());

        SongService.callback = new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                SongService.this.songPlay();
            }

            @Override
            public void onPause() {
                super.onPause();
                SongService.this.songPause();
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                SongService.this.playNextSong();
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                SongService.this.playPrevSong();
            }

        };

        IntentFilter filter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(noiseReceiver, filter);
    }

    public static void setActions(PlayerActions actions) {
        SongService.actions = actions;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Basics.toasting(getApplicationContext(), "Due to Low Memory Assist Music is Shutting Down.");
        stopSelf();
        onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            MediaButtonReceiver.handleIntent(maker.mediaSession, intent);
            String action = intent.getStringExtra("action");
            if (action != null)
                switch (action) {
                    case "start": {
                        setPlayer(intent.getIntExtra("index", 0));
                        break;
                    }
                    case "initial": {
                        init = true;
                        setPlayer(0);
                        break;
                    }
                    case "pause": {
                        songPause();
                        break;
                    }
                    case "play": {
                        songPlay();
                        break;
                    }
                    case "replay": {
                        songReplay();
                        break;
                    }
                    case "next": {
                        playNextSong();
                        break;
                    }
                    case "back": {
                        playPrevSong();
                        break;
                    }
                    case "stop": {
                        songStop();
                        break;
                    }
                    case "close": {
                        songClose();
                        break;
                    }
                    default:
                    case "nmode": {
                        songNextMode();
                        break;
                    }
                }
        } catch (Exception e) {
            Issue.print(e, SongService.class.getName());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void songPlay() {
        try {
            if (SongService.player != null) {
                SongService.player.start();
                PlayerActions(S_PLAY);
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void songReplay() {
        try {
            if (SongService.player != null) {
                SongService.player.seekTo(0);
                SongService.player.start();
                PlayerActions(S_NEW);
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void songPause() {
        try {
            if (SongService.player != null) {
                SongService.player.pause();
                PlayerActions(S_PAUSED);
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void songStop() {
        try {
            if (SongService.player != null) {
                SongService.player.stop();
                SongService.player.seekTo(0);
                PlayerActions(S_STOP);
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void songClose() {
        try {
            stopSelf();
            PlayerActions(S_STOP);
            endPlayer();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void songNextMode() {
        try {
            switch (SongService.looping) {
                case SongService.LOOP: {
                    SongService.looping = SongService.ONCE;
                    Basics.toasting(getApplicationContext(), "Current in Loop");
                    break;
                }
                case SongService.ONCE: {
                    SongService.looping = SongService.RAND;
                    Basics.toasting(getApplicationContext(), "Random");
                    break;
                }
                case SongService.RAND: {
                    SongService.looping = SongService.ALL_ONCE;
                    Basics.toasting(getApplicationContext(), "All Song Once");
                    break;
                }
                case SongService.ALL_ONCE: {
                    SongService.looping = SongService.ONLY_THIS;
                    Basics.toasting(getApplicationContext(), "Only Current");
                    break;
                }
                case SongService.ONLY_THIS: {
                    SongService.looping = SongService.LOOP;
                    Basics.toasting(getApplicationContext(), "Loop All");
                    break;
                }
            }
            PlayerActions(S_MODE);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    // MODE ACTIONS
    private void playNextSong() {
        try {
            if (SongService.list != null) {
                setPlayer((SongService.index == SongService.list.size() - 1) ? 0 : SongService.index + 1);
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void playPrevSong() {
        try {
            if (SongService.list != null) {
                setPlayer(SongService.index == 0 ? SongService.list.size() - 1 : SongService.index - 1);
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void playRandomSong() {
        try {
            if (SongService.list != null) {
                setPlayer(new Random().nextInt(SongService.list.size() - 1));
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void notify(boolean play) {
        try {
            if (play) {
                startForeground(111, maker.MusicLyPlaying(SongService.progress()));
            } else {
                startForeground(111, maker.MusicLyPaused(SongService.progress()));
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (init) return;
        if (isLastInit) {
            isLastInit = false;
            return;
        }
        try {
            SongService.player.start();
            PlayerActions(S_NEW);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        PlayerActions(S_COMPLETE);
        SystemClock.sleep(1000L);
        // loop random repeat code to be written here
        switch (SongService.looping) {
            case SongService.LOOP: {
                playNextSong();
                break;
            }
            case SongService.RAND: {
                playRandomSong();
                break;
            }
            case SongService.ONLY_THIS: {
                notify(false);
                break;
            }
            case SongService.ALL_ONCE: {
                if (SongService.index != SongService.list.size() - 1) {
                    playNextSong();
                } else
                    notify(false);
                break;
            }
            default: {
                setPlayer(SongService.index);
            }
        }
    }

    private void setPlayer(int index) {
        endPlayer();
        if (SongService.list == null) return;
        try {
            Song song = SongService.list.get(index);
            SongService.name = song.getTitle();
            SongService.index = index;
            SongService.player = MediaPlayer.create(getApplicationContext(), Uri.parse(song.getPath()));
            SongService.player.setOnPreparedListener(this);
            SongService.player.setOnCompletionListener(this);
            PlayerActions(init ? S_INIT : S_CHANGED);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void endPlayer() {
        try {
            if (SongService.player != null) {
                if (SongService.player.isPlaying()) {
                    SongService.player.stop();
                }
                SongService.player.release();
                SongService.player = null;
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }


    public static Song currSong() {
        try {
            if (SongService.list != null) {
                return SongService.list.get(SongService.index);
            }
        } catch (Exception e) {
            Issue.print(e, SongService.class.getName());
        }
        return null;
    }

    public static int getImg() {
        try {
            switch (SongService.looping) {
                case SongService.LOOP: {
                    return R.drawable.loop;
                }
                default:
                case SongService.ONCE: {
                    return R.drawable.once;
                }
                case SongService.RAND: {
                    return R.drawable.rand;
                }
                case SongService.ALL_ONCE: {
                    return R.drawable.all_once;
                }
                case SongService.ONLY_THIS: {
                    return R.drawable.only_this;
                }
            }
        } catch (Exception e) {
            Issue.print(e, SongService.class.getName());
        }
        return R.drawable.once;
    }

    public static int progress() {
        if (SongService.player == null) return 0;
        try {
            long cur = SongService.player.getCurrentPosition();
            long tot = SongService.player.getDuration() / 100;
            return (int) (cur / tot);
        } catch (Exception e) {
            Issue.print(e, SongService.class.getName());
        }
        return 0;
    }

    public static final int S_INIT = 0;
    public static final int S_PLAY = 1;
    public static final int S_PAUSED = 2;
    public static final int S_STOP = 3;
    public static final int S_NEW = 4;
    public static final int S_CHANGED = 5;
    public static final int S_COMPLETE = 6;
    public static final int S_MODE = 7;

    private void PlayerActions(int code) {
        try {
            if (SongService.actions != null) {
                switch (code) {
                    case S_INIT: {
                        init = false;
                        isLastInit = true;
                        SongService.actions.songInitialized(SongService.list.get(SongService.index));
                        notify(SongService.player != null && SongService.player.isPlaying());
                        break;
                    }
                    case S_NEW:
                    case S_PLAY: {
                        if (getAudioFocus()) {
                            setTimer();
                            SongService.actions.songPlayed();
                            notify(true);
                        }
                        break;
                    }
                    case S_PAUSED: {
                        endTimer();
                        SongService.actions.songPaused();
                        notify(false);
                        break;
                    }
                    case S_CHANGED: {
                        setTimer();
                        SongService.actions.songChanged(SongService.list.get(SongService.index));
                        notify(true);
                        break;
                    }
                    case S_STOP: {
                        endTimer();
                        SongService.actions.songStop();
                        notify(false);
                        break;
                    }
                    case S_COMPLETE: {
                        endTimer();
                        SongService.actions.songCompleted();
                        notify(false);
                        break;
                    }
                    case S_MODE: {
                        SongService.actions.playerModeChanged(getImg());
                        notify(SongService.player != null && SongService.player.isPlaying());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Issue.print(e, SongService.class.getName());
        }
    }

    private long count = 0;

    private Timer mTimer;

    public void setTimer() {
        try {
            endTimer();
            count = 0;
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (SongService.player != null) {
                            if (SongService.player.isPlaying()) {
                                count++;
                                if (count % 100 == 0)
                                    SongService.actions.progress(
                                            SongService.player.getDuration() / 1000
                                            , SongService.player.getCurrentPosition() / 1000);
                                if (count % 1000 == 0) {
                                    int pos = SongService.player.getCurrentPosition();
                                    // startForeground(111, maker.MusicLyPlaying(pos));
                                    if (count >= SongService.player.getDuration()) {
                                        mTimer.cancel();
                                        mTimer = null;
                                    }
                                    count = 0;
                                }
                            }
                        }
                    } catch (Exception e) {
                        Issue.print(e, getClass().getName());
                    }
                }
            }, 0, 1);

        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void endTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public static class BroadCastIntents {
        public static Intent pause(Context context) {
            return new Intent(context, SongReceiver.class).putExtra("action", "pause");
        }

        public static Intent play(Context context) {
            return new Intent(context, SongReceiver.class).putExtra("action", "play");
        }

        public static Intent start(Context context, ArrayList<Song> list, int index) {
            SongService.list = list;
            return new Intent(context, SongReceiver.class).putExtra("action", "start").putExtra("index", index);
        }

        public static Intent next(Context context) {
            return new Intent(context, SongReceiver.class).putExtra("action", "next");
        }

        public static Intent close(Context context) {
            return new Intent(context, SongReceiver.class).putExtra("action", "close");
        }

        public static Intent replay(Context context) {
            return new Intent(context, SongReceiver.class).putExtra("action", "replay");
        }

        public static Intent back(Context context) {
            return new Intent(context, SongReceiver.class).putExtra("action", "back");
        }

        public static Intent stop(Context context) {
            return new Intent(context, SongReceiver.class).putExtra("action", "stop");
        }

        public static Intent nMode(Context context) {
            return new Intent(context, SongReceiver.class).putExtra("action", "nmode");
        }

        public static Intent initialize(Context context, ArrayList<Song> list) {
            SongService.list = list;
            return new Intent(context, SongReceiver.class).putExtra("action", "initial");
        }

        public static Intent onlinePlayer(Context context, String uri) {
            return new Intent(context, OnlinePlayer.class).putExtra("action", "online")
                    .putExtra("path", uri);
        }
    }

}
