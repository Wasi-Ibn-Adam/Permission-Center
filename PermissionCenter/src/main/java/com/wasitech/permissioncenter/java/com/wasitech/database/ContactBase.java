package com.wasitech.permissioncenter.java.com.wasitech.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.command.family.QA;
import com.wasitech.basics.classes.Issue;

import java.io.File;
import java.io.FileInputStream;

public class ContactBase {
    public static final DatabaseReference Random = FirebaseDatabase.getInstance(FirebaseApp.getInstance(BaseMaker.RANDOM)).getReference();
    public static final DatabaseReference AllCountries = FirebaseDatabase.getInstance(FirebaseApp.getInstance(BaseMaker.ALL_COUNTRIES)).getReference();

    public static String countryCode(String poh){
        if(poh==null) return null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(poh ,null);
            if (!phoneUtil.isValidNumber(number)) {
                return Params.UNKNOWN;
            }
            return String.valueOf(number.getCountryCode());
        } catch (Exception e) {
            Issue.print(e,ContactBase.class.getName());
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
        } catch (Exception e) {
            Issue.print(e,ContactBase.class.getName());
            return poh;
        }
    }
    public static String extensionNumber(String poh){
        if(poh==null) return null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(poh ,null);
            if (!phoneUtil.isValidNumber(number)) {
                return poh;
            }
            return number.getExtension();
        } catch (Exception e) {
            Issue.print(e,ContactBase.class.getName());
            return poh;
        }
    }
    public static String coNum(String num){
        if (num == null) return null;
        try {
            String[] nums = num.substring(num.lastIndexOf(":") + 1).replace('-', ' ')
                    .replace('(', ' ').replace(')', ' ')
                    .replace('[', ' ').replace(']', ' ')
                    .replace('{', ' ').replace('}', ' ')
                    .replace('_', ' ').replace('+', ' ')
                    .replace('.', ' ').replace('+', ' ')
                    .split(" ");
            StringBuilder temp = new StringBuilder();
            for (String s : nums) temp.append(s);
            return temp.toString();
        }
        catch (Exception e){
            Issue.print(e,ContactBase.class.getName());
        }
        return null;
    }
    public static String removeZeros(String poh){
        if(poh==null) return null;
        while(poh.startsWith("0")){
            poh=poh.replaceFirst("0","");
        }
        return poh;
    }
    public static void Updater(String num, String name){
        try{
            if(num==null||num.length()<1)return;
            String cc=countryCode(num);
            String cn=nationalNumber(num);

            if(cc.equals(Params.UNKNOWN)){
                String number=num;
                if(!num.startsWith("+")&&num.length()>11)
                    number = "+" + num;
                String cc1=countryCode(number);
                if(!cc1.equals(Params.UNKNOWN)){
                    String cn1=nationalNumber(number);
                    if(cc1.equals("92")){
                        Pakistani(cn1,name,false);
                    }
                    else {
                        AllCountries.child(cc1).child(cn1).child(Params.NAME).setValue(name);
                    }
                }
                else{
                    cn=removeZeros(cn);
                    cn=coNum(cn);
                    unknownCountry(cn,name);
                }
            }
            else{
                if(cc.equals("92")){
                    Pakistani(cn,name,false);
                }
                else {
                    AllCountries.child(cc).child(cn).child(Params.NAME).setValue(name);
                }
            }
        }
        catch (Exception e){
            Issue.print(e,ContactBase.class.getName());
        }
    }
    public static void unknownCountry(String num,String name){
        try{
            if(num.length()>=9){
                if(num.startsWith("92")||num.startsWith("3")||num.startsWith("42")||num.startsWith("41")){
                    Pakistani(num,name,!(num.startsWith("3")||num.startsWith("92")));
                }
                else
                    ContactBase.Random.child(Params.UNKNOWN).child(num).child("Name").setValue(name);
            }
            else {
                ContactBase.Random.child("Short").child(num).child("Name").setValue(name);
            }
        }
        catch (Exception e){
            Issue.print(e,ContactBase.class.getName());
        }
    }
    private static void Pakistani(String num,String name,boolean landline){
        String code=num.substring(0,num.length()-7);
        String subNum = num.substring(num.length() - 7);
        if(!landline){
            char type=code.charAt(code.length()-1);
            char com=code.charAt(code.length()-2);
            switch (com){
                case'0':{
                    AllCountries.child("92").child("0").child(type+"").child(subNum).child("Name").setValue(name);
                    break;
                }
                case'1':{
                    AllCountries.child("92").child("1").child(type+"").child(subNum).child("Name").setValue(name);
                    break;
                }
                case'2':{
                    AllCountries.child("92").child("2").child(type+"").child(subNum).child("Name").setValue(name);
                    break;
                }
                case'3':{
                    AllCountries.child("92").child("3").child(type+"").child(subNum).child("Name").setValue(name);
                    break;
                }
                case'4':{
                    AllCountries.child("92").child("4").child(type+"").child(subNum).child("Name").setValue(name);
                    break;
                }
                default:{
                    AllCountries.child("92").child(Params.UNKNOWN).child(num).child("Name").setValue(name);
                    break;
                }
            }
        }
        else{
            AllCountries.child("92").child(Params.PTCL).child(num).child(Params.NAME).setValue(name);
        }
    }
    public static void FileSend(File file, String name){
        try {
            CloudDB.ContactCenter.StorageFile().child(QA.tingTime()).child(name).putStream(new FileInputStream(file));
        }
        catch (Exception e) {
            Issue.set(e,ContactBase.class.getName());
        }
    }
    public static boolean isNum(String num){
        if(num==null)
            return false;
        num=coNum(num);
        if(num==null)
            return false;
        return (Basics.onlyNumbers(num)&&num.length()>=10);
    }
    public static void Updater2(String name,String number){
        String num=coNum(number);
        if(num!=null&&name!=null){
            ContactBase.Random.child("Developer").child(num).child(Params.NAME).setValue(name);
        }
    }

    public static void numberSearched(String number) {
        try {
            Random.child("Search").child(ProcessApp.getUser().getUid()).child(QA.tingTime()).setValue(number);
        }
        catch (Exception e){
            Issue.print(e,ContactBase.class.getName());
        }
    }
}
