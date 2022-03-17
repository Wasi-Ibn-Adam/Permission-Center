package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.Speak;

import com.wasitech.database.CloudDB;
import com.wasitech.database.ContactBase;

public class RandomWords {
    /*

     * username
     * time of different location

     *   remind me / timer
     *
     * */


    public static String Actions(String str) {
        int num=-1;
        if (ComFuns.socialApp(str)) {
            Speak.stack.push(ComFuns.socialAppNum(str));
            Speak.stack.push(Coder.SOCIAL);
            return " ";
        }
        else if((num=ProcessApp.checkContact(str))!=-1){
            String name=ProcessApp.cList.get(num).getName();
            return name+" is a Contact Name in Your Contact-List";
        }

        Speak.COMMAND_TYPE= CloudDB.DataCenter.HINT;
        if (ComFuns.salam(str)){
            return "Walikum-Assalam!";
        }
        else if (str.contains("selfie") || str.contains("pic") || str.contains("camera") || str.contains("photo")) {
            if (str.contains("selfie") || str.contains("front"))
                Speak.stack.push(Coder.R_CAM_FRONT);
            else
                Speak.stack.push(Coder.R_CAM_BACK);

            Speak.stack.push(Coder.HINT_CAM);
            return "Did you mean to take picture?";
        }
        else if (str.contains("music") ||str.contains("song") || str.contains("audio")) {
            if(str.contains("of")) {
                Speak.stack.push(Coder.R_MUSIC_STOP);
                Speak.stack.push(Coder.HINT_MUSIC);
                return "Did you mean Stop Music?";
            }
            else{
                String p=null;
                if (str.contains("pause")) {
                    Speak.stack.push(Coder.R_SONG_PAUSE);
                    p="Pause";
                }
                else if (str.contains("next")) {
                    Speak.stack.push(Coder.R_SONG_NEXT);
                    p="Next";
                }
                else if (str.contains("prev")) {
                    Speak.stack.push(Coder.R_SONG_PREV);
                    p="Previous";
                }
                else if (str.contains("stop")) {
                    Speak.stack.push(Coder.R_SONG_STOP);
                    p="Stop";
                }
                else if(str.contains("play")) {
                    Speak.stack.push(Coder.R_SONG_PLAY);
                    p="Play";
                }
                if(p!=null){
                    Speak.stack.push(Coder.HINT_SONG);
                    return "Did you mean "+p+" Song?";
                }
            }
        }
        else if (str.contains("record") || str.contains("voice")) {
            if (ComFuns.stop(str))
                Speak.stack.push(Coder.R_RECORD_END);
            else
                Speak.stack.push(Coder.R_RECORD_START);
            Speak.stack.push(Coder.HINT_RECORD);
            return "Did you mean Voice Recording Head?";
        }
        else if (str.contains("cache") && str.contains("clear")) {
            Speak.stack.push(Coder.CLEAR_CACHE);
            return " ";
        }
        else if (ComFuns.wrong(str) && str.contains("password")){
            Speak.stack.push(Coder.HINT_WRONG_PASSWORD);
            return "Did you mean Wrong Password Detection?";
        }
        else if (ContactBase.isNum(str)) {
            Speak.stack.push(Coder.HINT_FIND_NUMBER);
            return "Do you want to Find Owners name of " + str;
        }
        else if (str.equals("help") || str.equals("help me") || (str.contains("help") && str.contains("please"))) {
            Speak.stack.push(Coder.L_HELP);
            return "Help with what?";
        }
        else if (str.equals("haha") || str.equals("nothing") || str.equals("wifi") || str.equals("wi-fi")) {
            return " ";
        }
        else if (str.contains("alarm")) {
            Speak.stack.push(Coder.HINT_SHOW_ALARM);
            return "Wanna Set Alarm or Show Alarm list?";
        }
        else if (str.contains(" ") && str.length() > 7) {
            Speak.stack.push(Coder.HINT_GOOGLE);
            return "Do you want to Google Search " + str + "?";
        }
        else if (str.equalsIgnoreCase("what")){
            return Answers.WHAT();
        }
        else if (str.equalsIgnoreCase("where")){
            return Answers.WHERE();
        }
        else if (str.equalsIgnoreCase("ok")){
            return Answers.OK_INFORMAL();
        }

        Speak.COMMAND_TYPE= CloudDB.DataCenter.UNKNOWN;
        while (!Speak.stack.empty())
            Speak.stack.pop();
        return Answers.ASSISTANT.CANT_UNDERSTAND();
    }
}
