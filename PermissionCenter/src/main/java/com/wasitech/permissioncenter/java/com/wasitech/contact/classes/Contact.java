package com.wasitech.permissioncenter.java.com.wasitech.contact.classes;


import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.classes.MultiFun;

import java.io.InputStream;
import java.util.ArrayList;

public class Contact {
    private String name;
    private ArrayList<Number> numberA;
    private int pic;
    private String id;

    public boolean contains(String str) {
        if (MultiFun.containsIgnoreCase(str, name)) {
            return true;
        } else {
            for (Number s : numberA) {
                if (MultiFun.containsIgnoreCase(str, s.number)) return true;
            }
        }
        return false;
    }

    public boolean same(Contact contact) { return (id.equals(contact.id)); }

    public Contact() { }

    public Contact(String id, String name, int pic, ArrayList<String> numbers) {
        this.name = name;
        setNumbers(numbers);
        this.pic = pic;
        this.id = id;
    }

    public Contact(String id, String name, ArrayList<Number> numbers) {
        this.id = id;
        this.name = name;
        this.numberA = numbers;
    }

    public static Bitmap getPic(Context context, String contactId) {
        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId)));
        if (inputStream != null) {
            return BitmapFactory.decodeStream(inputStream);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Number> getNumbers() {
        return numberA;
    }

    public void setNumbers(ArrayList<String> numbers) {
        for (String s : numbers) {
            numberA.add(new Number(s));
        }
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int size() {
        return numberA.size();
    }

    public static String lookupKey(Context context, String contactId) {
        try {
            @SuppressLint("Recycle") Cursor cur = context.getContentResolver()
                    .query(ContactsContract.Contacts.CONTENT_URI,
                            new String[]{ContactsContract.Contacts.LOOKUP_KEY},
                            ContactsContract.Contacts._ID + " = " + contactId,
                            null, null);
            assert cur != null;
            if (cur.moveToFirst()) {
                return cur.getString(0);
            }
        } catch (Exception e) {
            Issue.print(e, Contact.class.getName());
        }
        return null;
    }

    public static class Number {
        private String number, type;

        public Number(String number, String type) {
            this.number = number;
            this.type = type;
        }

        public Number(String fromString) {
            String[] pcs = fromString.split(":");
            type = pcs[0];
            number = pcs[1];
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type + ":" + number;
        }

        public boolean matches(Number number) {
            return (number.number.equals(getNumber()));
        }
    }
}
