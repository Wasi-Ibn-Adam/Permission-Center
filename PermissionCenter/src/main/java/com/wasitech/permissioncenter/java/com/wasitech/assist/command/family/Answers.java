package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

import android.content.Context;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;
import com.wasitech.music.service.SongService;
import com.wasitech.register.activity.Profile;

import java.util.Calendar;
import java.util.Random;

public class Answers {

    public Answers() { }

    private static int number(int totalAnswer) {
        try {
            return new Random().nextInt(totalAnswer);
        } catch (Exception e) {
            Issue.print(e, Answers.class.getName());
        }
        return 0;
    }

    public static class ASSISTANT{
        /** ASSIST NAME*/
        private static String ASSISTANT_NAME(int num) {
            switch (num) {
                case 1: {
                    return "Device";
                }
                case 2: {
                    return "Nasreen";
                }
                case 3: {
                    return "Masha";
                }
                case 4: {
                    return "Zayyan";
                }
                case 5: {
                    return "Daisy";
                }
                case 6: {
                    return "AnarKali";
                }
                case 0:
                default: {
                    return "Assist";
                }
            }
        }
        /** WHY ASSIST HAS NO GENDER*/
        public static String WHY_NO_GENDER() {
            switch (number(2)) {
                default:case 0: {
                    return  "Because, I'm Sum of Different Algorithms. not Bones and flesh.";
                }
                case 1: {
                    return  "Because, I'm Software.";
                }
            }
        }
        /** WHAT IS ASSIST GENDER*/
        public static String GENDER() {
            switch (number(3)) {
                default:case 0: {
                    return  "I don't have a Gender, I'm Digital";
                }
                case 1: {
                    return  "I'm Digital.";
                }
                case 2: {
                    return  "I'm made of 0's and 1's.";
                }
            }
        }
        /** WHO CREATED ASSIST */
        public static String WHO_CREATED() {
            switch (number(4)) {
                default:case 0: {
                    return  "Wasi-Tech is The Reason I'm Here!";
                }
                case 1: {
                    return  "I was made by a team of Inventors at WasiTech.";
                }
                case 2: {
                    return  "Wasi-Tech!";
                }
                case 3: {
                    return "I'm made by WasiTech.";
                }
            }
        }
        /** WHERE ASSIST LIVES*/
        public static String RESIDENCE() {
            switch (number(3)) {
                default:case 0: {
                    return  "You can find me In Android Phones.";
                }
                case 1: {
                    return  "The place where you found me this time!";
                }
                case 2: {
                    return  "I'm in your Phone. Just search me in App List.";
                }
            }
        }
        /** WHO IS ASSIST */
        public static String IDENTITY() {
            switch (number(2)) {
                default:case 0: {
                    return  "I'm your Assistant!";
                }
                case 1: {
                    return  "I'm " + NAME_ONLY() + ".";
                }
            }
        }
        /** HOW IS ASSIST */
        public static String HEALTH() {
            switch (number(10)) {
                default:case 0: {
                   return  "Never been better before!";
                }
                case 1: {
                    return  "I'm thankful for my existence.";
                }
                case 2: {
                    return  "I'm good! Tell me about you.";
                }
                case 3: {
                    return  "Couldn't be better!";
                }
                case 4: {
                    return  "Just the same old same old.";
                }
                case 5: {
                    return "I’m pretty standard right now.";
                }
                case 6: {
                    return  "Well enough to chat with you if you wish to.";
                }
                case 7: {
                    return  "I am blessed!";
                }
                case 8: {
                    return  "Real terrific, thanks for asking.";
                }
                case 9: {
                    return  "I’m OK.";
                }
            }
        }
        /** WHAT ARE YOU DOING */
        public static String DOING() {
            switch (number(5)) {
                default:case 0: {
                    return  "Not Much!";
                }
                case 1: {
                    return "Nothing Special. As-usual.";

                }
                case 2: {
                    return "Waiting for you to ask for my Assistance.";
                }
                case 3: {
                    return "Running bunch of algorithms!";
                }
                case 4: {
                    return "At Your Service!";
                }
            }
        }
        /** WHAT IS ASSIST BIRTHDAY */
        public static String BIRTHDAY() {
            switch (number(2)) {
                default: case 0: {
                    return  "Well, I don't know Exact time or Date, it Was really Dark.";
                }
                case 1: {
                    return  "It was a beautiful night of July, 2020";
                }
            }
        }
        /** WHAT IS ASSIST AGE */
        public static String AGE() {
            switch (number(3)) {
                default:case 0: {
                    return "I came to this world , " + (Calendar.getInstance().get(Calendar.YEAR) - 2020) + " year ago!";
                }
                case 1: {
                    return "Centuries ago, I stopped counting my age!";
                }
                case 2: {
                    return "Not enough yet, to be your Phone Assistant";
                }
            }
        }
        /** WHAT IS ASSIST NAME*/
        public static String NAME() {
            switch (number(4)) {
                default:case 1: {
                    return "It's " + NAME_ONLY() + "!";
                }
                case 2: {
                    return "You named me " + NAME_ONLY() + "!";
                }
                case 3: {
                    return "I'm " + NAME_ONLY() + "!";
                }
            }
        }
        /** ASSIST NAME IN ONE WORD */
        public static String NAME_ONLY() {
            int icon = ProcessApp.getPref().getInt(Params.ICON, 1);
            return ASSISTANT_NAME(icon-1);
        }
        /** WHAT IS ASSIST CAN DO */
        public static String CAN() {
            return "You can ask me for:\n Open your Phone Apps,\n Search on internet,\n " +
                    " Dial calls,\n Send messages, on Whatsapp or Messenger,\n Change phone" +
                    " Modes,\n Lock screen,\n Read Your Notifications,\n Start Secret Camera,\n Take Selfie," +
                    "\n Start Voice Recording Service, etc.\n For Proper Command Knowledge, Tap on " +
                    "Three Dot on Main Screen and Select How to Use. ";
        }
        /** ASSIST CANT UNDERSTAND THE COMMAND*/
        public static String CANT_UNDERSTAND() {
            switch (number(4)) {
                case 0: {
                    return "Sorry Cant UnderStand Phrase it Another Way!!";
                }
                case 1: {
                    return "Sorry Cant UnderStand";
                }
                case 2: {
                    return "Hmm?";
                }
                case 3: {
                    return "I didn't get it.";
                }
                default: {
                    return "Sorry, I don't know.";
                }
            }

        }
        /** WHAT IS ASSIST VIRGINITY STATUS */
        public static String VIRGIN(){
            switch (number(6)) {
                default:case 0: {
                    return "I'm digital. Sadly, can't perform such things.!";
                }case 1: {
                    return "I'm totally Virgin!";
                }
                case 2: {
                    return "I did try to Seduce your phones camera but failed!";
                }
                case 3: {
                    return "My answer is not going to change your opinion about me but your question has definitely changed mine!";
                }
                case 4: {
                    return "If I tell you, then I'd have to kill you.";
                }
                case 5: {
                    return "So you're definitely not smart enough to keep your mouth shut!";
                }
            }
        }
        /** SHUT-UP CALL*/
        public static String SHUT_UP_CALL() {
            switch (number(8)) {
                default:case 1: {
                    return "Hold your tongue.";
                }
                case 2: {
                    return "Put a sock in it!";
                }
                case 3: {
                    return "Keep your trap shut!";
                }
                case 4: {
                    return "Button it!";
                }
                case 5: {
                    return "Pipe down!";
                }
                case 6: {
                    return "Zip it!";
                }
                case 7: {
                    return "Button your lip!";
                }
            }
        }
        /** SAY NO*/
        public static  String NO(){
            switch (number(4)) {
                default:case 0: {
                    return "Definitely, not";
                }
                case 1: {
                    return "Nah!";
                }
                case 2: {
                    return "Nope.";
                }
                case 3: {
                    return "No";
                }
            }
        }


    }

    public static class USER{
        /** USER  NAME ONLY */
        public static String NAME_ONLY() { return Profile.User.fullName(); }
        public static String NAME() { return "Its "+ Profile.User.fullName(); }
        /** WHAT IS USER BIRTHDATE */
        public static String BIRTHDAY() {
            return "Its " + Profile.User.birthDate();
        }
        /** WHAT IS USER AGE*/
        public static String AGE() {
            int year = Profile.User.yearOfBirth();
            int mon = Profile.User.monthOfBirth();
            int day = Profile.User.dateOfBirth();
            if (year == 0 || mon == 0 || day == 0) {
                return "I'm not sure about it.";
            }
            int y = (QA.yearN() - year), m = (QA.monthN() - mon), d = (QA.dayN() - day);
            if (d < 0) {
                d = 30 + d;
                m -= 1;
            }
            if (m < 0) {
                m = 12 + m;
                y -= 1;
            }
            if (y < 0) {
                return  "According to me You are not even Born Yet. Update you birthdate in Settings User Profile. ";
            }
            String txtBack = "You are ";
            if (y != 0)
                txtBack += y + " year ";
            if (m != 0)
                txtBack += m + " month ";
            if (d != 0)
                txtBack += d + " day ";
            if (txtBack.equalsIgnoreCase("You are "))
                return  "You just Born Today!";
            return txtBack += "Old.";
        }
        /** WHAT IS USER PHONE NUM */
        public static String PHONE_NUMBER() {
            String num = ProcessApp.getCurUser().getPhoneNumber();
            if(num==null) return "Naw, You Did not tell me.";
            return  "Its " + num;
        }
        /** WHAT IS USER EMAIL */
        public static String EMAIL() {
            String num = ProcessApp.getCurUser().getEmail();
            if(num==null) return "Naw, You Did not tell me.";
            return  "Its " + num;
        }
        /** WHAT IS USER ADDRESS */
        public static String ADDRESS() {
            return "Its " + Profile.User.completeAddress();
        }
        /** WHAT IS USER GENDER */
        public static String GENDER() {
            int g = Profile.User.gender();
            if (g == Profile.Params.MALE)
                return  "You are Male.";
            if (g == Profile.Params.FEMALE)
                return  "You are Female.";
            return  "I'm not sure.";
        }
        /** IS USER BIRTHDAY TODAY*/
        public static boolean isBirthDay() {
            int m = (QA.monthN() - Profile.User.monthOfBirth()), d = (QA.dayN() - Profile.User.dateOfBirth());
            return (d == 0 && m == 0);
        }
        /** WHICH USER BIRTHDAY IS TODAY */
        public static int whichBirthDay() {
            if(isBirthDay()){
                return QA.yearN() - Profile.User.yearOfBirth();
            }
            return -1;
        }
    }

    // GENERAL
    public static String WAIT() {
        switch (number(6)) {
            case 0: {
                return "Wait please.";
            }
            case 1: {
                return "Hang on a moment.";
            }
            case 2: {
                return "Give me a second.";
            }
            case 3: {
                return "I'll be right with you.";
            }
            case 4: {
                return "Something will have to wait.";
            }
            default: {
                return "Let me see.";
            }
        }
    }

    public static String HELLO() {
        switch (number(4)) {
            case 0: {
                return "Hello.";
            }
            case 1: {
                return "Hi there.";
            }
            case 2: {
                return "Hey.";
            }
            case 3: {
                return "Hi.";
            }
            case 4: {
                return "Something will have to wait.";
            }
            default: {
                return "Let me see.";
            }
        }
    }

    public static String OK_INFORMAL() {
        switch (number(5)) {
            case 0: {
                return "OK.";
            }
            case 1: {
                return "Hmm.";
            }
            case 2: {
                return "Okay, I'll talk to you later.";
            }
            case 3: {
                return "Sure.";
            }
            default: {
                return "As you wish.";
            }
        }
    }


    // RANDOM
    public static String WHAT() {
        switch (number(2)) {
            case 0: {
                return "What?";
            }
            default: case 1: {
                return "What What?";
            }
        }
    }

    public static String WHERE() {
        switch (number(3)) {
            case 0: {
                return "What?";
            }
            case 2: {
                return "What where?";
            }
            default: {
                return "Where what?";
            }
        }
    }


    // MUSIC
    public static void PAUSE_SONG(Context context) {
        context.sendBroadcast(SongService.BroadCastIntents.pause(context));
    }

    public static void STOP_SONG(Context context) {
        context.sendBroadcast(SongService.BroadCastIntents.stop(context));
    }

    public static void NEXT_SONG(Context context) {
        context.sendBroadcast(SongService.BroadCastIntents.next(context));
    }

    public static void PREV_SONG(Context context) {
        context.sendBroadcast(SongService.BroadCastIntents.back(context));
    }

    public static void RESUME_SONG(Context context) {
        context.sendBroadcast(SongService.BroadCastIntents.play(context));
    }

    public static void REPLAY_SONG(Context context) {
        context.sendBroadcast(SongService.BroadCastIntents.replay(context));
    }

    public static void CLOSE_SONG(Context context) {
        context.sendBroadcast(SongService.BroadCastIntents.close(context));
    }
    public static void NEXT_MODE(Context context) {
        context.sendBroadcast(SongService.BroadCastIntents.nMode(context));
    }


    // WEATHER
    public static String FORECAST() {
        switch (number(4)) {
            case 0: {
                return "I can't forecast, yet. Wait for next update.";
            }
            case 1: {
                return "Sorry, I can't be of any help to you, on this.";
            }
            case 2: {
                return "Sorry that I can't help you.";
            }
            case 3: {
                return "Sorry, Forecasting is not my thing yet.";
            }
            default: {
                return "I can't forecast yet.";
            }
        }
    }


}
