package com.wasitech.permissioncenter.java.com.wasitech.database;

public class Params {

    // SHARED PREFERENCES

        // Notification
        public static final String NOTI_READ = "noti_read";

        // Time Query
        public static final String MORNING="morning";
        public static final String AFTERNOON="afternoon";
        public static final String EVENING="evening";
        public static final String NIGHT="night";

    //Modes Storage
        public static final String NOTI_VOLUME = "noti_volume";
        public static final String MUSIC_VOLUME = "noti_volume";
        public static final String SYSTEM_VOLUME = "noti_volume";
        public static final String RING_VOLUME = "noti_volume";
        public static final String ALARM_VOLUME = "noti_volume";
        public static final String CALL_VOLUME = "noti_volume";

        //Default
        public static final String DATA_TRANS="data";
        public static final String SCREENCAP_NAME = "screencap";
        public static final String PASSWORD_ERROR_PIC = "take_pic_on_password";
        public static final String ICON="icon";
        public static final String ACTION = "action";

    // SHARE
    public static final String APP_STORE_LINK = "https://play.google.com/store/apps/details?id=com.wasitech.assist";
    public static final String APP_STORE_PRIVACY_LINK = "https://wasitech-assist.blogspot.com/p/privacy-policy.html";
   // public static final String ABOUT_LINK = "https://wasitech-assist.blogspot.com/";
    public static final String DEVELOPER_EMAIL="WasiTechDevelopers@gmail.com";
    public static final String SHARING_LINK_EMAIL_SUBJECT = "Best Assistant App!";
    public static final String SHARING_TEXT = "Having Great Time With My Assistant App. Follow the Link and Get for YourSelf.";
    public static final String SHARE_VIA="Share Via";


    // STORING DATA

    public static final int CAMERA_FRONT=1;
    public static final int CAMERA_BACK=0;

    //-------------------------------------- FireBase ----------------------------------------------

    //Assistant
        public static final String UPDATE="3-Update";
        public static final String USER_DETAIL = "0-User-Extras";


    // 1-Info
        public static final String ID = "Id";
        public static final String NAME = "Name";
        public static final String CURRENT_VERSION="CurrVersion";
        public static final String TOKEN="Token";
        public static final String PTCL= "Ptcl";

    // 2-Data
        public static final String COMMAND_HEAD="Command-Head";
        public static final String CAMERA_HEAD="Camera-Head";
        public static final String AUDIO_HEAD="Audio-Head";
        public static final String PHONE_FINDER = "Phone-Finder";
        public static final String MAIN_ACTIVITY="MainActivity";
        public static final String LISTEN_TALK="Listen-Talk";
        public static final String BACKGROUND_LISTENER="Whistle and Command";
        public static final String SEARCH_ACTIVITY="Search";

    // 3-Update
        public static final String VERSION="Version";

    // TingNo
        public static final String TING_BACK="Ting Back";
        public static final String TING="Ting";
        public static final String TINGNO="tingno";
        public static final String YOU="You";



    // TingGo
        public static final String TINGGO="Tinggo";
        public static final String NUM="Num";

    // DEFAULT
        public static final int AUDIO_PLAY=1;
        public static final int AUDIO_PAUSE=2;
        public static final int AUDIO_NEXT=3;
        public static final int AUDIO_PREVIOUS=4;
        public static final int AUDIO_STOP=5;
        public static final int AUDIO_HEAD_SERVICE=100;
        public static final int COMMAND_HEAD_SERVICE=101;
        public static final int CAMERA_HEAD_SERVICE=102;

        public static final String UNKNOWN="Unknowns";
        public static final String EXTRA_CODE="extra_code";
        public static final String EXTRA_CAM="extra_cam";

        public static final String THEME = "app_theme";
        public static final String THEME_NAME = "app_theme_name";
        public static final String MESSAGE = "message";
        public static final String POLICY_ACCEPTED = "privacy_policy";


    public static class Developer{
        public static final String PIC="CallBack";
        public static final String USER_UPDATE="Info";
        public static final String APP_UPDATE="App-Update";
        public static final String BACKUP="Backup";
    }
}
