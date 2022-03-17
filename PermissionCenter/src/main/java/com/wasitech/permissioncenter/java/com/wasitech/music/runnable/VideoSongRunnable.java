package com.wasitech.permissioncenter.java.com.wasitech.music.runnable;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.classes.Song;
import com.wasitech.music.classes.Sort;

import java.util.Collections;

import static com.wasitech.music.runnable.AudioSongRunnable.LongFormation;

public abstract class VideoSongRunnable implements Runnable {
    private final Context context;

    public abstract void onComplete();

    public VideoSongRunnable(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        String[] projection;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                projection = new String[]{
                        MediaStore.Video.Media._ID,
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.SIZE
                };
                for (int i = 0; i < 2; i++) {
                    try {
                        Cursor cursor = context.getContentResolver().query(videoInternal(i == 0), projection, null, null, null);
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                Song song=new Song(
                                        Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)))
                                        ,cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                                        ,cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                                        , LongFormation(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)))
                                );
                                sListAdder(song);
                            }
                            cursor.close();
                        }
                    } catch (Exception e) {
                        Issue.print(e,VideoSongRunnable.class.getName());
                    }
                }
            } else {
                projection = new String[]{
                        MediaStore.Video.Media._ID,
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.SIZE
                };
                for (int i = 0; i < 2; i++) {
                    try {
                        Cursor cursor = context.getContentResolver().query(videoInternal(i == 0), projection, null, null, null);
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                    Song song=new Song(
                                            Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)))
                                            ,cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                                            ,cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                                            ,LongFormation(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)))
                                    );
                                    sListAdder(song);
                            }
                            cursor.close();
                        }
                    } catch (Exception e) {
                        Issue.print(e,VideoSongRunnable.class.getName());
                    }
                }
            }
        } catch (Exception e) {
            Issue.print(e,VideoSongRunnable.class.getName());
        }
        Collections.sort(ProcessApp.svList, Sort.Compare());
        onComplete();
    }

    private void sListAdder(Song song) {
        if(song.getDuration()<1000)return;
        boolean find=false;
        for(Song s:ProcessApp.svList){
            find=s.areExactlySame(song);
            if(find) break;
        }
        if(!find){
            ProcessApp.svList.add(song);
        }
    }


    private Uri videoInternal(boolean id) {
        if (id) return MediaStore.Video.Media.INTERNAL_CONTENT_URI;
        else return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }
}
