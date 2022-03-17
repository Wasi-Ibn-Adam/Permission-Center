package com.wasitech.permissioncenter.java.com.wasitech.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wasitech.ting.Chat.Module.Ting;
import com.wasitech.ting.Noti.UserWithTingNo;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.classes.Alarm;
import com.wasitech.assist.classes.App;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.contact.classes.Contact;
import com.wasitech.contact.classes.ContactSms;
import com.wasitech.assist.classes.Sms;
import com.wasitech.basics.Storage;
import com.wasitech.assist.classes.Tinggo;
import com.wasitech.assist.classes.Tingno;
import com.wasitech.assist.classes.UserLastTing;
import com.wasitech.assist.classes.Weather;
import com.wasitech.basics.classes.Issue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class LocalDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "assist_database";
    private static final int DB_VERSION = 1;

    private final DBNotification dbNoti;
    private final DBTingUser dbUser;
    private final DBTingNo dbTingno;
    private final DBTingGo dbTinggo;
    private final DBTingCount dbTingCount;
    private final DBPic dbPic;
    private final DBAlarm dbAlarm;
    private final DBSms dbSms;
    private final DBContact dbContact;

    public LocalDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        dbNoti = new DBNotification();
        dbUser = new DBTingUser();
        dbTingno = new DBTingNo();
        dbTinggo = new DBTingGo();
        dbTingCount = new DBTingCount();
        dbPic = new DBPic();
        dbAlarm = new DBAlarm();
        dbSms= new DBSms();
        dbContact=new DBContact();
    }

    protected static final String KEY_ID = "id";
    protected static final String PACKAGE = "package";
    protected static final String NAME = "name";
    protected static final String UID = "uid";
    protected static final String PIC = "pic";
    protected static final String MSG_ID = "msg_id";
    protected static final String REACT = "reaction";
    protected static final String NUMBER = "number";
    protected static final String EMAIL = "email";
    protected static final String IMAGE_PATH = "img_path";
    protected static final String EXTRA = "extra";
    protected static final String COUNT = "count";
    protected static final String TITLE = "title";
    protected static final String TEXT = "text";
    protected static final String FROM = "sender";
    protected static final String TIME = "time";
    protected static final String HOUR = "hour";
    protected static final String MINUTE = "minute";
    protected static final String ACTIVE = "active";
    protected static final String SIM = "sim";

    private void deleteTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null, null);
        db.close();
    }

    public void safeDelete() {
        deleteTable(DBApp.TABLE);
        deleteTable(DBContact.TABLE);
        deleteTable(DBNotification.TABLE);
        deleteTable(DBTingUser.TABLE);
        deleteTable(DBTingNo.TABLE);
        deleteTable(DBTingGo.TABLE);
        deleteTable(DBTingCount.TABLE);
        deleteTable(DBPic.TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBApp.QUERY);
        sqLiteDatabase.execSQL(DBContact.QUERY);
        sqLiteDatabase.execSQL(DBNotification.QUERY);
        sqLiteDatabase.execSQL(DBPic.QUERY);
        sqLiteDatabase.execSQL(DBTingUser.QUERY);
        sqLiteDatabase.execSQL(DBTingCount.QUERY);
        sqLiteDatabase.execSQL(DBTingNo.QUERY);
        sqLiteDatabase.execSQL(DBTingGo.QUERY);
        sqLiteDatabase.execSQL(DBAlarm.QUERY);
        sqLiteDatabase.execSQL(DBSms.QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DBApp.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBContact.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBNotification.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBPic.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBTingUser.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBTingCount.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBTingNo.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBTingGo.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBAlarm.UPDATE_QUERY);
        sqLiteDatabase.execSQL(DBSms.UPDATE_QUERY);
    }


    private class DBApp {
        protected static final String TABLE = "table_app";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT,"
                + PACKAGE + " TEXT)";

        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;


        protected void addApp(App app) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME, app.getName());
            values.put(PACKAGE, app.getPackageName());
            db.insert(TABLE, null, values);
            db.close();
        }

        protected ArrayList<App> getAppList() {
            ArrayList<App> list = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    App app = new App(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(PACKAGE)));
                    list.add(app);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }

        protected void updateApp(App app) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME, app.getName());
            values.put(PACKAGE, app.getPackageName());
            int num = db.update(TABLE, values, PACKAGE + "=?", new String[]{app.getPackageName()});
            if (num == 0) {
                addApp(app);
            }
            db.close();
        }
    }
    private class DBSms {
        protected static final String TABLE = "table_sms";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + NUMBER + " TEXT,"
                + SIM + " TEXT,"
                + FROM + " INTEGER,"
                + TIME + " TEXT,"
                + TEXT + " TEXT)";

        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;

        protected void addSms(Sms sms) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NUMBER, sms.getSender());
            values.put(SIM, sms.getReceiver());
            values.put(TIME, sms.getTime());
            values.put(TEXT, sms.getMsg());
            db.insert(TABLE, null, values);
            db.close();
        }

        protected Sms getLastSms(String num) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE +" WHERE "+NUMBER+" =?";
            Cursor cursor = db.rawQuery(query, new String[]{num});
            Sms sms=null;
            if (cursor.moveToFirst()) {
                sms = new Sms(cursor.getString(cursor.getColumnIndex(NUMBER)),
                        cursor.getString(cursor.getColumnIndex(SIM)),
                        cursor.getString(cursor.getColumnIndex(TEXT)),
                        cursor.getString(cursor.getColumnIndex(TIME)),
                        cursor.getInt(cursor.getColumnIndex(FROM)));
            }
            cursor.close();
            db.close();
            return sms;
        }
        protected ArrayList<Sms> getSmsList(String num) {
            ArrayList<Sms> list = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE +" WHERE "+NUMBER+" =?";
            Cursor cursor = db.rawQuery(query, new String[]{num});
            if (cursor.moveToFirst()) {
                do {
                    Sms sms = new Sms(cursor.getString(cursor.getColumnIndex(NUMBER)),
                            cursor.getString(cursor.getColumnIndex(SIM)),
                            cursor.getString(cursor.getColumnIndex(TEXT)),
                            cursor.getString(cursor.getColumnIndex(TIME)),
                            cursor.getInt(cursor.getColumnIndex(FROM)));                    list.add(sms);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }
    }
    public Sms getLastSms(String id){
        return dbSms.getLastSms(id);
    }
    public void addSms(Sms sms){
         dbSms.addSms(sms);
    }
    public ArrayList<Sms> getSmsList(String id){
         return dbSms.getSmsList(id);
    }


    private class DBContact {
        protected static final String TABLE = "table_contact";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT,"
                + NUMBER + " TEXT,"
                + EXTRA + " TEXT)";
        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;

        protected void addContact(Contact contact) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME, contact.getName());
            if (contact.size() >= 1)
                values.put(NUMBER, contact.getNumbers().get(0).toString());
            if (contact.getNumbers().size() >= 2)
                values.put(EXTRA, contact.getNumbers().get(1).toString());
            db.insert(TABLE, null, values);
            db.close();
        }

        protected ArrayList<Contact> getContactList() {
            ArrayList<Contact> list = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(KEY_ID))));
                    contact.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                    ArrayList<String> numbers = new ArrayList<>();
                    numbers.add(cursor.getString(cursor.getColumnIndex(NUMBER)));
                    numbers.add(cursor.getString(cursor.getColumnIndex(EXTRA)));
                    contact.setNumbers(numbers);
                    list.add(contact);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }
        protected ArrayList<ContactSms> getContactSmsList() {
            ArrayList<ContactSms> list = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ContactSms contact = new ContactSms();
                    contact.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                    contact.setSender(cursor.getString(cursor.getColumnIndex(NUMBER)));
                    contact.setSms(dbSms.getLastSms(contact.getSender()));
                    list.add(contact);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }

        protected void setContactList(ArrayList<Contact> list) {
            deleteTable(TABLE);
            for (int i = 0; i < list.size(); i++) {
                addContact(list.get(i));
            }
        }

        protected int updateContact(Contact contact) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME, contact.getName());
            if (contact.getNumbers().size() >= 1)
                values.put(NUMBER, contact.getNumbers().get(0).toString());
            if (contact.getNumbers().size() >= 2)
                values.put(EXTRA, contact.getNumbers().get(1).toString());
            int num = db.update(TABLE, values, NAME + "=?", new String[]{contact.getName()});
            db.close();
            return num;
        }
    }
    public void addContact(Contact contact) {
        dbContact.addContact(contact);
    }

    public ArrayList<ContactSms> getContactSmsList(){
        return dbContact.getContactSmsList();
    }

    private class DBNotification {
        protected static final String TABLE = "table_notification";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + PACKAGE + " TEXT,"
                + TITLE + " TEXT,"
                + TIME + " TEXT,"
                + TEXT + " TEXT)";
        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;


        protected void addNoti(String pkg, String title, String text, long time) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PACKAGE, pkg);
            values.put(TITLE, title);
            values.put(TEXT, text);
            values.put(TIME, Long.toString(time));
            db.insert(TABLE, null, values);
            db.close();
        }

        protected void deleteNoti(String pkg) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE, PACKAGE + " =?", new String[]{pkg});
            db.close();
        }

        protected void deleteNoti(String pkg, String title, String text) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE, PACKAGE + " =? and " + TITLE + " =? and " + TEXT + " =?", new String[]{pkg, title, text});
            db.close();
        }

        protected boolean notificationExist(String pkg, String title, String text) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + PACKAGE + " =? and " + TITLE + "=? and " + TEXT + "=?";
            Cursor cursor = db.rawQuery(query, new String[]{pkg, title, text});
            boolean returning = (cursor.getCount() > 0);
            cursor.close();
            db.close();
            return returning;
        }

        protected long getNotiTime(String pkg, String title, String text) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + PACKAGE + " =? and " + TITLE + " =? and " + TEXT + " =?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{pkg, title, text});
            String time = "0";
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToLast())
                    time = cursor.getString(cursor.getColumnIndex(TIME));
                cursor.close();
            }
            db.close();
            return Long.parseLong(time);
        }

        protected void deleteAll() {
            deleteTable(TABLE);
        }

        protected void deleteByText(String text) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE, TEXT + " =?", new String[]{text});
            db.close();
        }
    }

    public void addNotification(String pkg, String title, String text, long time) {
        try {
            dbNoti.addNoti(pkg, title, text, time);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
    }

    public void deleteNotification(String pkg) {
        try {
            dbNoti.deleteNoti(pkg);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
    }

    public void deleteNotification(String pkg, String title, String text) {
        try {
            dbNoti.deleteNoti(pkg, title, text);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
    }

    public long getNotiTime(String pkg, String title, String text) {
        try {
            return dbNoti.getNotiTime(pkg, title, text);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
        return 0;
    }

    public void deleteNotification() {
        dbNoti.deleteAll();
    }

    public void deleteNotificationByText(String text) {
        dbNoti.deleteByText(text);
    }


    private class DBTingUser {
        protected static final String TABLE = "table_ting_user";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + UID + " TEXT,"
                + NAME + " TEXT,"
                + EMAIL + " TEXT,"
                + NUMBER + " TEXT,"
                + IMAGE_PATH + " TEXT)";

        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;

        protected boolean addUser(TingUser user) {
            if (userNotExist(user.getUid())) {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UID, user.getUid());
                values.put(NAME, user.getName());
                values.put(NUMBER, user.getNumber());
                values.put(EMAIL, user.getEmail());
                values.put(IMAGE_PATH, user.getImagePath());
                db.insert(TABLE, null, values);
                db.close();
                return true;
            }
            return false;
        }

        protected TingUser getUser(String id) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{id});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                TingUser user = new TingUser(
                        cursor.getString(cursor.getColumnIndex(UID)),
                        cursor.getString(cursor.getColumnIndex(NAME)),
                        cursor.getString(cursor.getColumnIndex(NUMBER)),
                        cursor.getString(cursor.getColumnIndex(IMAGE_PATH)),
                        cursor.getString(cursor.getColumnIndex(EMAIL)));
                cursor.close();
                db.close();
                return user;
            }
            return null;
        }

        protected boolean userNotExist(String id) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{id});
            boolean temp = (cursor != null && cursor.getCount() > 0);
            db.close();
            return !temp;
        }

        protected ArrayList<TingUser> getUsersList() {
            ArrayList<TingUser> list = new ArrayList<>();
            try {
                SQLiteDatabase db = getReadableDatabase();
                String query = "SELECT * FROM " + TABLE;
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        TingUser user = new TingUser(
                                cursor.getString(cursor.getColumnIndex(UID)),
                                cursor.getString(cursor.getColumnIndex(NAME)),
                                cursor.getString(cursor.getColumnIndex(NUMBER)),
                                cursor.getString(cursor.getColumnIndex(IMAGE_PATH)),
                                cursor.getString(cursor.getColumnIndex(EMAIL)));
                        list.add(user);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());
            }
            return list;
        }

        protected void deleteUser(String id) {
            try {
                SQLiteDatabase db = getWritableDatabase();
                db.delete(TABLE, UID + " =?", new String[]{id});
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());
            }
        }

        protected ArrayList<UserLastTing> getUserListWithChat() {
            ArrayList<UserLastTing> list = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(UID));
                    if (dbTinggo.tingsExist(id)) {
                        TingUser user = new TingUser(
                                cursor.getString(cursor.getColumnIndex(UID)),
                                cursor.getString(cursor.getColumnIndex(NAME)),
                                cursor.getString(cursor.getColumnIndex(NUMBER)),
                                cursor.getString(cursor.getColumnIndex(IMAGE_PATH)),
                                cursor.getString(cursor.getColumnIndex(EMAIL)));
                        UserLastTing lastTing = new UserLastTing(user, dbTinggo.lastTing(id));
                        list.add(lastTing);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }

        protected String getImageUrl(String uid) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{uid});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String path = cursor.getString(cursor.getColumnIndex(IMAGE_PATH));
                cursor.close();
                db.close();
                return path;
            }
            return null;
        }

        public ArrayList<UserWithTingNo> getTingNoUserList() {
            ArrayList<UserWithTingNo> list = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(UID));
                    Tingno last=dbTingno.getLastTingNo(id);
                    if (last!=null) {
                        TingUser user = new TingUser(
                                cursor.getString(cursor.getColumnIndex(UID)),
                                cursor.getString(cursor.getColumnIndex(NAME)),
                                cursor.getString(cursor.getColumnIndex(NUMBER)),
                                cursor.getString(cursor.getColumnIndex(IMAGE_PATH)),
                                cursor.getString(cursor.getColumnIndex(EMAIL)));
                        UserWithTingNo lastTing = new UserWithTingNo(user, last);
                        list.add(lastTing);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }
    }

    public boolean userNotExist(String id) {
        try {
            return dbUser.userNotExist(id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
        return true;
    }

    public boolean addTingUser(TingUser user) {
        try {
            return dbUser.addUser(user);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
        return false;
    }

    public TingUser getAssistUser(String id) {
        return dbUser.getUser(id);
    }

    public ArrayList<TingUser> getTingUserList() {
        try {
            return dbUser.getUsersList();
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
        return new ArrayList<>();
    }
    public ArrayList<UserWithTingNo> getTingNoUserList() {
        try {
            return dbUser.getTingNoUserList();
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
        return new ArrayList<>();
    }

    public ArrayList<UserLastTing> getUserWithChatList() {
        try {
            return dbUser.getUserListWithChat();
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
        return new ArrayList<>();
    }

    public void deleteUser(String id) {
        dbUser.deleteUser(id);
    }

    public String getUserImagePath(String uid) {
        return dbUser.getImageUrl(uid);
    }


    private class DBTingNo {
        protected static final String TABLE = "table_ting_no";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + UID + " TEXT,"
                + TEXT + " TEXT,"
                + FROM + " TEXT,"
                + TIME + " TEXT)";

        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;

        protected void addTingNo(Tingno t) {
            if (tingNotExist(t)) {
                try {
                    SQLiteDatabase db = getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(TEXT, t.getText());
                    values.put(UID, t.getUid());
                    values.put(TIME, t.getTime());
                    values.put(FROM, t.getSender());
                    db.insert(TABLE, null, values);
                    db.close();
                } catch (Exception e) {
                    Issue.print(e, LocalDB.class.getName());

                }
            }
        }

        private boolean tingNotExist(Tingno t) {
            try {
                SQLiteDatabase db = getReadableDatabase();
                String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =? and " + TEXT + "=? and " + TIME + "=?";
                @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{t.getUid(), t.getText(), t.getTime()});
                boolean temp = (cursor != null && cursor.getCount() > 0);
                db.close();
                return !temp;
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
            return false;
        }

        protected ArrayList<Tingno> getTingNoList() {
            ArrayList<Tingno> list = new ArrayList<>();
            try {
                SQLiteDatabase db = getReadableDatabase();
                String query = "SELECT * FROM " + TABLE;
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        Tingno tingno = new Tingno(
                                cursor.getString(cursor.getColumnIndex(UID)),
                                cursor.getString(cursor.getColumnIndex(TEXT)),
                                cursor.getString(cursor.getColumnIndex(FROM)),
                                cursor.getString(cursor.getColumnIndex(TIME)));
                        list.add(tingno);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
            return list;
        }

        protected ArrayList<Tingno> getUsersTingNoList() {
            ArrayList<Tingno> list = new ArrayList<>();
            try {
                SQLiteDatabase db = getReadableDatabase();
                String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
                Cursor cursor = db.rawQuery(query, new String[]{Tingno.DEVELOPER});
                if (cursor.moveToFirst()) {
                    do {
                        Tingno tingno = new Tingno(
                                cursor.getString(cursor.getColumnIndex(UID)),
                                cursor.getString(cursor.getColumnIndex(TEXT)),
                                cursor.getString(cursor.getColumnIndex(FROM)),
                                cursor.getString(cursor.getColumnIndex(TIME)));
                        list.add(tingno);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
            return list;
        }

        protected Tingno getLastTingNo(String id) {
            Tingno ting=null;
            try {
                SQLiteDatabase db = getReadableDatabase();
                String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
                Cursor cursor = db.rawQuery(query, new String[]{id});
                if (cursor.moveToFirst()) {
                    ting = new Tingno(
                            cursor.getString(cursor.getColumnIndex(UID)),
                            cursor.getString(cursor.getColumnIndex(TEXT)),
                            cursor.getString(cursor.getColumnIndex(FROM)),
                            cursor.getString(cursor.getColumnIndex(TIME)));
                }
                cursor.close();
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
            return ting;
        }

        protected ArrayList<Tingno> getTingNoList(String id) {
            ArrayList<Tingno> list = new ArrayList<>();
            try {
                SQLiteDatabase db = getReadableDatabase();
                String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
                Cursor cursor = db.rawQuery(query, new String[]{id});
                if (cursor.moveToFirst()) {
                    do {
                        Tingno tingno = new Tingno(
                                cursor.getString(cursor.getColumnIndex(UID)),
                                cursor.getString(cursor.getColumnIndex(TEXT)),
                                cursor.getString(cursor.getColumnIndex(FROM)),
                                cursor.getString(cursor.getColumnIndex(TIME)));
                        list.add(tingno);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
            return list;
        }

        protected void deleteTingNo(Tingno noti) {
            try {
                SQLiteDatabase db = getWritableDatabase();
                String query = UID + " =? and " + TEXT + " =? and " + TIME + " =?";
                db.delete(TABLE, query, new String[]{noti.getUid(), noti.getText(), noti.getTime()});
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
        }

        protected void deleteTingNo(String id) {
            try {
                SQLiteDatabase db = getWritableDatabase();
                db.delete(TABLE, UID + " =?", new String[]{id});
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
        }

    }

    public void addTingNoti(Tingno noti) {
        try {
            dbTingno.addTingNo(noti);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
    }

    public ArrayList<Tingno> getUsersTingNotiList() {
        try {
            return dbTingno.getUsersTingNoList();
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
        return new ArrayList<>();
    }

    public Tingno getLastTingNo(String id) {
        try {
            return dbTingno.getLastTingNo(id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
        return null;
    }

    public ArrayList<Tingno> getTingNotiList(String id) {
        try {
            return dbTingno.getTingNoList(id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
        return new ArrayList<>();
    }

    public boolean tingNotExist(Tingno noti) {
        try {
            return dbTingno.tingNotExist(noti);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
        return false;
    }

    public void deleteTingNo(Tingno noti) {
        dbTingno.deleteTingNo(noti);
    }

    public void deleteTingNo(String id) {
        dbTingno.deleteTingNo(id);
    }


    private class DBTingGo {
        protected static final String TABLE = "table_tinggo";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + UID + " TEXT,"
                + TEXT + " TEXT,"
                + FROM + " TEXT,"
                + TIME + " TEXT,"
                + MSG_ID + " TEXT,"
                + REACT + " INTEGER)";

        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;

        protected void addTingGo(Ting ting) {
            if (tingNotExist(ting)) {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UID, ting.getTinggo().getUid());
                values.put(TEXT, ting.getTinggo().getText());
                values.put(FROM, ting.getTinggo().getWho());
                values.put(TIME, ting.getTinggo().getTime());
                values.put(REACT, ting.getReaction());
                values.put(MSG_ID, ting.getMsgId());
                db.insert(TABLE, null, values);
                db.close();
            }
        }

        private boolean tingsExist(String id) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{id});
            boolean temp = (cursor != null && cursor.getCount() > 0);
            db.close();
            return temp;
        }

        protected boolean tingNotExist(Ting go) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =? and " + MSG_ID + " =?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{go.getTinggo().getUid(), go.getMsgId()});
            boolean temp = (cursor != null && cursor.getCount() > 0);
            db.close();
            return !temp;
        }

        protected Ting getTing(String uid, String msgId) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =? and " + MSG_ID + " =?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{uid, msgId});
            Ting last = null;
            if (cursor.moveToLast()) {
                last = new Ting(new Tinggo(
                        cursor.getInt(cursor.getColumnIndex(FROM)),
                        cursor.getString(cursor.getColumnIndex(UID)),
                        cursor.getString(cursor.getColumnIndex(TEXT)),
                        cursor.getString(cursor.getColumnIndex(TIME))
                ));
                last.setMsgId(cursor.getString(cursor.getColumnIndex(MSG_ID)));
                last.setReaction(cursor.getInt(cursor.getColumnIndex(REACT)));
            }
            cursor.close();
            return last;
        }

        protected boolean tingUpdate(String id, String msgId, int react) {
            if (react != getReaction(id, msgId)) {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(REACT, react);
                String query = UID + " =? and " + MSG_ID + " =?";
                db.update(TABLE, values, query, new String[]{id, msgId});
                db.close();
                return true;
            }
            return false;
        }

        protected Ting lastTing(String id) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
            Cursor cursor = db.rawQuery(query, new String[]{id});
            Ting last = null;
            if (cursor.moveToLast()) {
                last = new Ting(new Tinggo(
                        cursor.getInt(cursor.getColumnIndex(FROM)),
                        cursor.getString(cursor.getColumnIndex(UID)),
                        cursor.getString(cursor.getColumnIndex(TEXT)),
                        cursor.getString(cursor.getColumnIndex(TIME))
                ));
                last.setMsgId(cursor.getString(cursor.getColumnIndex(MSG_ID)));
                last.setReaction(cursor.getInt(cursor.getColumnIndex(REACT)));
            }
            cursor.close();
            db.close();
            return last;
        }

        protected int getReaction(String id, String msgId) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =? and " + MSG_ID + " =?";
            Cursor cursor = db.rawQuery(query, new String[]{id, msgId});
            int react = 0;
            if (cursor.moveToLast()) react = cursor.getInt(cursor.getColumnIndex(REACT));
            cursor.close();
            db.close();
            return react;
        }

        protected ArrayList<Ting> getTingGoList(String id) {
            ArrayList<Ting> list = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + "=?";
            Cursor cursor = db.rawQuery(query, new String[]{id});
            if (cursor.moveToFirst()) {
                do {
                    Ting ting = new Ting(new Tinggo(
                            cursor.getInt(cursor.getColumnIndex(FROM)),
                            cursor.getString(cursor.getColumnIndex(UID)),
                            cursor.getString(cursor.getColumnIndex(TEXT)),
                            cursor.getString(cursor.getColumnIndex(TIME))
                    ));
                    ting.setReaction(cursor.getInt(cursor.getColumnIndex(REACT)));
                    ting.setMsgId(cursor.getString(cursor.getColumnIndex(MSG_ID)));
                    list.add(ting);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }

        protected ArrayList<Ting> getAllTings() {
            ArrayList<Ting> list = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Ting ting = new Ting(new Tinggo(
                            cursor.getInt(cursor.getColumnIndex(FROM)),
                            cursor.getString(cursor.getColumnIndex(UID)),
                            cursor.getString(cursor.getColumnIndex(TEXT)),
                            cursor.getString(cursor.getColumnIndex(TIME))
                    ));
                    ting.setReaction(cursor.getInt(cursor.getColumnIndex(REACT)));
                    ting.setMsgId(cursor.getString(cursor.getColumnIndex(MSG_ID)));
                    list.add(ting);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return list;
        }

    }

    public Ting getTing(String uid, String id) {
        return dbTinggo.getTing(uid, id);
    }

    public void addTingGo(Ting ting) {
        try {
            dbTinggo.addTingGo(ting);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
    }

    public boolean tingUpdate(String id, String msgId, int react) {
        try {
            return dbTinggo.tingUpdate(id, msgId, react);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
        return false;
    }

    public boolean tinggoNotExist(Ting ting) {
        return dbTinggo.tingNotExist(ting);
    }

    public Tinggo lastTing(String id) {
        try {
            return dbTinggo.lastTing(id).getTinggo();
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
        return null;
    }

    public ArrayList<Ting> getTingGoList(String id) {
        try {
            return dbTinggo.getTingGoList(id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
        return new ArrayList<>();
    }


    private class DBTingCount {
        protected static final String TABLE = "table_ting_count";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + UID + " TEXT,"
                + COUNT + " INTEGER)";

        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;

        protected void addInTingCount(String id, int count) {
            int num = getTingCount(id);
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COUNT, count + num);
            if (db.update(TABLE, values, UID + " =?", new String[]{id}) < 1) {
                values.put(UID, id);
                db.insert(TABLE, null, values);
            }
            db.close();
        }

        protected void deleteTingCount(String id) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE, UID + " =?", new String[]{id});
            db.close();
        }

        protected int getTingCount(String id) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + " =?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{id});
            int count = 0;
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(cursor.getColumnIndex(COUNT));
            }
            db.close();
            return count;
        }
    }

    public void addTingCount(String id, int count) {
        try {
            dbTingCount.addInTingCount(id, count);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
    }
    public void addDeveloperTingCount(String id, int count) {
        try {
            dbTingCount.addInTingCount(Tingno.DEVELOPER+id, count);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
    }

    public void deleteTingCount(String id) {
        try {
            dbTingCount.deleteTingCount(id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
    }
    public void deleteDeveloperTingCount(String id) {
        try {
            dbTingCount.deleteTingCount(Tingno.DEVELOPER+id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
    }

    public int getTingCount(String id) {
        return dbTingCount.getTingCount(id);
    }
    public int getDeveloperTingCount(String id) {
        return dbTingCount.getTingCount(Tingno.DEVELOPER+id);
    }


    private class DBPic {
        protected static final String TABLE = "table_profile_pic";
        private static final String subQuery = TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + UID + " TEXT,"
                + PIC + " BLOB)";

        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;

        protected void savePic(String id, byte[] img) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UID, id);
            values.put(PIC, img);
            db.insert(TABLE, null, values);
            db.close();
        }

        protected void deletePic(String id) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE, UID + "=?", new String[]{id});
            db.close();
        }

        protected boolean picExist(String id) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + "=?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{id});
            boolean temp = cursor != null && cursor.getCount() > 0;
            db.close();
            return temp;
        }

        protected byte[] getPic(String id) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE " + UID + "=?";
            byte[] pic = null;
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{id});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                pic = cursor.getBlob(2);
                cursor.close();
            }
            db.close();
            return pic;
        }

        protected void updatePic(String id, byte[] img) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PIC, img);
            if (db.update(TABLE, values, UID + "=?", new String[]{id}) != 1) {
                values.put(UID, id);
                db.insert(TABLE, null, values);
            }
            db.close();
        }
    }

    public void savePic(String id, byte[] img) {
        try {
            dbPic.savePic(id, img);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
    }

    public void deletePic(String id) {
        try {
            dbPic.deletePic(id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
    }

    public boolean picExist(String id) {
        try {
            return dbPic.picExist(id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
        return false;
    }

    public byte[] getPic(String id) {
        try {
            return dbPic.getPic(id);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
        return null;
    }

    public void updatePic(String id, byte[] img) {
        try {
            dbPic.updatePic(id, img);
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());

        }
    }


    private class DBAlarm {
        private static final String TABLE_ALARM = "table_alarm";
        private static final String subQuery = TABLE_ALARM + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + HOUR + " INTEGER,"
                + MINUTE + " INTEGER,"
                + TEXT + " TEXT,"
                + ACTIVE + " INTEGER)";

        public static final String QUERY = "CREATE TABLE " + subQuery;
        public static final String UPDATE_QUERY = "CREATE TABLE IF NOT EXISTS " + subQuery;


        protected boolean isAlarm(Alarm alarm) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_ALARM + " WHERE " + HOUR + "=" + alarm.getH() + " and " + MINUTE + "=" + alarm.getM() + " and " + TEXT + "=?";
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{alarm.getTalk()});
            boolean temp = (cursor != null && cursor.getCount() > 0);
            db.close();
            return temp;
        }

        protected boolean isAlarmActive(int h, int m, String t) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_ALARM + " WHERE " + HOUR + "=" + h + " and " + MINUTE + "=" + m + " and " + TEXT + "=?";
            boolean flag = false;
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{t});
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    flag = cursor.getInt(cursor.getColumnIndex(ACTIVE)) == Alarm.ACTIVE;
                }
                cursor.close();
            }
            db.close();
            return flag;
        }

        protected int getAlarmId(Alarm alarm) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_ALARM + " WHERE " + HOUR + "=" + alarm.getH() + " and " + MINUTE + "=" + alarm.getM() + " and " + TEXT + "=?";
            int id = 0;
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{alarm.getTalk()});
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                }
                cursor.close();
            }
            db.close();
            return id;
        }

        protected Alarm getAlarm(int h, int m, String t) {
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_ALARM + " WHERE " + HOUR + "=" + h + " and " + MINUTE + "=" + m + " and " + TEXT + "=?";
            Alarm alarm = null;
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{t});
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    alarm = new Alarm(
                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            cursor.getInt(cursor.getColumnIndex(HOUR)),
                            cursor.getInt(cursor.getColumnIndex(MINUTE)),
                            cursor.getString(cursor.getColumnIndex(TEXT)),
                            cursor.getInt(cursor.getColumnIndex(ACTIVE)) == Alarm.ACTIVE);
                }
                cursor.close();
            }
            db.close();
            return alarm;
        }

        protected boolean addAlarm(Alarm alarm) {
            if (isAlarm(alarm))
                return false;
            try {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(HOUR, alarm.getH());
                values.put(MINUTE, alarm.getM());
                values.put(ACTIVE, 1);
                values.put(TEXT, alarm.getTalk());
                db.insert(TABLE_ALARM, null, values);
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

                return false;
            }
            return true;
        }

        protected void setAlarmState(int id, boolean s) {
            int num = 0;
            if (s)
                num = 1;
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ACTIVE, num);
            db.update(TABLE_ALARM, values, KEY_ID + "=" + id, new String[]{});
            db.close();

        }

        protected void setAlarmsState(boolean s) {
            int num = 0;
            if (s)
                num = 1;
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ACTIVE, num);
            db.update(TABLE_ALARM, values, null, null);
            db.close();
        }

        protected void updateAlarm(Alarm old, Alarm latest) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ACTIVE, 1);
            values.put(HOUR, latest.getH());
            values.put(MINUTE, latest.getM());
            values.put(TEXT, latest.getTalk());
            db.update(TABLE_ALARM, values,
                    HOUR + "=" + old.getH() + " and " + MINUTE + "=" + old.getM() + " and " + TEXT + "=?",
                    new String[]{old.getTalk()});
            db.close();
        }

        protected void removeAlarm(Alarm alarm) {
            try {
                SQLiteDatabase db = getWritableDatabase();
                String query = HOUR + " =" + alarm.getH() + " AND " + MINUTE + " =" + alarm.getM() + " AND " + TEXT + " =?";
                db.delete(TABLE_ALARM, query, new String[]{alarm.getTalk()});
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
        }

        protected ArrayList<Alarm> getAlarmList() {
            ArrayList<Alarm> list = new ArrayList<>();
            try {
                SQLiteDatabase db = getReadableDatabase();
                String query = "SELECT * FROM " + TABLE_ALARM;
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        Alarm alarm = new Alarm(
                                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                                cursor.getInt(cursor.getColumnIndex(HOUR)),
                                cursor.getInt(cursor.getColumnIndex(MINUTE)),
                                cursor.getString(cursor.getColumnIndex(TEXT)),
                                cursor.getInt(cursor.getColumnIndex(ACTIVE)) == Alarm.ACTIVE);
                        list.add(alarm);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            } catch (Exception e) {
                Issue.print(e, LocalDB.class.getName());

            }
            return list;
        }
    }

    public Alarm getAlarm(int h, int m, String t) {
        return dbAlarm.getAlarm(h, m, t);
    }

    public int getAlarmId(Alarm alarm) {
        return dbAlarm.getAlarmId(alarm);
    }

    public boolean addAlarm(Alarm alarm) {
        return dbAlarm.addAlarm(alarm);
    }

    public void updateAlarm(Alarm old, Alarm latest) {
        dbAlarm.updateAlarm(old, latest);
    }

    public void removeAlarm(Alarm alarm) {
        dbAlarm.removeAlarm(alarm);
    }

    public void setAlarmsState(boolean s) {
        dbAlarm.setAlarmsState(s);
    }

    public ArrayList<Alarm> getAlarmList() {
        return dbAlarm.getAlarmList();
    }

    public void setAlarmState(int id, boolean s) {
        dbAlarm.setAlarmState(id, s);
    }


    public void normalize() {
        try {
            getWritableDatabase().close();
            getReadableDatabase().close();
        } catch (Exception e) {
            Issue.print(e, LocalDB.class.getName());
        }
    }

    public void addWeather(Weather weather) {
    }

    public File getTingGoBackUp(Context c) throws Exception {
        File db = Storage.CreateBaseFile(c, Storage.TINGGO);
        FileOutputStream stream = new FileOutputStream(db);
        ArrayList<Ting> list = dbTinggo.getAllTings();
        stream.write("Tinggo\n".getBytes());
        for (Ting t : list) {
            stream.write(t.toString().getBytes());
        }
        stream.close();
        return db;
    }

    public void setTingGoBackUp(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String singleLine;
        while ((singleLine = br.readLine()) != null) {
            if (singleLine.equalsIgnoreCase("Tinggo") || singleLine.contains("Tinggo"))
                continue;
            Ting t = new Ting(singleLine);
            if (t.getMsgId() == null || t.getTinggo().getUid() == null) {
                Basics.Log("empty " + singleLine);
                continue;
            }
            Basics.Log("full " + singleLine);
            dbTinggo.addTingGo(t);
        }
        br.close();
    }

}
