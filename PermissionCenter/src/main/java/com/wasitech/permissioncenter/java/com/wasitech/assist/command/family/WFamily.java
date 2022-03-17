package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.wasitech.assist.BuildConfig;
import com.wasitech.assist.classes.Speak;
import com.wasitech.assist.classes.VoiceToText;
import com.wasitech.database.Params;

public class WFamily extends ActivityCompat {
    private String txtBack;
    public static String city;
    public WFamily(){ }
    public String wSplitter(String str){
        splitter(str);
        return txtBack;
    }
    private void splitter(String str){
        if (ComFuns.weather(str)||ComFuns.temp(str)||ComFuns.forecast(str)){
            if(weathers(str))
                return;
        }
        if(ComFuns.birthday(str)){
            if(ComFuns.your(str))                                                                   {answers("A6");}
            if(ComFuns.mine(str))                                                                   {answers("U2");}
        }
        if(ComFuns.phone(str)){
            if(ComFuns.manufacturer(str))                                                           { answers("P0");}
            else if(str.contains("model"))                                                          { answers("P2");}
            else if(ComFuns.owner(str)&&ComFuns.thiS(str))                                          { answers("U1");}
        }
        if(ComFuns.gender(str)){
            if(ComFuns.your(str)||ComFuns.you(str))                                                 { answers("A4");}
            if(ComFuns.you(str)&&str.contains("don't"))                                             { answers("A5");}
            if(ComFuns.isAre(str)&&ComFuns.you(str))                                                { answers("A4");}
        }
        if(ComFuns.today(str)){
            if(ComFuns.day(str))                                                                    {answers("T6");}
            else if(ComFuns.isAre(str))                                                             {answers("T9");}
        }
        if(ComFuns.your(str)){
            if(ComFuns.manufacturer(str))                                                           { answers("A8");}
            else if(str.contains("model")||str.contains("version"))                                 { answers("P1");}
            else if(str.contains("voice")&&ComFuns.can(str)&&ComFuns.not(str))                      { answers("G16");}

        }
        if(ComFuns.time(str)){
            if(ComFuns.now(str)||ComFuns.isAre(str))                                                { answers("T4");}
        }
        if(ComFuns.year(str)){
            if(ComFuns.thiS(str)||ComFuns.isAre(str))                                               { answers("T8");}
        }
        if(ComFuns.month(str)){
            if(ComFuns.thiS(str)||ComFuns.isAre(str))                                               { answers("T7");}
        }
        else if(ComFuns.date(str))                                                                  { dateFunction(str); }

        if(ComFuns.location(str)) {
            if(ComFuns.track(str)||ComFuns.find(str))                                               {answers("G10");}
        }
        else if(ComFuns.where(str))                                                  whereFuns(str);
        else if(ComFuns.when(str)&&ComFuns.you(str)&&ComFuns.die(str))                              { answers("G13");}
        else if(ComFuns.you(str)&&ComFuns.die(str))                                                 { answers("G12");}
        else if(ComFuns.you(str)&&ComFuns.love(str))                                                { answers("G6");}
        else if(ComFuns.isAre(str))                                                  isAreFuns(str);
        else if(str.contains("thank you"))                                                          { answers("G0");}
        else if(str.equals("nasreen")||str.equals("assistant")||str.equals("masha"))                { answers("G3");}
        else if (ComFuns.hiHello(str)||str.equalsIgnoreCase(Answers.ASSISTANT.NAME_ONLY()))               { answers("G1");}
        else if (ComFuns.you(str)&& ComFuns.love(str))                                              { answers("G2");}
        else if(ComFuns.good(str)||ComFuns.bad(str))              timeWishes(str,ComFuns.good(str));
        else if(ComFuns.keyAre(str)){
           areFuns(str);
        }
        if(ComFuns.how(str))                                                               how(str);
        if(ComFuns.what(str))                                                             what(str);
        if(ComFuns.who(str))                                                               who(str);
        //if(txtBack==null||txtBack.isEmpty())                                                        { answers("not");}
    }

    private void how(String str){
        if(ComFuns.your(str)){
            if(ComFuns.name(str)&&ComFuns.change(str))                                              { answers("G18");}
        }
        if(ComFuns.isAre(str)){
            if(ComFuns.you(str)) {
                if(ComFuns.mine(str))                                                               { answers("G4");}
                else                                                                                { answers("A0");}
            }
        }
        if(ComFuns.old(str))                                                 { ageFactor(str);}
    }
    private void who(String str){
        if(ComFuns.isAre(str)){
            if(ComFuns.you(str))                                             { answers("A1");}
            else if(ComFuns.mine(str))                                       { answers("U0");}
        }
        else if((ComFuns.manufacturer(str) ||ComFuns.make(str)||ComFuns.made(str)
                ||ComFuns.developed(str)||ComFuns.build(str)) &&(ComFuns.you(str))) { answers("A9"); }
    }
    private void what(String str){
        if(ComFuns.name(str)) {
            if (ComFuns.your(str))                                                                  { answers("A10");}
            else if (ComFuns.mine(str))                                                             { answers("U0");}
        }
        else if((ComFuns.going(str)&&str.contains(" on"))||
                ComFuns.up(str)||(ComFuns.you(str)&&ComFuns.doing(str)))                            { answers("A3");}
        else if(ComFuns.keyAre(str)&&ComFuns.keyYou(str))                                           { answers("A1");}
        else if(ComFuns.you(str)&&ComFuns.can(str))                                                 { answers("A2");}
        else if(str.contains("happen"))                                                             { answers("G17");}
        else if(ComFuns.age(str))                                          { ageFactor(str);}
        else if(ComFuns.yesterday(str)||ComFuns.today(str)||ComFuns.tomorrow(str))                  { dateFunction(str);}
        else if(ComFuns.age(str))                                          { ageFactor(str); }
    }


    private void ageFactor(String str){
        if(ComFuns.your(str)||ComFuns.you(str))            { answers("A11"); }
        else if(ComFuns.iam(str)||ComFuns.mine(str))       { answers("U3"); }
    }
    private void dateFunction(String str){
        if(ComFuns.today(str)){
            if(ComFuns.date(str))                                               { answers("T5"); }
            else                                                                { answers("T6"); }
        }
        else if(ComFuns.tomorrow(str)){
            if(ComFuns.date(str))                                               { answers("Tt1"); }
            else if(ComFuns.day(str))                                           { answers("Tt2"); }
            else                                                                { answers("Tt2"); }
        }
        else{
            if(ComFuns.date(str))                                               { answers("Ty1"); }
            else if(ComFuns.day(str))                                           { answers("Ty2"); }
            else                                                                { answers("Ty2"); }
        }
    }
    private void timeWishes(String str,boolean isGood){
        if(isGood){
            if(ComFuns.night(str))                                                                  { answers("T3");}
            else if(ComFuns.morning(str))                                                           { answers("T0");}
            else if(ComFuns.evening(str))                                                           { answers("T2");}
            else if(ComFuns.afterNoon(str))                                                         { answers("T1");}
        }
        else if(ComFuns.day(str)||ComFuns.morning(str)||ComFuns.evening(str)
                ||ComFuns.afterNoon(str)||ComFuns.night(str))                                       { answers("G9"); }
    }
    private void areFuns(String str) {
        if(ComFuns.keyYou(str)) {
            if (ComFuns.keyOk(str))                                                                 answers("G7");
            else if(ComFuns.keyNice(str))                                                           answers("G8");
        }
    }
    private void isAreFuns(String str){
        if(ComFuns.keyYou(str)){
            if(ComFuns.awesome(str))                                                                           { answers("G14");}
            else if(ComFuns.awful(str))                                                                        { answers("G15");}
            else if(ComFuns.bad(str))                                                                          { answers("G5");}
        }


    }
    private void whereFuns(String str){
        if(ComFuns.you(str)){
            if(ComFuns.live(str))                                                                               { answers("A7");}
            else if(ComFuns.sleep(str))                                                                         { answers("G11");}
        }
    }
    private boolean weathers(String str){
        if(ComFuns.weather(str)||ComFuns.forecast(str)||ComFuns.temp(str)){
            if(ComFuns.tomorrow(str)){
                answers("WN");
                return false;
            }
            else if(ComFuns.temp(str)){
                if(ComFuns.in(str)){
                    locationCropper(str);
                    VoiceToText.stack.push(Coder.R_WEATHER_L_T);VoiceToText.stack.push(Coder.WEATHER);
                    answers("W");
                    return true;
                }
                else if(ComFuns.today(str)||ComFuns.now(str)){
                    VoiceToText.stack.push(Coder.R_WEATHER_T);VoiceToText.stack.push(Coder.WEATHER);
                    answers("W");
                    return true;
                }
            }
            else if(ComFuns.weather(str)){
                if(ComFuns.in(str)){
                    locationCropper(str);
                    VoiceToText.stack.push(Coder.R_WEATHER_L_N);VoiceToText.stack.push(Coder.WEATHER);
                    answers("W");
                    return true;
                }
                else if(ComFuns.forecast(str)){
                    VoiceToText.stack.push(Coder.R_WEATHER_F);VoiceToText.stack.push(Coder.WEATHER);
                    answers("W");
                    return true;
                }
                else if(ComFuns.today(str)||ComFuns.now(str)){
                    VoiceToText.stack.push(Coder.R_WEATHER_N);VoiceToText.stack.push(Coder.WEATHER);
                    answers("W");
                    return true;
                }
            }
        }
        return false;
    }

    private void locationCropper(String str) {
        int i=-1;
        if(str.contains(" in "))
            i=str.indexOf(" in ")+4;
        if(i==-1)
            i=str.indexOf("in ")+3;
        String p1=str.substring(i);
        if(p1.contains(" ")){
            String[]p=p1.split(" ");
            if(p[0].length()>4)
                WFamily.city=p[0];
            else if(p.length>2)
                WFamily.city=p[0]+" "+p[1];
            else
                WFamily.city=p1;
        }
        else
            WFamily.city=p1;
    }

    private void answers(String code){
        switch (code){
            case "A0":{txtBack= Answers.ASSISTANT.HEALTH(); break;}
            case "A1":{txtBack= Answers.ASSISTANT.IDENTITY(); break;}
            case "A2":{txtBack= Answers.ASSISTANT.CAN(); break;}
            case "A3":{txtBack= Answers.ASSISTANT.DOING(); break;}
            case "A4":{txtBack= Answers.ASSISTANT.GENDER(); break;}
            case "A5":{txtBack= Answers.ASSISTANT.WHY_NO_GENDER(); break;}
            case "A6":{txtBack= Answers.ASSISTANT.BIRTHDAY(); break;}
            case "A7":{txtBack= Answers.ASSISTANT.RESIDENCE(); break;}
            case "A8":
            case "A9": {txtBack= Answers.ASSISTANT.WHO_CREATED(); break;}
            case"A10":{txtBack= Answers.ASSISTANT.NAME(); break;}
            case"A11":{txtBack= Answers.ASSISTANT.AGE(); break;}
            case"A12":{txtBack= " "; break;}

            case "U0":{txtBack= Answers.USER.NAME();break;}
            case "U1":{txtBack= Answers.USER.NAME_ONLY();break; }
            case "U2":{txtBack= Answers.USER.BIRTHDAY(); break;}
            case "U3":{txtBack= Answers.USER.AGE(); break;}
            case "U4":{txtBack=""; break;}

            case "P0":{txtBack="Your Phone Manufacturer is "+ Build.MANUFACTURER; break;}
            case "P1":{txtBack="Its "+ BuildConfig.VERSION_NAME+" ."; break;}
            case "P2":{txtBack="Your Phone Model is "+Build.MODEL; break;}
            case "P3":{txtBack=""; break;}

            case "G0":{txtBack= "Your Welcome."; break;}
            case "G1":{txtBack= Answers.HELLO(); break;}
            case "G2":{txtBack="I Love My Creator! and Also His Creator!"; break;}
            case "G3":{txtBack="Yes?"; break;}
            case "G4":{txtBack="For me You are Good."; break;}
            case "G5":{txtBack="Sorry, I will do my Best to be Better."; break;}
            case "G6":{txtBack="Love you too!"; break;}
            case "G7":{txtBack="Absolutely! I'm Digital."; break;}
            case "G8":{txtBack="Damn, I don't know."; break;}
            case "G9":{txtBack="What happened!"; break;}
            case"G10":{txtBack= "No, But You Can Suggest to Developers."; break;}
            case"G11":{txtBack= "I'm a Software. I can't sleep."; break;}
            case"G12":{txtBack= "I'm Virtual Assistant, But Your words are still very Real. Chose them Wisely."; break;}
            case"G13":{txtBack= "Once my Server Crashed, but Thank God! I was saved before My funeral."; break;}
            case"G14":{txtBack= "Thank You for Saying so, I really appreciate it."; break;}
            case"G15":{txtBack= "Please don't talk to me Like that."; break;}
            case"G16":{txtBack= "What about now?";Speak.stack.push(Coder.L_FUN);break;}
            case"G17":{txtBack= "Nothing!"; break;}
            case"G18":{txtBack= "To change my name, Click on three dots on right top corner of main menu, then select settings, and select your desired Name from Assistant name Tab  ";break;}
            case"G19":{txtBack= " ";break;}

            case "T0":{ txtBack=QA.morAfterEveNight(Params.MORNING); break;}
            case "T1":{ txtBack=QA.morAfterEveNight(Params.AFTERNOON); break;}
            case "T2":{ txtBack=QA.morAfterEveNight(Params.EVENING); break;}
            case "T3":{ txtBack=QA.morAfterEveNight(Params.NIGHT); break;}
            case "T4":{ txtBack= "Now TIME is " +QA.time(); break;}
            case "T5":{ txtBack= "Today is " +QA.date();
                if(Answers.USER.isBirthDay()){
                    int n=Answers.USER.whichBirthDay();
                    if(n>0){
                        txtBack+= " and also Your "+QA.thDay(n)+" BirthDay!";
                    }
                    else
                        txtBack+= " and also Your BirthDay!";
                }
                break;
            }
            case "T6":{ txtBack= "Today is " +QA.day();
                if(Answers.USER.isBirthDay()){
                    int n=Answers.USER.whichBirthDay();
                    if(n>0){
                        txtBack+= "and also Your "+QA.thDay(n)+" BirthDay!";
                    }
                    else
                        txtBack+= " and also Your BirthDay!";
                }
                break;
            }
            case "T7":{ txtBack= "This is " +QA.month(); break;}
            case "T8":{ txtBack= "This is " +QA.year(); break;}
            case "T9":{ txtBack= "Today is " +QA.day()+"\n" +QA.date(); break;}
            case "Tt1":{ txtBack= "Tomorrow is " +QA.date(QA.getDate(1)); break;}
            case "Tt2":{ txtBack= "Tomorrow is " +QA.day(QA.getDate(1)); break;}
            case "Ty1":{ txtBack= "Yesterday was " +QA.date(QA.getDate(-1)); break;}
            case "Ty2":{ txtBack= "Yesterday was " +QA.day(QA.getDate(-1)); break;}

            case "W":{ txtBack= " ";break;}
            case "WN":{ txtBack= Answers.FORECAST();break;}

            case "not":{txtBack = "Sorry Cant Understand!!";break;}
        }
    }
}
