package com.wasitech.permissioncenter.java.com.wasitech.music.runnable;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.classes.Song;
import com.wasitech.music.classes.Sort;

import java.util.Collections;

public abstract class AudioSongRunnable implements Runnable {
    private final Context context;

    public AudioSongRunnable(Context context) {
        this.context = context;
    }

    public abstract void onComplete();

    @Override
    public void run() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection1;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                projection1 = new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.SIZE
                };
                for (int i = 0; i < 2; i++) {
                    try {
                        Cursor cursor = context.getContentResolver()
                                .query(audioInternal(i == 0), projection1, selection, null, null);
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
                        Issue.print(e,AudioSongRunnable.class.getName());
                    }
                }
            }
            else {
                projection1 = new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.SIZE
                };
                for (int i = 0; i < 2; i++) {
                    try {
                        Cursor cursor = context.getContentResolver().query(audioInternal(i == 0), projection1, selection, null, null);
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
                        Issue.print(e,AudioSongRunnable.class.getName());
                    }
                }
            }
        } catch (Exception e) {
            Issue.print(e,AudioSongRunnable.class.getName());
        }
        Collections.sort(ProcessApp.saList, Sort.Compare());
        onComplete();
    }

    private void sListAdder(Song song) {
        if(song.getDuration()<1000)return;
        boolean find=false;
        for(Song s:ProcessApp.saList){
            find=s.areExactlySame(song);
            if(find) break;
        }
        if(!find){
            ProcessApp.saList.add(song);
        }
    }

    private Uri audioInternal(boolean id) {
        if (id) return MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        else return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    public static long getDuration(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(path);
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return durationStr!=null?Long.parseLong(durationStr):0;
    }
    public static String getArtist(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(path);
        return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
    }
    public static String getAlbum(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(path);
        return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
    }


    public static long LongFormation(String s){
        try {
            return Long.parseLong(s != null ? s : "0");
        }
        catch (Exception e){
            Issue.print(e,AudioSongRunnable.class.getName());
        }
        return 0;
    }
    private int IntFormation(String s){
        try {
            return Integer.parseInt(s!=null?s:"0");
        }
        catch (Exception e){
            Issue.print(e,getClass().getName());
        }
        return 0;
    }
}