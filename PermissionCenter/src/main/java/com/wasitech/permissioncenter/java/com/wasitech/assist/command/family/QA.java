package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

import android.annotation.SuppressLint;
import android.content.Context;

import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QA {
    public Context context;
    public QA(Context context){
        this.context=context;
    }

    private static Date getDate(){return new Date();}

    public static String date(){
        String date= toStringDate(getDate());
        String []test=date.split(" ");
        return (test[2] +" "+ test[1] +" "+ test[3] );
    }
    public static String date(Date d){
        String date= toStringDate(d);
        String []test=date.split(" ");
        return (test[2] +" "+ test[1] +" "+ test[3] );
    }
    public static int year(){
        String date= toStringDate(getDate());
        String []test=date.split(" ");
        return Integer.parseInt(test[3]);
    }
    public static int yearN(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    public static int year(Date d){
        String date= toStringDate(d);
        String []test=date.split(" ");
        return Integer.parseInt(test[3]);
    }
    public static String month(){
        String date= toStringDate(getDate());
        String []test=date.split(" ");
        return (namesMonth(test[1].toLowerCase()));
    }
    public static int monthN(){
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }
    public static String month(Date d){
        String date= toStringDate(d);
        String []test=date.split(" ");
        return (namesMonth(test[1].toLowerCase()));
    }
    private static String namesMonth(String str){
        switch (str){
            case "jan":{return "January";}
            case "feb":{return "February";}
            case "mar":{return "March";}
            case "apr":{return "April";}
            case "may":{return "May";}
            case "jun":{return "June";}
            case "jul":{return "July";}
            case "aug":{return "August";}
            case "sep":{return "September";}
            case "oct":{return "October";}
            case "nov":{return "November";}
            case "dec":{return "December";}
            default:return str;
        }
    }
    public static String tingTime(){
        String date= toStringDate(getDate());
        return date.substring(date.indexOf(" ")).trim();
    }
    public static String timeDate(Date d){
        String date= toStringDate(d);
        return date.substring(date.indexOf(" ")).trim();
    }
    public static String dateAndTimeInMinutes() {
        String t[]= tingTime().split(":");
        return t[0]+":"+t[1];
    }
    public static String dateAndTimeInMinutes(Date d) {
        String t[]=timeDate(d).split(":");
        return t[0]+":"+t[1];
    }
    public static String day() {
        String date = toStringDate(getDate());
        String[] test = date.split(" ");
        return (namesDay(test[0].toLowerCase()));
    }
    public static int dayN() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    public static String day(Date d) {
        String date = toStringDate(d);
        String[] test = date.split(" ");
        return (namesDay(test[0].toLowerCase()));
    }
    private static String namesDay(String str){
        switch (str){
            case "mon":{return "Monday";}
            case "tue":{return "Tuesday";}
            case "wed":{return "Wednesday";}
            case "thu":{return "Thursday";}
            case "fri":{return "Friday";}
            case "sat":{return "Saturday";}
            case "sun":{return "Sunday";}
            default:return str;
        }
    }
    public static String cTime(){
        String[] data=toStringDate(getDate()).split(" ");
        return data[4];
    }
    public static String time(){
        String date= toStringDate(getDate());
        String []test=date.split(" ");
        String[]timePart=test[4].split(":");
        String returning;
        if(Integer.parseInt(timePart[0])>12){
            returning=Integer.parseInt(timePart[0])-12+":"+timePart[1]+" PM";
        }
        else if(Integer.parseInt(timePart[0])==12){
            returning=timePart[0]+":"+timePart[1]+" Noon";
        }
        else if(Integer.parseInt(timePart[0])==0){
            returning=timePart[1]+" minutes of MidNight";
        }
        else{
            returning=timePart[0]+":"+timePart[1]+" AM";
        }
        return (returning);
    }
    public static int timeNumber(String str){
        if(str==null)
            return -1;
        if(Basics.onlyNumbers(str))
            return Integer.parseInt(str);

        if(ComFuns.N1(str)) {
            if(ComFuns.N20(str)) return 21;
            if(ComFuns.N30(str)) return 31;
            if(ComFuns.N40(str)) return 41;
            if(ComFuns.N50(str)) return 51;
            return 1;
        }
        if(ComFuns.N2(str)) {
            if(ComFuns.N20(str)) return 22;
            if(ComFuns.N30(str)) return 32;
            if(ComFuns.N40(str)) return 42;
            if(ComFuns.N50(str)) return 52;
            return 2;
        }
        if(ComFuns.N3(str)) {
            if(ComFuns.N_teen(str)) return 13;
            if(ComFuns.N20(str)) return 23;
            if(ComFuns.N30(str)) return 33;
            if(ComFuns.N40(str)) return 43;
            if(ComFuns.N50(str)) return 53;
            return 3;
        }
        if(ComFuns.N4(str)) {
            if(ComFuns.N_teen(str)) return 14;
            if(ComFuns.N20(str)) return 24;
            if(ComFuns.N30(str)) return 34;
            if(ComFuns.N40(str)) return 44;
            if(ComFuns.N50(str)) return 54;
            return 4;
        }
        if(ComFuns.N5(str)) {
            if(ComFuns.N_teen(str)) return 15;
            if(ComFuns.N20(str)) return 25;
            if(ComFuns.N30(str)) return 35;
            if(ComFuns.N40(str)) return 45;
            if(ComFuns.N50(str)) return 55;
            return 5;
        }
        if(ComFuns.N6(str)) {
            if(ComFuns.N_teen(str)) return 16;
            if(ComFuns.N20(str)) return 26;
            if(ComFuns.N30(str)) return 36;
            if(ComFuns.N40(str)) return 46;
            if(ComFuns.N50(str)) return 56;
            return 6;
        }
        if(ComFuns.N7(str)) {
            if(ComFuns.N_teen(str)) return 17;
            if(ComFuns.N20(str)) return 27;
            if(ComFuns.N30(str)) return 37;
            if(ComFuns.N40(str)) return 47;
            if(ComFuns.N50(str)) return 57;
            return 7;
        }
        if(ComFuns.N8(str)) {
            if(ComFuns.N_teen(str)) return 18;
            if(ComFuns.N20(str)) return 28;
            if(ComFuns.N30(str)) return 38;
            if(ComFuns.N40(str)) return 48;
            if(ComFuns.N50(str)) return 58;
            return 8;
        }
        if(ComFuns.N9(str)) {
            if(ComFuns.N_teen(str)) return 19;
            if(ComFuns.N20(str)) return 29;
            if(ComFuns.N30(str)) return 39;
            if(ComFuns.N40(str)) return 49;
            if(ComFuns.N50(str)) return 59;
            return 9;
        }
        if(ComFuns.N10(str)) return 10;
        if(ComFuns.N11(str)) return 11;
        if(ComFuns.N12(str)) return 12;
        if(ComFuns.N20(str)) return 20;
        if(ComFuns.N30(str)) return 30;
        if(ComFuns.N40(str)) return 40;
        if(ComFuns.N50(str)) return 50;
        if(ComFuns.N60(str)) return 60;
        return -1;
    }
    public static String thDay(int n){
        if(n<21)
        switch (n){
            case 0 :return "Zeroth";
            case 1 :return "First";
            case 2 :return "Second";
            case 3 :return "Third";
            case 4 :return "Fourth";
            case 5 :return "Fifith";
            case 6 :return "Sixth";
            case 7 :return "Seventh";
            case 8 :return "Eight";
            case 9 :return "Ninth";
            case 10 :return "Tenth";
            case 11 :return "Eleventh";
            case 12 :return "Twelfth";
            case 13 :return "Thirteenth";
            case 14 :return "Fourteenth";
            case 15 :return "Fifteenth";
            case 16 :return "Sixteenth";
            case 17 :return "Seventeenth";
            case 18 :return "Eighteenth";
            case 19 :return "Nineteenth";
            case 20 :return "Twentieth";
        }
        else if(n<30)
                return "Twenty "+thDay(n-20);
        if(n==30)
            return "Thirtieth";
        else if(n<40)
                return "Thirty "+thDay(n-30);
        if(n==40)
            return "Fortieth";
        else if(n<50)
                return "Forty "+thDay(n-40);
            return "";
    }

    public static String toStringDate(Date coming) {
        String dateString;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("EE MMM dd yyyy HH:mm:ss");
        dateString = format.format( coming );
        return dateString;
    }
    public static String morAfterEveNight(String str){
        String date= toStringDate(new Date());
        String []test=date.split(" ");
        String[]timePart=test[4].split(":");
        switch (str){
            case Params.MORNING:{
                if(Integer.parseInt(timePart[0])==12){
                    return "Good Noon!!";
                }
                else if(Integer.parseInt(timePart[0])>=0&&Integer.parseInt(timePart[0])<4){
                    return "It is too early to be Morning! Anyway, Have a Good Day!";
                }
                else if(Integer.parseInt(timePart[0])>=4&&Integer.parseInt(timePart[0])<12){
                    return "Good Morning!";
                }
                else if(Integer.parseInt(timePart[0])>12&&Integer.parseInt(timePart[0])<16){
                    return "It is too Late to be Morning! Anyway, Good AfterNoon!";
                }
                else if(Integer.parseInt(timePart[0])>=16&&Integer.parseInt(timePart[0])<18){
                    return "Good Evening! Time is nearly to sleep again, WakeUp.";
                }
                else{
                    return "Seems Like You need a Sleep, Good Night!";
                }
            }
            case Params.AFTERNOON:{
                if(Integer.parseInt(timePart[0])==12){
                    return "Good Noon!!";
                }
                else if(Integer.parseInt(timePart[0])>=0&&Integer.parseInt(timePart[0])<4){
                    return "It's Night not Day, Good Night!";
                }
                else if(Integer.parseInt(timePart[0])>=4&&Integer.parseInt(timePart[0])<12){
                    return "It's not even Noon Yet! So wishing You Good Morning!";
                }
                else if(Integer.parseInt(timePart[0])>12&&Integer.parseInt(timePart[0])<16){
                    return "Good AfterNoon!";
                }
                else if(Integer.parseInt(timePart[0])>=16&&Integer.parseInt(timePart[0])<18){
                    return "Good Evening!";
                }
                else{
                    return "Seems Like You need a Sleep, Good Night!";
                }
            }
            case Params.EVENING:{
                if(Integer.parseInt(timePart[0])==12){
                    return "Good Noon!!";
                }
                else if(Integer.parseInt(timePart[0])>=0&&Integer.parseInt(timePart[0])<4){
                    return "It's Night not Evening, Good Night!";
                }
                else if(Integer.parseInt(timePart[0])>=4&&Integer.parseInt(timePart[0])<12){
                    return "Take a Look around it's Morning, So wishing You Good Morning!";
                }
                else if(Integer.parseInt(timePart[0])>12&&Integer.parseInt(timePart[0])<16){
                    return "Not Evening yet it's AfterNoon!";
                }
                else if(Integer.parseInt(timePart[0])>=16&&Integer.parseInt(timePart[0])<18){
                    return "Good Evening!";
                }
                else{
                    return "It's Late to say Evening, Good Night!";
                }
            }
            case Params.NIGHT:{
                if(Integer.parseInt(timePart[0])==12){
                    return "It's Noon!!";
                }
                else if(Integer.parseInt(timePart[0])>=4&&Integer.parseInt(timePart[0])<12){
                    return "Night is Gone, Time to say Good Morning!";
                }
                else if(Integer.parseInt(timePart[0])>12&&Integer.parseInt(timePart[0])<16){
                    return "It's AfterNoon!";
                }
                else if(Integer.parseInt(timePart[0])>=16&&Integer.parseInt(timePart[0])<18){
                    return "Good Evening!";
                }
                else{
                    return "Good Night!";
                }
            }
            default:{
                return "";
            }
        }
    }
    public static Date getDate(int n){
        Date d=new Date();
        Calendar c=Calendar.getInstance();
        c.add(Calendar.HOUR,24*n);
        return c.getTime();
    }

    public static Date getTingDateBack(String time) {
        if(time!=null){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter6=new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
            try {
                return formatter6.parse(time);
            } catch (ParseException e) {
                Issue.print(e, QA.class.getName());
            }
        }
        return new Date();
    }
}
