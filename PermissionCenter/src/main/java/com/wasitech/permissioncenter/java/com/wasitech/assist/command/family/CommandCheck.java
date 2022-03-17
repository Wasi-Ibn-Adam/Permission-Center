package com.wasitech.permissioncenter.java.com.wasitech.assist.command.family;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.wasitech.assist.actions.Open;
import com.wasitech.assist.activities.AlarmActivity;
import com.wasitech.assist.classes.Modes;
import com.wasitech.assist.classes.Speak;
import com.wasitech.assist.services_receivers.AudioRecordingHeadService;
import com.wasitech.assist.services_receivers.BackgroundService;
import com.wasitech.assist.services_receivers.CameraHeadService;
import com.wasitech.assist.services_receivers.CommandHeadService;
import com.wasitech.assist.web.AssistWeb;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.contact.activity.ContactListAct;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;
import com.wasitech.music.activity.MusicListAct;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionChecker;

public class CommandCheck extends ActivityCompat {
    private final Context context;
    private String txtBack;

    public CommandCheck(Context context) {
        this.context = context;
    }

    public boolean checkActions(String str) {
        new Actions().choose(str);

        if (Actions.stack != null && Actions.stack.size() != 0) {
            String action = Actions.stack.pop();
            String data = Actions.stack.pop();
            switch (action) {
                case Actions.CONST.ACTION_ALARM_SET: {
                    responses("14", "Tell me Alarm time?");
                    Speak.stack.push(Coder.SET_ALARM);
                    return true;
                }
                case Actions.CONST.ACTION_ALARM_WAKE: {
                    if (ComFuns.after(str) || ComFuns.at(str)) {
                        String time = AlarmActivity.setAlarm(context, data, "Wake up,  you hoo, Wake up!");
                        if (time != null)
                            responses("14", "OK, I've set alarm for " + time);
                        else {
                            responses("14", "When should I wake you?");
                            Speak.stack.push(Coder.SET_ALARM);
                        }
                    } else {
                        responses("14", "When should I wake you?");
                        Speak.stack.push(Coder.SET_ALARM);
                    }
                    return true;
                }
                case Actions.CONST.ACTION_ALARM_SHOW: {
                    context.startActivity(Intents.alarmShow(context.getApplicationContext()));
                    responses("14", " ");
                    return true;
                }
                case Actions.CONST.ACTION_APPLICATION_FIND: {
                    break;
                }
                case Actions.CONST.ACTION_APPLICATION_OPEN: {
                    responses("14", new Open(context).openApps(data));
                    return true;
                }
                case Actions.CONST.ACTION_AUDIO_RECORD: {
                    if (ComFuns.onFunction(data)) {
                        if (Basics.isMyServiceRunning(context, AudioRecordingHeadService.class)) {
                            responses("14", "Recording Service is Already Active");
                        } else {
                            context.startActivity(Intents.audioHeadPermission(context, true));
                            responses("14", " ");
                        }
                    } else {
                        if (context.stopService(Intents.AudioHead(context)))
                            responses("14", "Recording Service is Stopped");
                        else
                            responses("14", "Recording Service is not Active");
                    }
                    return true;
                }
                case Actions.CONST.ACTION_AUDIO_SEND: {
                    break;
                }

                case Actions.CONST.ACTION_BYE: {
                    if(Basics.isMyServiceRunning(context, CommandHeadService.class)){
                        new Handler().postDelayed(() -> System.exit(0), 500);
                    }
                    else{
                        Speak.stack.push(Coder.EXIT);
                    }
                    responses("14", "Good Bye!");
                    return true;
                }
                case Actions.CONST.ACTION_BLUETOOTH_TURN: {
                    responses("14", Basics.BlueTooth.set(ComFuns.onFunction(data)));
                    return true;
                }
                case Actions.CONST.ACTION_BLUETOOTH_CONNECT: {
                    Basics.BlueTooth.connection(context);
                    responses("14", "Select Device to Connect with.");
                    new Handler().postDelayed(() -> Toast.makeText(context, Basics.BlueTooth.connected(context), Toast.LENGTH_SHORT).show(), 10000);
                    return true;
                }
                case Actions.CONST.ACTION_BLUETOOTH_DISCONNECT: {
                    break;
                }
                case Actions.CONST.ACTION_BACKGROUND_TURN: {
                    if (ComFuns.onFunction(data)) {

                        if (Basics.isMyServiceRunning(context, BackgroundService.class)) {
                            responses("14", "Service is Already Activated.");
                        } else {
                            if (Permission.Check.microphone(context)) {
                                context.startService(Intents.Background(context));
                                responses("14", "BackGround Service is Active. Now Whistle and command.");
                            } else {
                                context.startActivity(Intents.backgroundPermission(context, true));
                                responses("14", " ");
                            }
                        }
                    } else {
                        if (context.stopService(Intents.Background(context))) {
                            responses("14", "Service is Stopped.");
                        } else {
                            responses("14", "BackGround Service is not Active.");
                        }
                    }
                    return true;
                }

                case Actions.CONST.ACTION_CANT_DELETE: {
                    responses("14", "Sorry, I can't delete anything. You have to delete things manually.");
                    break;
                }
                case Actions.CONST.ACTION_CALL_ACCEPT: {
                    break;
                }
                case Actions.CONST.ACTION_CALL_SHOW: {
                    break;
                }
                case Actions.CONST.ACTION_CALL_RECORD: {
                    responses("14", "Call Recording is Illegal, but if you You want it Put the call on Speaker and Use Normal Recording Service to Record.");
                    return true;
                }
                case Actions.CONST.ACTION_CALL_MAKE: {
                    if (!Permission.Check.contacts(context.getApplicationContext())) {
                        context.startActivity(Intents.dialerPermission(context, data));
                    } else {
                        context.startActivity(ContactListAct.Intents.dailContacts(context, data));
                    }
                    responses("14", " ");
                    return true;
                }
                case Actions.CONST.ACTION_CHAT_OPEN: {
                    break;
                }
                case Actions.CONST.ACTION_CONTACT_CREATE: {
                    break;
                }
                case Actions.CONST.ACTION_CONTACT_EDIT: {
                    break;
                }
                case Actions.CONST.ACTION_CONTACT_SHOW: {
                    if (!Permission.Check.contacts(context.getApplicationContext())) {
                        context.startActivity(Intents.contactShowPermission(context, data));
                    } else {
                        context.startActivity(ContactListAct.Intents.showContacts(context, data));
                    }
                    responses("14", " ");
                    return true;
                }
                case Actions.CONST.ACTION_CONTACT_FIND: {
                    context.startActivity(Intents.NumberFind(context));
                    responses("14", " ");
                    return true;
                }

                case Actions.CONST.ACTION_DOCUMENT_FIND: {
                    break;
                }
                case Actions.CONST.ACTION_DOCUMENT_OPEN: {
                    break;
                }

                case Actions.CONST.ACTION_EMAIL_SEND: {
                    break;
                }

                case Actions.CONST.ACTION_FLASH_TURN: {
                    if (!Permission.Check.camera(context)) {
                        context.startActivity(Intents.flashPermission(context, ComFuns.onFunction(data)));
                        responses("14", " ");
                    } else {
                        responses("14", Basics.FlashLight(context, ComFuns.onFunction(data)));
                    }
                    return true;
                }

                case Actions.CONST.ACTION_MEDIA_MUTE: {
                    new Modes(context).mediaMute(ComFuns.unmute(data));
                    return true;
                }
                case Actions.CONST.ACTION_MODE_MUTE: {
                    responses("14", new Modes(context, data).whichApply());
                    return true;
                }

                case Actions.CONST.ACTION_NOTIFICATION_READ: {
                    String sender;
                    if (ComFuns.offFunction(data)) {
                        if (ProcessApp.getPref().getBoolean(Params.NOTI_READ, true)) {
                            ProcessApp.getPref().edit().putBoolean(Params.NOTI_READ, false).apply();
                            sender = "Notification Read-Mode De-Activated";
                        } else {
                            sender = "Already Deactivated Notification Read-Mode.";
                        }
                        new LocalDB(context.getApplicationContext()).deleteNotification();
                    } else if (Basics.isNLServiceRunning(context.getApplicationContext())) {
                        if (ProcessApp.getPref().getBoolean(Params.NOTI_READ, true)) {
                            sender = "Notification Read-Mode Already Activated";
                        } else {
                            ProcessApp.getPref().edit().putBoolean(Params.NOTI_READ, true).apply();
                            sender = "Notification Read-Mode Activated";
                        }
                    } else {
                        sender = "Require Permission to Read Notifications.";
                        context.startActivity(Intents.NotiAppActivity(context));
                    }
                    responses("14", sender);
                    return true;
                }

                case Actions.CONST.ACTION_PICTURE_TAKE: {
                    if (ComFuns.selfie(str) || ComFuns.front(str))
                        context.startActivity(Intents.picPermission(context, PermissionChecker.FRONT_CAM));
                    else
                        context.startActivity(Intents.picPermission(context, PermissionChecker.RARE_CAM));
                    responses("14", "  ");
                    return true;
                }
                case Actions.CONST.ACTION_PICTURE_SHOW: {
                    if (!Permission.Check.storage(context)) {
                        context.startActivity(Intents.picShowPermission(context));
                    } else {
                        context.startActivity(Intents.PictureListActivity(context));
                    }
                    return true;
                }
                case Actions.CONST.ACTION_REMINDER_SET: {
                    break;
                }
                case Actions.CONST.ACTION_SEARCH: {
                    search(data);
                    return true;
                }
                case Actions.CONST.ACTION_STOP: {
                    break;
                }
                case Actions.CONST.ACTION_SMS_SEND: {
                    responses("14", "What is your Message:");
                    Speak.stack.push(Coder.L_MESSAGE);
                    return true;
                }
                case Actions.CONST.ACTION_SCREEN_SHOT: {
                    responses("next", "");
                    return true;
                }
                case Actions.CONST.ACTION_SCREEN_TURN: {
                    context.startActivity(Intents.lockPermission(context, !ComFuns.onFunction(data)));
                    responses("14", "  ");
                    return true;
                }
                case Actions.CONST.ACTION_SCREEN_SHOT_SHOW: {
                    break;
                }
                case Actions.CONST.ACTION_SECRET_CAMERA_TURN: {
                    if (ComFuns.onFunction(data)) {
                        if(Basics.isMyServiceRunning(context, CameraHeadService.class)) {
                            responses("14", "Secret Cam Service is Already Active");
                        } else {
                            context.startActivity(Intents.cameraHeadPermission(context, true));
                            responses("14", " ");
                        }
                    } else {
                        if (context.stopService(Intents.CamHead(context))) {
                            responses("14", "Service Stopped.");
                        } else {
                            responses("14", "Already Stopped.");
                        }
                    }
                    return true;
                }
                case Actions.CONST.ACTION_SONG_FIND: {
                    if (!Permission.Check.storage(context)){
                        context.startActivity(Intents.musicFindPermission(context, data));
                    } else {
                        context.startActivity(MusicListAct.Intents.search(context, MusicListAct.Intents.SHOW,data));
                    }
                    responses("14", " ");
                    return true;
                }
                case Actions.CONST.ACTION_SONG_PAUSE: {
                    Answers.PAUSE_SONG(context);
                    return true;
                }
                case Actions.CONST.ACTION_SONG_REPLAY: {
                    Answers.REPLAY_SONG(context);
                    return true;
                }
                case Actions.CONST.ACTION_SONG_PREVIOUS: {
                    Answers.PREV_SONG(context);
                    return true;
                }
                case Actions.CONST.ACTION_SONG_NEXT: {
                    Answers.NEXT_SONG(context);
                    return true;
                }
                case Actions.CONST.ACTION_SONG_RESUME: {
                    Answers.RESUME_SONG(context);
                    return true;
                }
                case Actions.CONST.ACTION_SONG_SHOW: {
                    if (!Permission.Check.storage(context)) {
                        context.startActivity(Intents.musicShowPermission(context));
                    } else {
                        context.startActivity(MusicListAct.Intents.Audio(context, MusicListAct.Intents.SHOW));
                    }
                    responses("14", " ");
                    return true;
                }
                case Actions.CONST.ACTION_SONG_TURN: {
                    if (ComFuns.onFunction(data))
                        Answers.RESUME_SONG(context);
                    else
                        Answers.STOP_SONG(context);
                    responses("14", "  ");
                    return true;
                }
                case Actions.CONST.ACTION_SONG_PLAY: {
                    if (!Permission.Check.storage(context)) {
                        context.startActivity(Intents.musicPlayPermission(context, data));
                    } else {
                        context.startActivity(MusicListAct.Intents.Audio(context, MusicListAct.Intents.PLAY,data));
                    }
                    responses("14", " ");
                    return true;
                }
                case Actions.CONST.ACTION_SONG_STOP: {      // small song cause error in stop function so
                    Answers.CLOSE_SONG(context);            // this is the reason close function called
                    responses("14", Basics.AudioVideo.stopAudio(context));
                    return true;
                }

                case Actions.CONST.ACTION_TIMER_SET: {
                    break;
                }

                case Actions.CONST.ACTION_VIDEO_RECORD: {
                    break;
                }
                case Actions.CONST.ACTION_VIDEO_SEARCH:
                case Actions.CONST.ACTION_SONG_SEARCH: {
                    Basics.Youtube(context, data);
                    responses("14", "Searching...");
                    return true;
                }
                case Actions.CONST.ACTION_VIDEO_SEND: {
                    break;
                }
                case Actions.CONST.ACTION_VIDEO_STOP: {
                    responses("14", Basics.AudioVideo.stopVideo(context));
                    return true;
                }
                case Actions.CONST.ACTION_VIDEO_SHOW: {
                    if (!Permission.Check.storage(context)) {
                        context.startActivity(Intents.videoShowPermission(context));
                    } else {
                        context.startActivity(MusicListAct.Intents.Video(context, MusicListAct.Intents.SHOW));
                    }
                    responses("14", " ");
                    return true;
                }

                case Actions.CONST.ACTION_WEATHER_FORECAST: {
                    break;
                }
                case Actions.CONST.ACTION_WEATHER_SHOW: {
                    break;
                }
                case Actions.CONST.ACTION_WRONG_PASSWORD: {
                    if (ComFuns.onFunction(data)) {
                        context.startActivity(Intents.passwordPermission(context));
                        responses("14", " ");
                    } else if (ComFuns.offFunction(data)) {
                        if (ProcessApp.getPref().getBoolean(Params.PASSWORD_ERROR_PIC, false)) {
                            ProcessApp.getPref().edit().putBoolean(Params.PASSWORD_ERROR_PIC, false).apply();
                            responses("14", "Service Deactivated.");
                        } else {
                            responses("14", "Service is Not Active.");
                        }
                    } else responses("incomplete", "");
                    return true;
                }
            }
        }
        return false;
    }

    private String wordsCropper(String str) {
        str = str.trim().replace(" of ", "");
        str = str.trim().replace(" me ", "");
        str = str.trim().replace(" us ", "");
        return str;
    }

    @SuppressLint("SetTextI18n")
    public String setCommand(String text) {

        if (ComFuns.AppNames(text)) {
            Speak.stack.push(Coder.L_YES);
            return "Yes";
        }
        if (!checkActions(text)) {
            txtBack = new WFamily().wSplitter(text);
            if (txtBack == null) {
                txtBack = RandomWords.Actions(text);
            }
        }
        return txtBack;
    }

    private void search(String searchFor) {
        searchFor = searchFor.toLowerCase().replaceFirst("search for", "").trim();
        searchFor = searchFor.toLowerCase().replaceFirst("search ", "").trim();
        if (searchFor.toLowerCase().contains("on youtube") || searchFor.toLowerCase().contains("from youtube")) {
            searchFor = searchFor.toLowerCase().replaceFirst("on youtube", "").trim();
            searchFor = searchFor.toLowerCase().replaceFirst("from youtube", "").trim();
            Basics.Youtube(context, searchFor);
            responses("14", "Searching on Youtube.");
        } else {
            context.startActivity(new Intent(context, AssistWeb.class).putExtra("data", searchFor).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            responses("14", "Searching " + searchFor);
        }
    }

    public void responses(String code, String extras) {
        switch (code) {
            case "14": {
                txtBack = extras;
                break;
            }
            case "incomplete": {
                txtBack = "Command is In Complete." + extras;
                break;
            }
            default:
            case "next": {
                txtBack = "Can not Perform this Action. Wait for Next Update.";
                break;
            }
        }
    }

}

