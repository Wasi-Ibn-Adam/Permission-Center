package com.wasitech.permissioncenter.java.com.wasitech.music.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.os.Build;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.media.session.MediaButtonReceiver;

import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.notify.AssistNotification;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.music.OnlinePlayer;
import com.wasitech.music.activity.MusicPlayer;
import com.wasitech.music.service.SongService;

import static com.wasitech.music.OnlinePlayer.O_CLOSE;
import static com.wasitech.music.OnlinePlayer.O_PAUSE;
import static com.wasitech.music.OnlinePlayer.O_PLAY;
import static com.wasitech.music.service.SongService.CLOSE;
import static com.wasitech.music.service.SongService.LOOP;
import static com.wasitech.music.service.SongService.NEXT;
import static com.wasitech.music.service.SongService.PAUSE;
import static com.wasitech.music.service.SongService.PLAY;
import static com.wasitech.music.service.SongService.PREV;

public class MusicNotification extends AssistNotification {

    public static final String CHANNEL_ID = "Music";

    public MusicNotification(Context context) {
        super(context);
        ComponentName mediaButtonReceiver = new ComponentName(context, MediaButtonReceiver.class);
        mediaSession = new MediaSessionCompat(context, "Assistant", mediaButtonReceiver, null);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected int importance() {
        return NotificationManager.IMPORTANCE_DEFAULT;
    }

    @Override
    protected String channelId() {
        return CHANNEL_ID;
    }

    private PendingIntent MusicIntent() {
        return PendingIntent.getActivity(context, 6,
                new Intent(context, MusicPlayer.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                //Intents.AudioShow(context)
                , 0);
    }

    private PendingIntent getSongIntent(int code) {
        Intent intent;
        switch (code) {
            default:
            case NEXT: {
                intent = SongService.BroadCastIntents.next(context);
                break;
            }
            case PREV: {
                intent = SongService.BroadCastIntents.back(context);
                break;
            }
            case PAUSE: {
                intent = SongService.BroadCastIntents.pause(context);
                break;
            }
            case PLAY: {
                intent = SongService.BroadCastIntents.play(context);
                break;
            }
            case CLOSE: {
                intent = SongService.BroadCastIntents.close(context);
                break;
            }
            case LOOP: {
                intent = SongService.BroadCastIntents.nMode(context);
                break;
            }
            case O_PAUSE: {
                intent = OnlinePlayer.pause(context);
                break;
            }
            case O_PLAY: {
                intent = OnlinePlayer.play(context);
                break;
            }
            case O_CLOSE: {
                intent = OnlinePlayer.close(context);
                break;
            }
        }
        return PendingIntent.getBroadcast(context, code, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }


    public MediaSessionCompat mediaSession;


    private MediaSessionCompat.Token getMediaSession(int state) {
        mediaSession.setMetadata(getMediaMetaData());
        mediaSession.setCallback(SongService.callback);
        mediaSession.setPlaybackState(getPlayBackStateCompat(state));
        mediaSession.setActive(true);
        mediaSession.setMediaButtonReceiver(getMediaButtonReceiver());
        return mediaSession.getSessionToken();
    }

    private PendingIntent getMediaButtonReceiver() {
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setClass(context.getApplicationContext(), MediaButtonReceiver.class);
        return PendingIntent.getBroadcast(context.getApplicationContext(), 0, mediaButtonIntent, 0);
    }

    // SONG INFO
    private MediaMetadataCompat getMediaMetaData() {
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
        builder.putString(MediaMetadata.METADATA_KEY_TITLE, SongService.name);
        builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, SongService.player.getDuration());

        //Notification icon in card
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON,
                BitmapFactory.decodeResource(context.getResources(), ProcessApp.getPic()));
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, BitmapFactory.decodeResource(context.getResources(), R.drawable.bg7));

        //lock screen icon for pre lollipop
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ART,
                BitmapFactory.decodeResource(context.getResources(), ProcessApp.getPic()));
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, SongService.name);

        return builder.build();
    }

    private PlaybackStateCompat getPlayBackStateCompat(int state) {
        PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder();
        if (state == PlaybackStateCompat.STATE_PLAYING) {
            builder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PAUSE |
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
        } else {
            builder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PLAY |
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
        }
        builder.setState(state, SongService.player.getCurrentPosition(), 1);
        return builder.build();
    }

    public Notification MusicLyPlaying(int pro) {
        return new NotificationCompat.Builder(context, CHANNEL_ID.toLowerCase())
                .setSmallIcon(R.drawable.notification)
                .setLargeIcon(OldNotificationMaker.randomPics(context, SongService.name.length()))
                .setContentTitle(SongService.name)
                .addAction(R.drawable.prev, "Previous", getSongIntent(PREV))
                .addAction(R.drawable.pause, "Pause", getSongIntent(PAUSE))
                .addAction(R.drawable.next, "Next", getSongIntent(NEXT))
                .addAction(SongService.getImg(), "Loop", getSongIntent(LOOP))
                .addAction(R.drawable.the_end, "Close", getSongIntent(CLOSE))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(getMediaSession(PlaybackStateCompat.STATE_PLAYING)))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setProgress(100, pro, false)
                .setContentIntent(MusicIntent())
                .setOnlyAlertOnce(true)
                .setSilent(true)
                .setOngoing(true)
                .build();
    }

    public Notification MusicLyPaused(int pro) {
        return new NotificationCompat.Builder(context, CHANNEL_ID.toLowerCase())
                .setSmallIcon(R.drawable.notification)
                .setLargeIcon(OldNotificationMaker.randomPics(context, SongService.name.length()))
                .setContentTitle(SongService.name)
                .addAction(R.drawable.prev, "Previous", getSongIntent(PREV))
                .addAction(R.drawable.play, "Play", getSongIntent(PLAY))
                .addAction(R.drawable.next, "Next", getSongIntent(NEXT))
                .addAction(SongService.getImg(), "Loop", getSongIntent(LOOP))
                .addAction(R.drawable.the_end, "Close", getSongIntent(CLOSE))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(getMediaSession(PlaybackStateCompat.STATE_PAUSED)))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)
                .setProgress(100, pro, false)
                .setContentIntent(MusicIntent())
                .setSilent(true)
                .setOngoing(false)
                .build();
    }

    public Notification OnlinePlaying() {
        return new NotificationCompat.Builder(context, CHANNEL_ID.toLowerCase())
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Online Song")
                .addAction(R.drawable.pause, "Pause", getSongIntent(O_PAUSE))
                .addAction(R.drawable.the_end, "Close", getSongIntent(O_CLOSE))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1)
                        .setMediaSession(getOnlineMediaSession(PlaybackStateCompat.STATE_PLAYING)))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setProgress(100, 0, true)
                .setOnlyAlertOnce(true)
                .setSilent(true)
                .setOngoing(true)
                .build();
    }

    public Notification OnlinePaused() {
        return new NotificationCompat.Builder(context, CHANNEL_ID.toLowerCase())
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Online Song")
                .addAction(R.drawable.play, "Play", getSongIntent(O_PLAY))
                .addAction(R.drawable.the_end, "Close", getSongIntent(O_CLOSE))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1)
                        .setMediaSession(getOnlineMediaSession(PlaybackStateCompat.STATE_PAUSED)))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)
                .setProgress(100, 0, false)
                .setSilent(true)
                .setOngoing(false)
                .build();
    }

    private PlaybackStateCompat getOnlinePlayBackStateCompat(int state) {
        PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder();
        if (state == PlaybackStateCompat.STATE_PLAYING) {
            builder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PAUSE);
        } else {
            builder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PLAY);
        }
        return builder.build();
    }

    private MediaSessionCompat.Token getOnlineMediaSession(int state) {
        mediaSession.setCallback(OnlinePlayer.getOnlineMediaSessionCallBack());
        mediaSession.setPlaybackState(getOnlinePlayBackStateCompat(state));
        mediaSession.setMediaButtonReceiver(getMediaButtonReceiver());
        return mediaSession.getSessionToken();
    }

}
