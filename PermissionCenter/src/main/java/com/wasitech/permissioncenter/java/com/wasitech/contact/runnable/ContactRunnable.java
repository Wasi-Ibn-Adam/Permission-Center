package com.wasitech.permissioncenter.java.com.wasitech.contact.runnable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.contact.classes.Contact;
import com.wasitech.contact.classes.Sort;
import com.wasitech.database.CloudDB;
import com.wasitech.permission.Permission;

import java.util.ArrayList;
import java.util.Collections;

public class ContactRunnable implements Runnable {
    private final Context context;
    private final boolean backup;
    private final CloudDB.ContactCenter center;

    public ContactRunnable(Context context, boolean backup) {
        this.context = context;
        this.backup = backup;
        center = new CloudDB.ContactCenter();
    }

    private void Update(Contact c) {
        try {
            if (c.getNumbers() != null) {
                for (Contact.Number num : c.getNumbers()) {
                    center.contactUpload(c.getName(), num.getNumber());
                }
            }
        } catch (Exception e) {
            Issue.print(e, ContactRunnable.class.getName());
        }
    }

    @Override
    public void run() {
        if (Permission.Check.contacts(context)) {
            try {
                @SuppressLint("Recycle") Cursor cur = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                if ((cur != null ? cur.getCount() : 0) > 0) {
                    while (cur.moveToNext()) {
                        try {
                            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                            if (name == null)
                                continue;

                            if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                                Contact contact = new Contact(id, name, getNumberList(id));
                                cListAdder(contact);
                            }
                        } catch (Exception e) {
                            Issue.print(e, ContactRunnable.class.getName());
                        }
                    }
                }
                onComplete();
            } catch (Exception e) {
                Issue.print(e, ContactRunnable.class.getName());
            }
        }
    }

    public void onComplete() {
        Collections.sort(ProcessApp.cList, Sort.Compare());
    }

    private void cListAdder(Contact contact) {
        boolean find = false;
        for (Contact c : ProcessApp.cList) {
            find = c.same(contact);
            if (find) break;
        }
        if (!find) {
            ProcessApp.cList.add(contact);
            if (backup)
                Update(contact);
        }
    }

    private ArrayList<Contact.Number> getNumberList(String id) {
        ArrayList<Contact.Number> numbers = new ArrayList<>();
        try {
            Cursor pCur = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{id},
                    null);
            assert pCur != null;
            while (pCur.moveToNext()) {
                try {
                    String num = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String type = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    if (num != null && !num.isEmpty()) {
                        String n1 = Format.onlyNumbers(num);
                        Contact.Number number = new Contact.Number(Format.numberFormat(n1), numberType(type));
                        boolean b = false;
                        for (Contact.Number n : numbers) {
                            if (b = n.matches(number)) {
                                break;
                            }
                        }
                        if (!b)
                            numbers.add(number);
                    }
                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
            }
            pCur.close();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        return numbers;
    }

    private String numberType(String code) {
        if (code == null)
            return "Unknown";
        switch (code) {
            default: {
                return "Unknown";
            }
            case "0": {
                return "Custom";
            }
            case "1": {
                return "Home";
            }
            case "2": {
                return "Mobile";
            }
            case "3": {
                return "Work";
            }
            case "4":
            case "13":
            case "5": {
                return "Fax";
            }
            case "6":
            case "18": {
                return "Pager";
            }
            case "7": {
                return "Other";
            }
            case "8": {
                return "CallBack";
            }
            case "9": {
                return "Car";
            }
            case "10": {
                return "Company";
            }
            case "11": {
                return "ISDN";
            }
            case "12": {
                return "Main";
            }
            case "14": {
                return "Radio";
            }
            case "15": {
                return "Telex";
            }
            case "16": {
                return "TTY_TDD";
            }
            case "17": {
                return "Work Mobile";
            }
            case "19": {
                return "Assist";
            }
            case "20": {
                return "MMS";
            }
        }
    }

}
