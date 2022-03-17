package com.wasitech.permissioncenter.java.com.wasitech.music.classes;

import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.runnable.AudioSongRunnable;

import java.io.File;

public class Song {
    public static final int MP3 = 0, OGG = 1, AAC = 2, WMA = 3, MP4 = 4, MOV = 5, WMV = 6;
    public static final int AVI = 7, FLV = 8, F4V = 9, SWF = 10, MKV = 11, WEBM = 12;
    private long id;
    private String album, artist;
    private long dateModified;
    private String title, path;
    private int type;
    private long size, duration;


    public boolean areSame(Song s) {
        if (s != null)
            return (id == s.id
                    && type == s.type
                    && size == s.size
                    && duration == s.duration);
        return false;
    }

    public boolean areExactlySame(Song s) {
        if (s != null)
            return (id == s.id
                    && Basics.equals(artist, s.artist)
                    && Basics.equals(album, s.album)
                    && Basics.equals(path, s.path)
                    && Basics.equals(title, s.title)
                    && dateModified == s.dateModified
                    && type == s.type
                    && size == s.size
                    && duration == s.duration
            );
        return false;
    }

    public boolean contains(String data) {
        if (title.toLowerCase().trim().contains(data.toLowerCase().trim()))
            return true;
        if (data.toLowerCase().trim().contains(title.toLowerCase().trim()))
            return true;
        return (data.trim().equalsIgnoreCase(title.trim()));
    }

    public void Log() {
        try {
            Basics.Log("---------------------------------");
            Basics.Log("Id: " + id);
            Basics.Log("Album: " + album);
            Basics.Log("Artist: " + artist);
            Basics.Log("Date Modified: " + getDate());
            Basics.Log("Title: " + title);
            Basics.Log("Path: " + path);
            Basics.Log("Type: " + type);
            Basics.Log("Size: " + size);
            Basics.Log("Duration: " + duration + " ---- " + Format.millisToTime(duration));
            Basics.Log("---------------------------------");
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    public Song(long id, String displayName, String path, long size) {
        this.id = id;
        setDisplayName(displayName);
        setPath(path);
        this.size = size;
    }

    public Song(String id, String fulltitle, String playerUrl, int duration, String description) {
        this.id = Integer.parseInt(id);
        this.title = fulltitle;
        this.path = playerUrl;
        this.type = MP3;
        this.duration = duration;
        this.dateModified = 0;
        this.artist = description;
        if (this.artist == null)
            this.artist = "Unknown Artist";
        this.album = "Unknown Album";
    }

    private void setDisplayName(String displayName) {
        String[] t = displayName.split("\\.");
        this.title = t[0];
        switch (t[1].toLowerCase()) {
            default:
            case "mp3": {
                type = MP3;
                break;
            }
            case "aac": {
                type = AAC;
                break;
            }
            case "ogg": {
                type = OGG;
                break;
            }
            case "wma": {
                type = WMA;
                break;
            }
            case "mp4": {
                type = MP4;
                break;
            }
            case "mov": {
                type = MOV;
                break;
            }
            case "wmv": {
                type = WMV;
                break;
            }
            case "avi": {
                type = AVI;
                break;
            }
            case "flv": {
                type = FLV;
                break;
            }
            case "f4v": {
                type = F4V;
                break;
            }
            case "swf": {
                type = SWF;
                break;
            }
            case "mkv": {
                type = MKV;
                break;
            }
            case "webm": {
                type = WEBM;
                break;
            }
        }
    }

    private void setPath(String path) {
        this.path = path;
        this.dateModified = new File(path).lastModified();
        this.duration = AudioSongRunnable.getDuration(path);
        this.album = AudioSongRunnable.getAlbum(path);
        this.artist = AudioSongRunnable.getArtist(path);
        if (this.artist == null)
            this.artist = "Unknown Artist";
        if (this.album == null)
            this.album = "Unknown Album";
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAlbum(String album) {
        this.album = album;
        if (this.album == null)
            this.album = "Unknown Album";
    }

    public void setArtist(String artist) {
        this.artist = artist;
        if (this.artist == null)
            this.artist = "Unknown Artist";
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isVideo() {
        switch (type) {
            case MP3:
            case WMA:
            case OGG:
            case AAC: {
                return false;
            }
            default: {
                return true;
            }
        }
    }

    public long getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public long getDateModified() {
        return dateModified;
    }

    public String getDate() {
        return Format.longToTime(dateModified);
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public int getType() {
        return type;
    }

    public long getSize() {
        return Math.max(size, 0);
    }

    public long getDuration() {
        return Math.max(duration, 0);
    }

}
