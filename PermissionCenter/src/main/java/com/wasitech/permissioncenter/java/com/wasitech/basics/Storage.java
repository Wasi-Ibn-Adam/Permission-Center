package com.wasitech.permissioncenter.java.com.wasitech.basics;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.command.family.Answers;
import com.wasitech.assist.command.family.QA;
import com.wasitech.basics.classes.Issue;
import com.wasitech.camera.classes.Images;
import com.wasitech.contact.classes.Contact;
import com.wasitech.music.classes.Song;

import java.io.File;
import java.util.ArrayList;

public class Storage {
    public static final String DOWN = Environment.DIRECTORY_DOWNLOADS;
    public static final String REC = Environment.DIRECTORY_MUSIC;
    public static final String IMG = Environment.DIRECTORY_DCIM;
    public static final String VID = Environment.DIRECTORY_MOVIES;
    public static final String APP = Environment.DIRECTORY_DOCUMENTS;
    public static final String DB = Environment.DIRECTORY_DOCUMENTS;
    public static final String APPLICATION = "App";
    public static final String CONTACTS = "Contact";
    public static final String TINGGO = "TingGo";
    private static ArrayList<Images> picList;

    private static ArrayList<Images> getList(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                File[] temp = file.listFiles();
                if (temp != null)
                    picList.addAll(getList(temp));
                continue;
            }
            Images img = new Images(file.getName(), file.getPath(),file.lastModified());
            picList.add(img);
        }
        return picList;
    }

    public static ArrayList<Images> getImgList() {
        picList = new ArrayList<>();
        try {
            File directory = Storage.getDirectory(IMG);
            File[] files = directory.listFiles();
            if (files != null)
                return getList(files);

        } catch (Exception e) {
            Issue.print(e, Storage.class.getName());
        }
        return picList;
    }

    private static ArrayList<Song> recList;

    private static ArrayList<Song> getRec(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                File[] temp = file.listFiles();
                if (temp != null)
                    recList.addAll(getRec(temp));
                continue;
            }
            Song song = new Song(0, file.getName(), file.getPath(), file.getTotalSpace());
            recList.add(song);
        }
        return recList;
    }

    public static ArrayList<Song> getRecList() {
        recList = new ArrayList<>();
        File directory = Storage.getDirectory(REC);
        File[] files = directory.listFiles();
        if (files != null)
            return getRec(files);
        return recList;
    }

    public static String fileName() {
        String name = Answers.ASSISTANT.NAME_ONLY() + " " + QA.date() + "-" + QA.cTime();
        return name.replaceAll(":", "");
    }

    public static Intent vCard(Context context, String id, String name) {
        String lookupKey = Contact.lookupKey(context, id);
        Uri vcardUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ContactsContract.Contacts.CONTENT_VCARD_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, vcardUri);
        intent.putExtra(Intent.EXTRA_SUBJECT, name);
        return intent;
    }

    public static File getDirectory(String root) {
        File dir = Environment.getExternalStoragePublicDirectory(root);
        File file = new File(dir, "Assist");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File CreateBaseFile(Context context, String type) {
        File dir = context.getDir("Database", Context.MODE_PRIVATE);
        File db = new File(dir, type + "_" + fileName() + ".db");
        if (db.exists())
            db.delete();
        return db;
    }

    public static File CreateDataFile(String folder, String postfix) {
        File directory = getDirectory(folder);
        File file = new File(directory, fileName() + postfix);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    public static File CreateDataFile(String folder, String fileName, String postfix) {
        File directory = getDirectory(folder);
        File file = new File(directory, fileName + postfix);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    public static File GetDataFile(String folder, String fileName, String postfix) {
        File directory = getDirectory(folder);
        File file = new File(directory, fileName + postfix);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static File CreateDataEncrypt(String fileName) {
        File directory = getDirectory(DB);
        return new File(directory, fileName + ".assist");
    }

    public static File CreateDataDecrypt(Context context, String name) {
        File dir = context.getDir("Database", Context.MODE_PRIVATE);
        return new File(dir, name + ".txt");
    }

    public static boolean clearCache(Context context) {
        try {
            File dir = context.getApplicationContext().getCacheDir();
            ProcessApp.deleteDir(dir);
        } catch (Exception e) {
            Issue.print(e, Storage.class.getName());
        }
        return false;
    }
}
