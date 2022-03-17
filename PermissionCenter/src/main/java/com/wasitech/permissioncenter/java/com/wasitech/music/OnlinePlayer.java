package com.wasitech.permissioncenter.java.com.wasitech.music;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;

import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.classes.Song;
import com.wasitech.music.notify.MusicNotification;
import com.wasitech.music.reciever.SongReceiver;

public class OnlinePlayer {
    private final Context context;
    public static MediaPlayer player;
    private static MusicNotification noti;
    public static final int O_PLAY = 11, O_PAUSE = 12, O_CLOSE = 13;

    public OnlinePlayer(Context c) {
        context = c;
        OnlinePlayer.noti = new MusicNotification(context);
    }

    private static void endPlayer() {
        try {
            if (OnlinePlayer.player != null) {
                if (OnlinePlayer.player.isPlaying()) {
                    OnlinePlayer.player.stop();
                }
                OnlinePlayer.player.release();
                OnlinePlayer.player = null;
            }
        } catch (Exception e) {
            Issue.print(e, OnlinePlayer.class.getName());
        }
    }

    public void setPlayerOnline(String uri) {
        OnlinePlayer.endPlayer();
        try {
            Song s = new Song("0", "Online Song", uri, 0, "Unknown");
            s.Log();
            OnlinePlayer.player = new MediaPlayer();
            OnlinePlayer.player.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            );
            OnlinePlayer.player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            OnlinePlayer.player.setOnPreparedListener(mp -> {
                mp.start();
                noti.getManager().notify(1, noti.OnlinePlaying());
            });
            OnlinePlayer.player.setOnCompletionListener(mp -> {
                noti.getManager().notify(1, noti.OnlinePaused());
            });
            OnlinePlayer.player.setDataSource(context, Uri.parse(uri));
            OnlinePlayer.player.prepareAsync();
        } catch (Exception e) {
            Basics.Log("ERR: " + e.getMessage());
            e.printStackTrace();
            Issue.print(e, getClass().getName());
        }
    }

    public static MediaSessionCompat.Callback getOnlineMediaSessionCallBack() {
        return new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                OnlinePlayer.player.start();
            }

            @Override
            public void onPause() {
                super.onPause();
                OnlinePlayer.player.pause();
            }

            @Override
            public void onStop() {
                super.onStop();
                OnlinePlayer.player.stop();
            }
        };
    }

    public static void pauseSong() {
        if (OnlinePlayer.player != null && OnlinePlayer.player.isPlaying()) {
            OnlinePlayer.player.pause();
            OnlinePlayer.noti.OnlinePaused();
        }
    }

    public static void playSong() {
        if (OnlinePlayer.player != null) {
            OnlinePlayer.player.start();
            OnlinePlayer.noti.OnlinePlaying();
        }
    }

    public static void closeSong() {
        if (OnlinePlayer.player != null) {
            OnlinePlayer.endPlayer();
            OnlinePlayer.noti.getManager().cancel(1);
        }
    }


    public static Intent pause(Context context) {
        return new Intent(context, SongReceiver.class).putExtra("action", "online_pause");
    }

    public static Intent play(Context context) {
        return new Intent(context, SongReceiver.class).putExtra("action", "online_play");
    }

    public static Intent close(Context context) {
        return new Intent(context, SongReceiver.class).putExtra("action", "online_close");
    }
}
