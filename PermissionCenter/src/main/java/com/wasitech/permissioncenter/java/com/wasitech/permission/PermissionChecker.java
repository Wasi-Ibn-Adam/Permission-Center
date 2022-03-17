package com.wasitech.permissioncenter.java.com.wasitech.permission;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wasitech.assist.R;
import com.wasitech.assist.classes.ScreenShooter;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.services_receivers.AudioRecordingHeadService;
import com.wasitech.assist.services_receivers.BackgroundService;
import com.wasitech.assist.services_receivers.CameraHeadService;
import com.wasitech.assist.services_receivers.CommandHeadService;
import com.wasitech.assist.services_receivers.MyAdmin;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.camera.cam.CamApi2;
import com.wasitech.camera.cam.Cam;
import com.wasitech.camera.cam.CamApi1;
import com.wasitech.contact.activity.ContactListAct;
import com.wasitech.contact.runnable.ContactRunnable;
import com.wasitech.database.Params;
import com.wasitech.music.activity.MusicListAct;
import com.wasitech.music.runnable.AudioSongRunnable;
import com.wasitech.music.runnable.VideoSongRunnable;
import com.wasitech.permission.Permission;

public class PermissionChecker extends BaseCompatActivity {
    //  private String data;
    private ComponentName cm;
    private MediaProjectionManager manager;
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> finishAndRemoveTask());
    private final ActivityResultLauncher<Intent> pic = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK)
            talk("Done.");
        finishAndRemoveTask();
    });
    private final ActivityResultLauncher<Intent> lock = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Basics.LockPhone(getApplicationContext(), cm, getIntent().getBooleanExtra(LOCK, false));
        } else {
            talk("Permission Denied");
        }
        finishAndRemoveTask();
    });
    private final ActivityResultLauncher<Intent> password = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (ProcessApp.getPref().getBoolean(Params.PASSWORD_ERROR_PIC, false)) {
                talk("Wrong Password Detector Already Activated.");
            } else {
                ProcessApp.getPref().edit().putBoolean(Params.PASSWORD_ERROR_PIC, true).apply();
                talk("Wrong Password Detector Activated.");
            }
        } else {
            talk("Permission Denied");
        }
        finishAndRemoveTask();
    });
    private final ActivityResultLauncher<Intent> screenshot = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // There are no request codes
            Intent data = result.getData();
            assert data != null;
            MediaProjection mediaProjection = manager.getMediaProjection(result.getResultCode(), data);
            ScreenShooter.takeScreenshot(getApplicationContext(), mediaProjection);
        }
        finishAndRemoveTask();
    });

    public static final String FLASH = "flash";
    public static final String CAMERA = "camera";
    public static final String DIAL = "dial";
    public static final String LOCK = "lock";
    public static final String MUSIC = "music";
    public static final String PASSWORD = "password";
    public static final String SCREENSHOT = "screen-shot";


    public static final String MUSIC_PLAY = "aud-play";
    public static final String MUSIC_FIND = "aud-find";

    public static final String CONTACT_SHOW = "contact-show";
    public static final String VIDEO_SHOW = "video-show";
    public static final String PIC_SHOW = "pic-show";
    public static final String MUSIC_SHOW = "aud-show";

    public static final String SMS_SEND = "sms_send";

    public static final String BG_HEAD = "bg-head";
    public static final String AUDIO_HEAD = "aud-head";
    public static final String CAMERA_HEAD = "cam-head";
    public static final String COMMAND_HEAD = "com-head";

    public static final int RARE_CAM = 0;
    public static final int FRONT_CAM = 1;

    public PermissionChecker() {
        super(0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_Transparent);
        super.onCreate(savedInstanceState);
        String temp = getIntent().getStringExtra(Params.DATA_TRANS);
        setPermission();
        if (temp == null)
            finishAndRemoveTask();
        else {
            switch (temp) {
                case AUDIO_HEAD: {
                    com.wasitech.permission.PermissionGroup.ACTION = com.wasitech.permission.PermissionGroup.AUD_HEAD;
                    drawOverCheck();
                    break;
                }
                case BG_HEAD: {
                    com.wasitech.permission.PermissionGroup.ACTION = com.wasitech.permission.PermissionGroup.BACKGROUND;
                    drawOverCheck();
                    break;
                }
                case CAMERA: {
                    permission.groupRequest().cameraHead(com.wasitech.permission.PermissionGroup.TAKE_PIC);
                    break;
                }
                case CAMERA_HEAD: {
                    com.wasitech.permission.PermissionGroup.ACTION = com.wasitech.permission.PermissionGroup.CAM_HEAD;
                    drawOverCheck();
                    break;
                }
                case COMMAND_HEAD: {
                    com.wasitech.permission.PermissionGroup.ACTION = com.wasitech.permission.PermissionGroup.COM_HEAD;
                    drawOverCheck();
                    break;
                }
                case CONTACT_SHOW: {
                    permission.request().contacts(com.wasitech.permission.PermissionGroup.CONTACT_SHOW);
                    break;
                }
                case DIAL: {
                    permission.request().contacts(com.wasitech.permission.PermissionGroup.CONTACT_DIAL);
                    break;
                }
                case FLASH: {
                    permission.request().camera(com.wasitech.permission.PermissionGroup.FLASH);
                    break;
                }
                case MUSIC_FIND: {
                    permission.request().storage(com.wasitech.permission.PermissionGroup.MUSIC_FIND);
                    break;
                }
                case MUSIC_PLAY: {
                    permission.request().storage(com.wasitech.permission.PermissionGroup.MUSIC_PLAY);
                    break;
                }
                case MUSIC:
                case MUSIC_SHOW: {
                    permission.request().storage(com.wasitech.permission.PermissionGroup.MUSIC_SHOW);
                    break;
                }
                case PIC_SHOW: {
                    permission.request().storage(com.wasitech.permission.PermissionGroup.PIC_SHOW);
                    break;
                }
                case VIDEO_SHOW: {
                    permission.request().storage(com.wasitech.permission.PermissionGroup.VIDEO_SHOW);
                    break;
                }

                case LOCK: {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cm = new ComponentName(getApplicationContext(), MyAdmin.class));
                    lock.launch(intent);
                    break;
                }

                case PASSWORD: {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cm = new ComponentName(getApplicationContext(), MyAdmin.class));
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your Application Data.");
                    password.launch(intent);
                    break;
                }
                case SMS_SEND: {
                    permission.groupRequest().sms();
                    break;
                }
                case SCREENSHOT: {
                    manager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                    screenshot.launch(manager.createScreenCaptureIntent());
                    break;
                }
                default: {
                    finishAndRemoveTask();
                }
            }
            checker(temp);
        }
    }

    private void checker(String temp) {
        switch (temp) {
            case "loc": permission.request().location();break;
            case "mic": permission.request().microphone();break;
            case "cam": permission.request().camera();break;
            case "con_r": com.wasitech.permission.Permissions.askReadContact(PermissionChecker.this, com.wasitech.permission.Permissions.R_CONTACT);break;
            case "con_w":
            case "con_rw":permission.request().contacts();break;
            case "sto_r": com.wasitech.permission.Permissions.askReadStorage(PermissionChecker.this, com.wasitech.permission.Permissions.R_STORAGE);break;
            case "sto_w":
            case "sto_rw": permission.request().storage();break;
        }
    }

    @Override
    protected void drawOverDenied() {
        talk("Permission Denied for Draw over Other Apps.");
        finishAndRemoveTask();
    }

    @Override
    protected void drawOverAct() {
        switch (com.wasitech.permission.PermissionGroup.ACTION) {
            case com.wasitech.permission.PermissionGroup.AUD_HEAD: {
                permission.groupRequest().audioHead();
                break;
            }
            case com.wasitech.permission.PermissionGroup.BACKGROUND: {
                permission.request().microphone(com.wasitech.permission.PermissionGroup.BACKGROUND);
                break;
            }
            case com.wasitech.permission.PermissionGroup.CAM_HEAD: {
                permission.groupRequest().cameraHead();
                break;
            }
            case com.wasitech.permission.PermissionGroup.COM_HEAD: {
                permission.request().microphone(com.wasitech.permission.PermissionGroup.COM_HEAD);
                break;
            }
            default:
                finishAndRemoveTask();
        }
    }

    @Override
    protected void setPermission() {
        permission = new com.wasitech.permission.PermissionGroup(PermissionChecker.this) {
            @Override
            public void requireRationaleAsk(String per, int code) {
                super.requireRationaleAsk(per, code);
                try {
                    String text = "";
                    switch (code) {
                        case AUD_HEAD: {
                            text = getString(R.string.s_aud_head);
                            break;
                        }
                        case BACKGROUND:
                        case CODE_MICROPHONE:
                        case COM_HEAD: {
                            text = getString(R.string.s_microphone);
                            break;
                        }
                        case TAKE_PIC:
                        case CAM_HEAD: {
                            text = getString(R.string.s_cam_head);
                            break;
                        }
                        case CONTACT_DIAL:
                        case CONTACT_GET:
                        case CONTACT_SHOW: {
                            text = getString(R.string.s_contact_find);
                            break;
                        }
                        case FLASH:{
                            text = getString(R.string.s_cam_flash);
                            break;
                        }
                        case MUSIC_FIND:
                        case MUSIC_PLAY:
                        case MUSIC_SHOW:
                        case PIC_SHOW:
                        case VIDEO_SHOW: {
                            text = getString(R.string.s_media_find);
                            break;
                        }
                        case SMS_SEND: {
                            text = getString(R.string.s_sms_send);
                            break;
                        }
                    }
                    permission.displayRationale(
                            text +" "+ Permission.Talking.whichNotGranted(PermissionChecker.this, code)
                            , code
                            , true);
                } catch (Exception e) {
                    Issue.print(e, PermissionChecker.class.getName());
                }
            }

            @Override
            public void requireSimpleAsk(String per, int code) {
                super.requireSimpleAsk(per, code);
                try {
                    String text = "";
                    switch (code) {
                        case AUD_HEAD:{
                            text = getString(R.string.s_m_aud_head);
                            break;
                        }
                        case BACKGROUND:
                        case CODE_MICROPHONE:
                        case COM_HEAD: {
                            text = getString(R.string.s_m_microphone);
                            break;
                        }
                        case TAKE_PIC:
                        case CAM_HEAD:{
                            text = getString(R.string.s_m_cam_head);
                            break;
                        }
                        case CONTACT_DIAL:
                        case CONTACT_GET:
                        case CONTACT_SHOW: {
                            text = getString(R.string.s_m_contact_find);
                            break;
                        }
                        case FLASH:{
                            text = getString(R.string.s_m_cam_flash);
                            break;
                        }
                        case MUSIC_FIND:
                        case MUSIC_PLAY:
                        case MUSIC_SHOW:
                        case VIDEO_SHOW:{
                            text = getString(R.string.s_m_media_find);
                            break;
                        }
                        case SMS_SEND:{
                            text=getString(R.string.s_m_sms_send);
                        }
                    }
                    permission.displaySimple(text + " "+ Permission.Talking.whichNotGranted(PermissionChecker.this, code), true);
                } catch (Exception e) {
                    Issue.print(e, PermissionChecker.class.getName());
                }
            }

            @Override
            public void onDenied(int code) {
                talk("Permission Denied.");
                try {
                    finishAndRemoveTask();
                } catch (Exception e) {
                    Issue.print(e, PermissionChecker.class.getName());
                }
            }

            @Override
            public void onGranted(int code) {
                try {
                    switch (code) {
                        case AUD_HEAD: {
                            if (getIntent().hasExtra(AUDIO_HEAD)) {
                                if (getIntent().getBooleanExtra(AUDIO_HEAD, false)) {
                                    startService(Intents.AudioHead(getApplicationContext()));
                                } else {
                                    stopService(Intents.AudioHead(getApplicationContext()));
                                }
                            }
                            else {
                                if (!Basics.isMyServiceRunning(getApplicationContext(), AudioRecordingHeadService.class)) {
                                    startService(Intents.AudioHead(getApplicationContext()));
                                    talk("Audio Head is Active");
                                } else {
                                    stopService(Intents.AudioHead(getApplicationContext()));
                                    talk("Audio Head is Deactivated");
                                }
                            }
                            finishAndRemoveTask();
                            break;
                        }
                        case BACKGROUND:{
                            if (getIntent().hasExtra(BG_HEAD)) {
                                if (getIntent().getBooleanExtra(BG_HEAD, false)) {
                                    startService(Intents.Background(getApplicationContext()));
                                } else {
                                    stopService(Intents.Background(getApplicationContext()));
                                }
                            }
                            else {
                                if (!Basics.isMyServiceRunning(getApplicationContext(), BackgroundService.class)) {
                                    startService(Intents.Background(getApplicationContext()));
                                    talk("Whistle and Command is Active");
                                } else {
                                    stopService(Intents.Background(getApplicationContext()));
                                    talk("Whistle and Command is Deactivated");
                                }
                            }
                            finishAndRemoveTask();
                            break;
                        }
                        case CAM_HEAD: {
                            if (getIntent().hasExtra(CAMERA_HEAD)) {
                                if (getIntent().getBooleanExtra(CAMERA_HEAD, false)) {
                                    startService(Intents.CamHead(getApplicationContext()));
                                } else {
                                    stopService(Intents.CamHead(getApplicationContext()));
                                }
                            } else {
                                if (!Basics.isMyServiceRunning(getApplicationContext(), CameraHeadService.class)) {
                                    startService(Intents.CamHead(getApplicationContext()));
                                    talk("Camera Head is Active");
                                } else {
                                    stopService(Intents.CamHead(getApplicationContext()));
                                    talk("Camera Head is Deactivated");
                                }
                            }
                            finishAndRemoveTask();
                            break;
                        }
                        case CODE_CONTACT:{
                            startContactThreads();
                            finishAndRemoveTask();
                            break;
                        }
                        case CONTACT_SHOW:{
                            startContactThreads();
                            startActivity(ContactListAct.Intents.showContacts(getApplicationContext(), getIntent().getStringExtra(PermissionChecker.CONTACT_SHOW)));
                            finish();
                            break;
                        }
                        case CONTACT_DIAL:{
                            startContactThreads();
                            launcher.launch(ContactListAct.Intents.dailContacts(getApplicationContext(), getIntent().getStringExtra(DIAL)));
                            break;
                        }
                        case CODE_STORAGE: {
                            startMusicThreads();
                            finishAndRemoveTask();
                            break;
                        }
                        case COM_HEAD: {
                            if (getIntent().hasExtra(COMMAND_HEAD)) {
                                if (getIntent().getBooleanExtra(COMMAND_HEAD, false)) {
                                    startService(Intents.ComHead(getApplicationContext()));
                                } else {
                                    stopService(Intents.ComHead(getApplicationContext()));
                                }
                            } else {
                                if (!Basics.isMyServiceRunning(getApplicationContext(), CommandHeadService.class)) {
                                    startService(Intents.ComHead(getApplicationContext()));
                                    talk("Command Head is Active");
                                } else {
                                    stopService(Intents.ComHead(getApplicationContext()));
                                    talk("Command Head is Deactivated");
                                }
                            }
                            finishAndRemoveTask();
                            break;
                        }
                        case FLASH:{
                            Basics.FlashLight(getApplicationContext(), getIntent().getBooleanExtra(PermissionChecker.FLASH, false));
                            finishAndRemoveTask();
                            break;
                        }
                        case MUSIC_FIND: {
                            startMusicThreads();
                            startActivity(MusicListAct.Intents.search(getApplicationContext(), MusicListAct.Intents.SHOW, getIntent().getStringExtra(PermissionChecker.MUSIC_FIND)));
                            finish();
                            break;
                        }
                        case MUSIC_PLAY: {
                            startMusicThreads();
                            startActivity(MusicListAct.Intents.Audio(PermissionChecker.this, MusicListAct.Intents.PLAY, getIntent().getStringExtra(PermissionChecker.MUSIC_PLAY)));
                            finish();
                            break;
                        }
                        case MUSIC_SHOW: {
                            startMusicThreads();
                            startActivity(MusicListAct.Intents.Audio(PermissionChecker.this, MusicListAct.Intents.SHOW,getIntent().getStringExtra(PermissionChecker.MUSIC_SHOW)));
                            finish();
                            break;
                        }
                        case PIC_SHOW: {
                            startMusicThreads();
                            startActivity(Intents.PictureListActivity(getApplicationContext()));
                            finish();
                            break;
                        }
                        case SMS_SEND:{
                            startContactThreads();
                            String msg=getIntent().getStringExtra(Params.MESSAGE);
                            String data=getIntent().getStringExtra(Params.NUM);
                            Basics.Send.sendMessage(PermissionChecker.this, msg+"", data);
                            finish();
                            break;
                        }
                        case TAKE_PIC:{
                            startMusicThreads();
                            int cam = getIntent().getIntExtra(CAMERA, 0);
                            if (Cam.allowCamera2Support(getApplicationContext(), cam)) {
                                pic.launch(CamApi2.Intents.takePic(getApplicationContext(),cam));
                            } else {
                                new CamApi1(PermissionChecker.this, cam, true);
                                talk("Done.");
                                finishAndRemoveTask();
                            }
                            break;
                        }
                        case VIDEO_SHOW:{
                            startMusicThreads();
                            startActivity(MusicListAct.Intents.Video(getApplicationContext(), MusicListAct.Intents.SHOW));
                            break;
                        }
                    }
                } catch (Exception e) {
                    Issue.print(e, PermissionChecker.class.getName());
                    finishAndRemoveTask();
                }
            }

            @Override
            public void neverAskAgain(int code) {
                try {
                    talk("Grant " + Permission.Talking.whichNotGranted(PermissionChecker.this, code) + " permission.");
                    startActivity(Permission.gotoSettings(getPackageName()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } catch (Exception e) {
                    Issue.print(e, PermissionChecker.class.getName());
                }
                finishAndRemoveTask();
            }

            @Override
            public void onComplete(int code) {
                //try {
                //    finishAndRemoveTask();
                //} catch (Exception e) {
                //    Issue.print(e, PermissionChecker.class.getName());
                //}
            }

        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permission.onResult(requestCode);
    }
    protected void startMusicThreads(){
        new Thread(new AudioSongRunnable(getApplicationContext()) {
            @Override
            public void onComplete() {
            }
        }).start();
        new Thread(new VideoSongRunnable(getApplicationContext()) {
            @Override
            public void onComplete() {

            }
        }).start();
    }

    protected void startContactThreads(){
        new Thread(new ContactRunnable(getApplicationContext(), true)).start();
    }
}
