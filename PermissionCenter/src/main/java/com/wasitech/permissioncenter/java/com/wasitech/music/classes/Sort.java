package com.wasitech.permissioncenter.java.com.wasitech.music.classes;

import com.wasitech.basics.app.ProcessApp;

import java.util.Comparator;

public class Sort {
    public static final String SONG_SORT = "song_sort";
    public static final String SONG_ORDER = "song_order";
    public static final int SORT_TYPE_TITLE = 0;
    public static final int SORT_TYPE_DATE = 1;
    public static final int SORT_TYPE_SIZE = 2;
    public static final int SORT_TYPE_DURATION = 3;

    public static final int ORDER_TYPE_ASC = 0;
    public static final int ORDER_TYPE_DES = 1;

    public static void setSortingOrder(int order, int sort) {
        ProcessApp.getPref().edit()
                .putInt(SONG_SORT, sort)
                .putInt(SONG_ORDER, order)
                .apply();
    }

    public static int getSort() { return ProcessApp.getPref().getInt(SONG_SORT, SORT_TYPE_TITLE); }
    public static int getOrder() { return ProcessApp.getPref().getInt(SONG_ORDER, ORDER_TYPE_ASC); }

    private final static Comparator<Song> titleComAcs = (s1, s2) -> s1.getTitle().toUpperCase().compareTo(s2.getTitle().toUpperCase());
    private final static Comparator<Song> dateComAcs = (s1, s2) -> {
        int a= Long.compare(s1.getDateModified(), s2.getDateModified());
        if (a != 0) return a;
        return s1.getTitle().toUpperCase().compareTo(s2.getTitle().toUpperCase());
    };
    private final static Comparator<Song> sizeComAcs = (s1, s2) -> {
        int a = Long.compare(s1.getSize(), s2.getSize());
        if (a != 0) return a;
        return s1.getTitle().toUpperCase().compareTo(s2.getTitle().toUpperCase());
    };
    private final static Comparator<Song> durationComAcs = (s1, s2) -> {
        int a= Long.compare(s1.getDuration(), s2.getDuration());
        if (a != 0) return a;
        return s1.getTitle().toUpperCase().compareTo(s2.getTitle().toUpperCase());
    };

    private final static Comparator<Song> titleComDecs = (s1, s2) -> s2.getTitle().toUpperCase().compareTo(s1.getTitle().toUpperCase());
    private final static Comparator<Song> dateComDes = (s1, s2) -> {
        int a= Long.compare(s2.getDateModified(), s1.getDateModified());
        if (a != 0) return a;
        return s2.getTitle().toUpperCase().compareTo(s1.getTitle().toUpperCase());
    };
    private final static Comparator<Song> sizeComDes = (s1, s2) -> {
        int a= Long.compare(s2.getSize(), s1.getSize());
        if (a != 0) return a;
        return s2.getTitle().toUpperCase().compareTo(s1.getTitle().toUpperCase());
    };
    private final static Comparator<Song> durationComDes = (s1, s2) -> {
        int a= Long.compare(s2.getDuration(), s1.getDuration());
        if (a != 0) return a;
        return s2.getTitle().toUpperCase().compareTo(s1.getTitle().toUpperCase());
    };

    public static Comparator<Song> Compare() {
        switch (getSort()) {
            default:
            case SORT_TYPE_TITLE: {
                return getOrder() == ORDER_TYPE_ASC ? titleComAcs : titleComDecs;
            }
            case SORT_TYPE_SIZE: {
                return getOrder() == ORDER_TYPE_ASC ? sizeComAcs : sizeComDes;
            }
            case SORT_TYPE_DATE: {
                return getOrder() == ORDER_TYPE_ASC ? dateComAcs : dateComDes;
            }
            case SORT_TYPE_DURATION: {
                return getOrder() == ORDER_TYPE_ASC ? durationComAcs : durationComDes;
            }
        }
    }

}
