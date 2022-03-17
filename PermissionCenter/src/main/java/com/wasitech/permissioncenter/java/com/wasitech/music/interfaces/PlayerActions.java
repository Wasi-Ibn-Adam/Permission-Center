package com.wasitech.permissioncenter.java.com.wasitech.music.interfaces;

import com.wasitech.music.classes.Song;

public interface PlayerActions {
    void songPlayed();

    void songChanged(Song song);

    void songPaused();

    void songCompleted();

    void playerModeChanged(int res);

    void songStop();

    void songInitialized(Song song);

    void progress(int max, int cur);
}

